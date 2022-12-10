/* -*- Mode: C; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// -------------------------------------------------------------------------*/

#include "debug.h"
#include "engine/pipe.h"
#include "engine/pipeintern.h"
#include "memory.h"

struct sjme_pipeInstance
{
	/** The type of pipe this is. */
	sjme_pipeRedirectType type;
	
	/** The functions used to handle this pipe. */
	sjme_pipeFunction functions;
	
	/** Is the pipe closed? */
	sjme_jboolean isClosed;
};

/* -------------------------------- DISCARD ------------------------------- */

static sjme_jboolean sjme_discardPipeClose(sjme_pipeInstance* pipe,
	sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

static sjme_jboolean sjme_discardPipeFlush(sjme_pipeInstance* pipe,
	sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

static sjme_jboolean sjme_discardPipeNewInstance(sjme_pipeInstance* outPipe,
	sjme_file* file, sjme_jboolean isInput, sjme_error* error)
{
	if (outPipe == NULL)
	{
		sjme_setError(error, SJME_ERROR_NULLARGS, 0);
		return sjme_false;
	}
	
	if (file != NULL)
	{
		sjme_setError(error, SJME_ERROR_INVALIDARG, 0);
		return sjme_false;
	}
	
	sjme_todo("Implement this?");
	return sjme_false;
}

static sjme_jboolean sjme_discardPipeRead(sjme_pipeInstance* pipe,
	sjme_pipeDirection direction, sjme_jbyte* buf,
	sjme_jint off, sjme_jint len, sjme_jint* result, sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}
	
static sjme_jboolean sjme_discardPipeWrite(sjme_pipeInstance* pipe,
	sjme_pipeDirection direction, sjme_jbyte* buf,
	sjme_jint off, sjme_jint len, sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

/* --------------------------------- BUFFER ------------------------------- */

static sjme_jboolean sjme_bufferPipeClose(sjme_pipeInstance* pipe,
	sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

static sjme_jboolean sjme_bufferPipeFlush(sjme_pipeInstance* pipe,
	sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

static sjme_jboolean sjme_bufferPipeNewInstance(sjme_pipeInstance* outPipe,
	sjme_file* file, sjme_jboolean isInput, sjme_error* error)
{
	if (outPipe == NULL)
	{
		sjme_setError(error, SJME_ERROR_NULLARGS, 0);
		return sjme_false;
	}
	
	if (file != NULL)
	{
		sjme_setError(error, SJME_ERROR_INVALIDARG, 0);
		return sjme_false;
	}
	
	sjme_todo("Implement this?");
	return sjme_false;
}
	
static sjme_jboolean sjme_bufferPipeRead(sjme_pipeInstance* pipe,
	sjme_pipeDirection direction, sjme_jbyte* buf,
	sjme_jint off, sjme_jint len, sjme_jint* result, sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}
	
static sjme_jboolean sjme_bufferPipeWrite(sjme_pipeInstance* pipe,
	sjme_pipeDirection direction, sjme_jbyte* buf,
	sjme_jint off, sjme_jint len, sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

/* -------------------------------- TERMINAL ------------------------------ */

static sjme_jboolean sjme_terminalPipeClose(sjme_pipeInstance* pipe,
	sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

static sjme_jboolean sjme_terminalPipeFlush(sjme_pipeInstance* pipe,
	sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

static sjme_jboolean sjme_terminalPipeNewInstance(sjme_pipeInstance* outPipe,
	sjme_file* file, sjme_jboolean isInput, sjme_error* error)
{
	if (outPipe == NULL || file == NULL)
	{
		sjme_setError(error, SJME_ERROR_NULLARGS, 0);
		return sjme_false;
	}
	
	sjme_todo("Implement this?");
	return sjme_false;
}
	
static sjme_jboolean sjme_terminalPipeRead(sjme_pipeInstance* pipe,
	sjme_pipeDirection direction, sjme_jbyte* buf,
	sjme_jint off, sjme_jint len, sjme_jint* result, sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}
	
static sjme_jboolean sjme_terminalPipeWrite(sjme_pipeInstance* pipe,
	sjme_pipeDirection direction, sjme_jbyte* buf,
	sjme_jint off, sjme_jint len, sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

static const sjme_pipeFunction sjme_pipeFunctions
	[SJME_NUM_PIPE_REDIRECTS] =
{
	/** @c SJME_TASK_PIPE_REDIRECT_DISCARD . */
	{
		.close = sjme_discardPipeClose,
		.flush = sjme_discardPipeFlush,
		.newInstance = sjme_discardPipeNewInstance,
		.read = sjme_discardPipeRead,
		.write = sjme_discardPipeWrite,
	},
	
	/** @c SJME_TASK_PIPE_REDIRECT_BUFFER . */
	{
		.close = sjme_bufferPipeClose,
		.flush = sjme_bufferPipeFlush,
		.newInstance = sjme_bufferPipeNewInstance,
		.read = sjme_bufferPipeRead,
		.write = sjme_bufferPipeWrite,
	},
	
	/** @c SJME_TASK_PIPE_REDIRECT_TERMINAL . */
	{
		.close = sjme_terminalPipeClose,
		.flush = sjme_terminalPipeFlush,
		.newInstance = sjme_terminalPipeNewInstance,
		.read = sjme_terminalPipeRead,
		.write = sjme_terminalPipeWrite,
	},
};

sjme_jboolean sjme_pipeNewInstance(sjme_pipeRedirectType type,
	sjme_pipeInstance** outPipe, sjme_file* file, sjme_jboolean isInput,
	sjme_error* error)
{
	sjme_pipeInstance* result;
	
	if (outPipe == NULL)
	{
		sjme_setError(error, SJME_ERROR_NULLARGS, 0);
		
		return sjme_false;
	}
	
	if (type < SJME_PIPE_REDIRECT_DISCARD || type >= SJME_NUM_PIPE_REDIRECTS)
	{
		sjme_setError(error, SJME_ERROR_INVALIDARG, type);
		
		return sjme_false;
	}
	
	/* Try to allocate the result. */
	result = sjme_malloc(sizeof(*result), error);
	if (result == NULL)
		return sjme_false;
	
	/* Initialize base info. */
	result->type = type;
	result->functions = sjme_pipeFunctions[type];
	
	/* Call sub-initializer accordingly. */
	if (!result->functions.newInstance(result, file, isInput, error))
	{
		sjme_free(result, error);
		
		if (!sjme_hasError(error))
			sjme_setError(error, SJME_ERROR_BAD_PIPE_INIT, 0);
		
		return sjme_false;
	}
	
	/* Use this one! */
	*outPipe = result;
	return sjme_true;
}

sjme_jboolean sjme_pipeDelete(sjme_pipeInstance* inPipe,
	sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

sjme_jboolean sjme_pipeNewAlwaysEOF(sjme_pipeInstance** outPipe,
	sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

sjme_jboolean sjme_pipeNewBufferPair(sjme_buffer* buffer,
	sjme_pipeInstance** outReadEnd, sjme_pipeInstance** outWriteEnd,
	sjme_jboolean bidirectional, sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

sjme_jboolean sjme_pipeNewDiscardWrites(sjme_pipeInstance** outPipe,
	sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}

sjme_jboolean sjme_pipeNewStdPipe(sjme_standardPipeType stdPipe,
	sjme_pipeInstance** outPipe, sjme_error* error)
{
	sjme_todo("Implement this?");
	return sjme_false;
}
