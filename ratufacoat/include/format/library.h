/* -*- Mode: C; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// -------------------------------------------------------------------------*/

/**
 * Library (JAR) support.
 * 
 * @since 2021/09/12
 */

#ifndef SQUIRRELJME_LIBRARY_H
#define SQUIRRELJME_LIBRARY_H

#include "sjmerc.h"
#include "counter.h"
#include "error.h"
#include "format/detect.h"
#include "format/format.h"
#include "memchunk.h"

/* Anti-C++. */
#ifdef __cplusplus
#ifndef SJME_CXX_IS_EXTERNED
#define SJME_CXX_IS_EXTERNED
#define SJME_CXX_SQUIRRELJME_LIBRARY_H
extern "C"
{
#endif /* #ifdef SJME_CXX_IS_EXTERNED */
#endif /* #ifdef __cplusplus */

/*--------------------------------------------------------------------------*/

typedef struct sjme_libraryInstance sjme_libraryInstance;

/**
 * This represents a library driver that is available for usage.
 * 
 * @since 2021/09/12
 */
typedef struct sjme_libraryDriver
{
	/** Is this for the given library driver? */
	sjme_formatDetectFunction detect;
	
	/** Initialization function. */
	sjme_formatInitFunction init;
	
	/** Destroy function. */
	sjme_formatDestroyFunction destroy;
} sjme_libraryDriver;

/**
 * Instance of a pack, is internally kept state.
 * 
 * @since 2021/09/19
 */ 
struct sjme_libraryInstance
{
	/** The format instance. */
	sjme_formatInstance format;
	
	/** The driver used for the library. */
	const sjme_libraryDriver* driver;
	
	/** Instance state for the current driver. */
	void* state;
	
	/** Counter for the library instance. */
	sjme_counter counter;
	
	/** The pointer to the pack this is within, will be @c NULL if not. */
	void* packOwner;
	
	/** The index within the pack file, will be @c -1 if not in a pack. */
	sjme_jint packIndex;
	
	/** The number of entries which are in this library. */
	sjme_jint numEntries;
};

/**
 * Closes the given library instance.
 * 
 * @param instance The instance of the library to close. 
 * @param error The error state if not closed.
 * @return If the library was properly closed.
 * @since 2021/10/31
 */
sjme_jboolean sjme_libraryClose(sjme_libraryInstance* instance,
	sjme_error* error);

/**
 * Opens the given library and makes an instance of it.
 * 
 * @param outInstance The output instance for returning.
 * @param data The data block.
 * @param size The size of the data block.
 * @param error The error state on open.
 * @return If this was successful or not.
 * @since 2021/09/19
 */
sjme_jboolean sjme_libraryOpen(sjme_libraryInstance** outInstance,
	const void* data, sjme_jint size, sjme_error* error);

/*--------------------------------------------------------------------------*/

/* Anti-C++. */
#ifdef __cplusplus
#ifdef SJME_CXX_SQUIRRELJME_LIBRARY_H
}
#undef SJME_CXX_SQUIRRELJME_LIBRARY_H
#undef SJME_CXX_IS_EXTERNED
#endif /* #ifdef SJME_CXX_SQUIRRELJME_LIBRARY_H */
#endif /* #ifdef __cplusplus */

#endif /* SQUIRRELJME_LIBRARY_H */