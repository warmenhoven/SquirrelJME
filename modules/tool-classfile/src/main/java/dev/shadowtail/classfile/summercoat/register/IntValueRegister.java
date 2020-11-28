// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package dev.shadowtail.classfile.summercoat.register;

/**
 * This is a register that contains an integer value.
 *
 * @since 2020/11/28
 */
public final class IntValueRegister
	extends Register
{
	/** The zero register. */
	public static final IntValueRegister ZERO =
		IntValueRegister.of(0);
	
	/**
	 * Initializes the basic register.
	 *
	 * @param __register The register to get.
	 * @since 2020/11/24
	 */
	public IntValueRegister(int __register)
	{
		super(__register);
	}
	
	/**
	 * Sets up an int value register.
	 * 
	 * @param __register The register to use.
	 * @return The int value register.
	 * @since 2020/11/28
	 */
	public static IntValueRegister of(int __register)
	{
		return new IntValueRegister(__register);
	}
}