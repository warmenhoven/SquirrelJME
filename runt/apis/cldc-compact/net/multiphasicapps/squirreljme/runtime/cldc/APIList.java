// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.runtime.cldc;

/**
 * This contains the integer based index of the available APIs.
 *
 * @since 2017/12/07
 */
public enum APIList
{
	/** The primary communication bridge. */
	COMM_BRIDGE,
	
	/** The current chore. */
	CURRENT_CHORE,
	
	/** Start of non-system use objects. */
	START_OF_NON_SYSTEM,
	
	/** Access to the clock. */
	CLOCK,
	
	/** Chores. */
	CHORES,
	
	/** Programs. */
	PROGRAMS,
	
	/** End. */
	;
}

