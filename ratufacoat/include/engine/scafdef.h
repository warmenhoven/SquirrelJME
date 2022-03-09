/* -*- Mode: C; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// -------------------------------------------------------------------------*/

/**
 * Scaffold definitions.
 * 
 * @since 2022/01/05
 */

#ifndef SQUIRRELJME_SCAFDEF_H
#define SQUIRRELJME_SCAFDEF_H

#include "sjmerc.h"
#include "utf.h"

/* Anti-C++. */
#ifdef __cplusplus
#ifndef SJME_CXX_IS_EXTERNED
#define SJME_CXX_IS_EXTERNED
#define SJME_CXX_SQUIRRELJME_SCAFDEF_H
extern "C"
{
#endif /* #ifdef SJME_CXX_IS_EXTERNED */
#endif /* #ifdef __cplusplus */

/*--------------------------------------------------------------------------*/

/**
 * The state of any given engine.
 * 
 * @since 2022/01/03
 */
typedef struct sjme_engineState sjme_engineState;

/**
 * The state of a single task within the engine.
 * 
 * @since 2022/01/09
 */
typedef struct sjme_engineTask sjme_engineTask;

/**
 * The state of a single thread within the engine.
 * 
 * @since 2022/01/03
 */
typedef struct sjme_engineThread sjme_engineThread;

/**
 * Represents the main arguments to a program.
 * 
 * @since 2022/01/09
 */
typedef struct sjme_mainArgs
{
	/** The number of arguments that exist. */
	sjme_jint count;
	
	/** The main arguments. */
	sjme_utfString* args[0];
} sjme_mainArgs;

/*--------------------------------------------------------------------------*/

/* Anti-C++. */
#ifdef __cplusplus
#ifdef SJME_CXX_SQUIRRELJME_SCAFDEF_H
}
#undef SJME_CXX_SQUIRRELJME_SCAFDEF_H
#undef SJME_CXX_IS_EXTERNED
#endif /* #ifdef SJME_CXX_SQUIRRELJME_SCAFDEF_H */
#endif /* #ifdef __cplusplus */

#endif /* SQUIRRELJME_SCAFDEF_H */