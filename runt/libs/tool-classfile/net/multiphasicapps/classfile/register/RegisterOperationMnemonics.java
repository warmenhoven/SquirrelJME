// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.classfile.register;

/**
 * Mnemonics for register operations.
 *
 * @since 2019/03/17
 */
public final class RegisterOperationMnemonics
{
	/**
	 * Not used.
	 *
	 * @since 2019/03/17
	 */
	private RegisterOperationMnemonics()
	{
	}
	
	/**
	 * Translate the operation to the represented mnemonic.
	 *
	 * @param __op The operation to translate.
	 * @return The string representing the instruction mnemonic.
	 * @since 2019/03/17
	 */
	public static String toString(int __op)
	{
		switch (__op)
		{
			case RegisterOperationType.NOP:
				return "NOP";
			case RegisterOperationType.NARROW_COPY:
				return "NARROW_COPY";
			case RegisterOperationType.NARROW_COPY_AND_COUNT_DEST:
				return "NARROW_COPY_AND_COUNT_DEST";
			case RegisterOperationType.WIDE_COPY:
				return "WIDE_COPY";
			
				// Unknown
			default:
				return String.format("UNKNOWN_%04X", __op);
		}
	}
}

