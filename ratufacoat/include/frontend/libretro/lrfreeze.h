/* -*- Mode: C; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// -------------------------------------------------------------------------*/

/**
 * Support for freezing and save states.
 * 
 * @since 2022/01/03
 */

#ifndef SQUIRRELJME_LRFREEZE_H
#define SQUIRRELJME_LRFREEZE_H

#include "lrlocal.h"

/*--------------------------------------------------------------------------*/

/**
 * Sets whether freezing is inhibited or not.
 * 
 * @param inhibit Should freezing be inhibited?
 * @since 2022/01/03 
 */
void sjme_libRetro_inhibitFastForward(sjme_jboolean inhibit);

/*--------------------------------------------------------------------------*/

#endif /* SQUIRRELJME_LRFREEZE_H */
