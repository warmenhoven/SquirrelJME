/* -*- Mode: C; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// -------------------------------------------------------------------------*/

#include <stdio.h>
#include <stdarg.h>

#include "debug.h"

/** Debug buffer size for messages. */
#define DEBUG_BUF 512

void sjme_messageR(const char* file, int line,
	const char* func, const char* message, ...)
{
	char buf[DEBUG_BUF];
	va_list args;
	
	/* Load message buffer. */
	va_start(args, message);
	vsnprintf(buf, DEBUG_BUF, message, args);
	va_end(args);
	
	/* Print output message. */
	if (file != NULL || line > 0 || func != NULL) 
		fprintf(stderr, "DB: (%s:%d in %s()): %s\n",
			file, line, func, buf);
	else
		fprintf(stderr, "DB: %s\n",
			buf);
}

sjme_returnNever sjme_todoR(const char* file, int line,
	const char* func, const char* message, ...)
{
	char buf[DEBUG_BUF];
	va_list args;
	
	/* Load message buffer. */
	va_start(args, message);
	vsnprintf(buf, DEBUG_BUF, message, args);
	va_end(args);
	
	/* Print output message. */
	if (file != NULL || line > 0 || func != NULL)
		fprintf(stderr, "TD: TODO Hit (%s:%d in %s()): %s\n",
			file, line, func, buf);
	else
		fprintf(stderr, "TD: TODO Hit: %s\n",
			buf);
	
	/* Exit and stop. */
	exit(EXIT_FAILURE);
	
	/* These are totally not used. */
#if defined(__clang__)
	#pragma clang diagnostic push
	#pragma ide diagnostic ignored "UnreachableCode"
	#pragma ide diagnostic ignored "UnusedLocalVariable"
#endif
	{
		sjme_returnNever fail;
		
		fail.ignored = 0;
		
		return fail;
	}
#if defined(__clang__)
	#pragma clang diagnostic pop
#endif
}