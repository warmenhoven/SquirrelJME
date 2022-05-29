/* -*- Mode: C; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// -------------------------------------------------------------------------*/

/**
 * This contains an implementation of an atomic list which is similar in
 * function to an atomic array list.
 * 
 * @since 2022/03/27
 */

#ifndef SQUIRRELJME_ATOMICLIST_H
#define SQUIRRELJME_ATOMICLIST_H

/*--------------------------------------------------------------------------*/

/**
 * This is an atomic list, which can be used when multiple threads need
 * to access a list structure where there may be potential contention between
 * threads trying to use it. It is similar in function to an atomic
 * array list.
 * 
 * @since 2022/03/27
 */
typedef struct sjme_atomicList sjme_atomicList;

/*--------------------------------------------------------------------------*/

#endif /* SQUIRRELJME_ATOMICLIST_H */
