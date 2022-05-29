/* -*- Mode: C; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// -------------------------------------------------------------------------*/

/**
 * Screen related functions and interfaces.
 * 
 * @since 2022/01/02
 */

#ifndef SQUIRRELJME_LRSCREEN_H
#define SQUIRRELJME_LRSCREEN_H

/*--------------------------------------------------------------------------*/

/**
 * Emits a message to the console and screen.
 * 
 * @param percent Percentage of progress, if out of bounds of @c 0 to @c 100
 * it is not considered valid.
 * @param format The message format. 
 * @param ... The specifiers.
 * @since 2022/01/02
 */
void sjme_libRetro_message(sjme_jbyte percent, const char* const format, ...);

/**
 * Initializes the screen settings.
 * 
 * @param config The configuration to set.
 * @return If initialization was a success.
 * @since 2022/01/02 
 */
sjme_jboolean sjme_libRetro_screenConfig(sjme_engineConfig* config);

/*--------------------------------------------------------------------------*/

#endif /* SQUIRRELJME_LRSCREEN_H */
