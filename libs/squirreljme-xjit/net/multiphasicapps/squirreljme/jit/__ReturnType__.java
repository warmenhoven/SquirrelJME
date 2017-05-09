// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.jit;

/**
 * This represents the type of return that an instruction performs after it is
 * executed.
 *
 * @since 2017/05/09
 */
public enum __ReturnType__
{
	/** Do not flow to the next instruction, jumps may be specified. */
	NONE,
	
	/** Continue to the next instruction. */
	NEXT,
	
	/** Return from the current method. */
	RETURN,
	
	/** Throw an exception from the current method. */
	THROW,
	
	/** End. */
	;
}

