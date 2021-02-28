/* -*- Mode: C; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// -------------------------------------------------------------------------*/

/**
 * Base JVM.
 * 
 * @since 2021/02/27
 */

#include "sjmerc.h"
#include "jvm.h"
#include "cpu.h"
#include "corefont.h"
#include "oldstuff.h"
#include "bootrom.h"
#include "memory.h"


sjme_jint sjme_jvmDestroy(sjme_jvm* jvm, sjme_error* error)
{
	sjme_cpuframe* cpu;
	sjme_cpuframe* oldcpu;
	sjme_jint i;
	
	/* Missing this? */
	if (jvm == NULL)
	{
		sjme_seterror(error, SJME_ERROR_INVALIDARG, 0);
		
		return 0;
	}
	
	/* Reset error. */
	sjme_seterror(error, SJME_ERROR_NONE, 0);
	
	/* Go through and cleanup CPUs. */
	for (i = 0; i < SJME_THREAD_MAX; i++)
	{
		/* Get CPU here. */
		cpu = &jvm->threads[i].state;
		
		/* Recursively clear CPU stacks. */
		while (cpu->parent != NULL)
		{
			/* Keep for later free. */
			oldcpu = cpu->parent;
			
			/* Copy down. */
			*cpu = *oldcpu;
			
			/* Free CPU state. */
			if (oldcpu != &jvm->threads[i].state)
				sjme_free(oldcpu);
		}
	}
	
	/* Delete major JVM data areas. */
	sjme_free(jvm->ram);
	sjme_free(jvm->config);
	if (jvm->presetrom == NULL)
		sjme_free(jvm->rom);
	
	/* Destroyed okay. */
	return 1;
}

sjme_jint sjme_jvmexec(sjme_jvm* jvm, sjme_error* error, sjme_jint cycles)
{
	sjme_jint threadid;
	sjme_cpu* cpu;
	sjme_error xerror;
	
	/* Fallback error state. */
	if (error == NULL)
	{
		memset(&xerror, 0, sizeof(xerror));
		error = &xerror;
	}
	
	/* Do nothing. */
	if (jvm == NULL)
	{
		sjme_seterror(error, SJME_ERROR_INVALIDARG, 0);
		
		return 0;
	}
	
	/* Run cooperatively threaded style CPU. */
	for (threadid = jvm->fairthreadid;;
		threadid = ((threadid + 1) & SJME_THREAD_MASK))
	{
		/* Have we used all our execution cycles? */
		if (cycles >= 0)
		{
			if (cycles == 0)
				break;
			if ((--cycles) <= 0)
				break;
		}
		
		/* Ignore CPUs which are not turned on. */
		cpu = &jvm->threads[threadid];
		if (cpu->threadstate == SJME_THREAD_STATE_NONE)
			continue;
		
		/* Execute CPU engine. */
		cycles = sjme_cpuexec(jvm, cpu, error, cycles);
		
		/* CPU fault, stop! */
		if (error->code != SJME_ERROR_NONE)
			break;
	}
	
	/* Start next run on the CPU that was last executing. */
	jvm->fairthreadid = (threadid & SJME_THREAD_MASK);
	
	/* Print error state to console? */
	if (error->code != SJME_ERROR_NONE)
	{
		/* Force error to be on-screen. */
		jvm->supervisorokay = 0;
		sjme_printerror(jvm, error);
	}
	
	/* Returning remaining number of cycles. */
	return cycles;
}

sjme_jvm* sjme_jvmNew(sjme_jvmoptions* options, sjme_nativefuncs* nativefuncs,
	sjme_error* error)
{
	sjme_jvmoptions nulloptions;
	void* ram;
	void* rom;
	void* conf;
	void* optionjar;
	sjme_jvm* rv;
	sjme_jint i, l, romsize;
	sjme_framebuffer* fbinfo;
	sjme_vmem* vmem;
	
	/* We need native functions. */
	if (nativefuncs == NULL)
		return NULL;
	
	/* Allocate virtual memory manager. */
	vmem = sjme_vmmnew(error);
	if (vmem == NULL)
	{
		sjme_seterror(error, SJME_ERROR_VMMNEWFAIL, error->code);
		
		return NULL;
	}
	
	/* Allocate VM state. */
	rv = sjme_malloc(sizeof(*rv));
	conf = sjme_malloc(SJME_DEFAULT_CONF_SIZE);
	if (rv == NULL || conf == NULL)
	{
		sjme_seterror(error, SJME_ERROR_NOMEMORY,
			sizeof(*rv) + SJME_DEFAULT_CONF_SIZE);
		
		sjme_free(rv);
		sjme_free(conf);
		
		return NULL;
	}
	
	/* Store virtual memory area. */
	rv->vmem = vmem;
	
	/* Virtual map config. */
	rv->config = sjme_vmmmap(vmem, 0, conf, SJME_DEFAULT_CONF_SIZE, error);
	if (rv->config == NULL)
		return NULL;
	
	/* If there were no options specified, just use a null set. */
	if (options == NULL)
	{
		memset(&nulloptions, 0, sizeof(nulloptions));
		options = &nulloptions;
	}
	
	/* If no RAM size was specified then use the default. */
	if (options->ramsize <= 0)
		options->ramsize = SJME_DEFAULT_RAM_SIZE;
	
	/* Allocate RAM, or at least keep trying to. */
	while (options->ramsize >= SJME_MINIMUM_RAM_SIZE)
	{
		/* Attempt to allocate the RAM. */
		ram = sjme_malloc(options->ramsize);
		
		/* Ram allocated! So stop. */
		if (ram != NULL)
			break;
		
		/* Cut RAM allocation size down in half. */
		options->ramsize /= 2;
	}
	
	/* Failed to allocate the RAM. */
	if (ram == NULL)
	{
		sjme_seterror(error, SJME_ERROR_NOMEMORY, options->ramsize);
			
		sjme_free(rv);
		sjme_free(conf);
		
		return NULL;
	}
	
	/* Virtual map RAM. */
	rv->ram = sjme_vmmmap(vmem, 0, ram, options->ramsize, error);
	if (rv->ram == NULL)
	{
		sjme_seterror(error, SJME_ERROR_VMMMAPFAIL, 0);
		
		sjme_free(rv);
		sjme_free(conf);
		
		return NULL;
	}
	
	/* Set native functions. */
	rv->nativefuncs = nativefuncs;
	
	/* Initialize the framebuffer info, if available. */
	fbinfo = NULL;
	if (nativefuncs->framebuffer != NULL)
		fbinfo = nativefuncs->framebuffer();
	
	/* Initialize framebuffer, done a bit early to show errors. */
	rv->fbinfo = fbinfo;
	if (fbinfo != NULL)
	{
		/* If scan-line is not specified, default to the display width. */
		if (fbinfo->scanlen == 0)
			fbinfo->scanlen = fbinfo->width;
		
		/* Number of available pixels. */
		if (fbinfo->numpixels == 0)
			fbinfo->numpixels = fbinfo->scanlen * fbinfo->height;
		
		/* Bytes per pixel must be specified. */
		if (fbinfo->bitsperpixel == 0)
			switch (fbinfo->format)
			{
				case SJME_PIXELFORMAT_PACKED_ONE:
					fbinfo->bitsperpixel = 1;
					break;
				
				case SJME_PIXELFORMAT_PACKED_TWO:
					fbinfo->bitsperpixel = 2;
					break;
				
				case SJME_PIXELFORMAT_PACKED_FOUR:
					fbinfo->bitsperpixel = 4;
					break;
				
				case SJME_PIXELFORMAT_BYTE_INDEXED:
					fbinfo->bitsperpixel = 8;
					break;
				
				case SJME_PIXELFORMAT_SHORT_RGB565:
					fbinfo->bitsperpixel = 16;
					break;
				
				default:
				case SJME_PIXELFORMAT_INTEGER_RGB888:
					fbinfo->bitsperpixel = 32;
					break;
			}
		
		/* Scan line in bytes is based on the bytes per pixel. */
		if (fbinfo->scanlenbytes == 0)
			fbinfo->scanlenbytes =
				(fbinfo->scanlen * fbinfo->bitsperpixel) / 8;
		
		/* Console positions and size. */
		rv->conx = 0;
		rv->cony = 0;
		rv->conw = fbinfo->width / sjme_font.charwidths[0];
		rv->conh = fbinfo->height / sjme_font.pixelheight;
	}
	
	/* Virtual map framebuffer, if available. */
	if (fbinfo != NULL)
	{
		rv->framebuffer = sjme_vmmmap(vmem, 0, fbinfo->pixels,
			(fbinfo->numpixels * fbinfo->bitsperpixel) / 8, error);
		if (rv->framebuffer == NULL)
		{
			sjme_seterror(error, SJME_ERROR_VMMMAPFAIL, 0);
			
			sjme_free(rv);
			sjme_free(ram);
			sjme_free(conf);
			
			return NULL;
		}
	}
	
	/* Needed by destruction later. */
	rv->presetrom = options->presetrom;
	
	/* Load the ROM? */
	rom = options->presetrom;
	if (rom == NULL)
	{
		/* Call sub-routine which can load the ROM. */
		rom = sjme_loadrom(nativefuncs, &romsize, error);
		
		/* Could not load the ROM? */
		if (rom == NULL)
		{
			/* Write the ROM failure message! */
			sjme_console_pipewrite(rv, (nativefuncs != NULL ?
				nativefuncs->stderr_write : NULL), sjme_romfailmessage, 0,
				sizeof(sjme_romfailmessage) / sizeof(sjme_jbyte), error);
			
			/* Clear resources */
			sjme_free(rv);
			sjme_free(ram);
			sjme_free(conf);
			
			/* Fail */
			return NULL;
		}
	}
	
	/* If we are copying from the preset ROM, duplicate it. */
	if (options->presetrom != NULL && options->copyrom != 0)
	{
		/* Use this ROM size. */
		romsize = options->romsize;
		
		/* Allocate space to fit ROM. */
		rom = sjme_malloc(options->romsize);
		if (rom == NULL)
		{
			sjme_seterror(error, SJME_ERROR_NOMEMORY, options->romsize);
			
			sjme_free(ram);
			sjme_free(conf);
			
			return NULL;
		}
		
		/* Copy large chunks at a time. */
		for (i = 0; i < options->romsize;)
		{
			/* Byte left to move? */
			l = options->romsize - i;
			
			/* Function uses a size_t which may be limited on this platform. */
			if (sizeof(sjme_jint) > sizeof(size_t) && l > (sjme_jint)SIZE_MAX)
				l = (sjme_jint)SIZE_MAX;
			
			/* Copy the data. */
			memmove(SJME_POINTER_OFFSET(rom, i),
				SJME_POINTER_OFFSET(options->presetrom, i), l);
			
			/* Offset up. */
			i += l;
		}
		
		/* We copied it, so never make a preset ROM. */
		rv->presetrom = NULL;
	}
	
	/* If we are using a preset ROM then just use the size. */
	if (rv->presetrom != NULL)
		romsize = options->romsize;
	
	/* Virtual map ROM. */
	rv->rom = sjme_vmmmap(vmem, 0, rom, romsize, error);
	if (rv->rom == NULL)
	{
		sjme_seterror(error, SJME_ERROR_VMMMAPFAIL, 0);
		
		sjme_free(rv);
		sjme_free(ram);
		sjme_free(conf);
		if (rv->presetrom == NULL)
			sjme_free(rom);
		
		return NULL;
	}
	
	/* Initialize configuration space. */
	sjme_configinit(rv, options, nativefuncs, error);
	
	/* Initialize the BootRAM and boot the CPU. */
	if (sjme_loadBootRom(rv, error) == 0)
	{
		/* Write the Boot failure message! */
		sjme_console_pipewrite(rv, (nativefuncs != NULL ?
			nativefuncs->stderr_write : NULL), sjme_bootfailmessage, 0,
			sizeof(sjme_bootfailmessage) / sizeof(sjme_jbyte), error);
		
		/* Force error to be on-screen. */
		rv->supervisorokay = 0;
		sjme_printerror(rv, error);
		
		/* Cleanup. */
		sjme_free(rv);
		sjme_free(ram);
		sjme_free(conf);
		
		/* If a pre-set ROM is not being used, make sure it gets cleared. */
		if (rv->presetrom == NULL)
			sjme_free(rom);
		
		return NULL;
	}
	
	/* Memory map the option JAR, if available. */
	if (nativefuncs->optional_jar != NULL)
		if (nativefuncs->optional_jar(&optionjar, &i) != 0)
		{
			rv->optionjar = sjme_vmmmap(vmem, 0, optionjar, i, error);
			if (rv->rom == NULL)
			{
				sjme_seterror(error, SJME_ERROR_VMMMAPFAIL, 0);
				
				sjme_free(rv);
				sjme_free(ram);
				sjme_free(conf);
				if (rv->presetrom == NULL)
					sjme_free(rom);
				
				return NULL;
			}
		}
	
	/* The JVM is ready to use. */
	return rv;
}

