// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.interpreter;

import net.multiphasicapps.descriptors.MethodSymbol;

/**
 * This represents an interpreted method within a class.
 *
 * @since 2016/03/01
 */
public class JVMMethod
	extends JVMMember<MethodSymbol>
{
	/**
	 * Initializes the interpreted method.
	 *
	 * @param __owner The class which owns this method.
	 * @throws NullPointerException On null arguments.
	 * @since 2016/03/01
	 */
	JVMMethod(JVMClass __owner)
		throws NullPointerException
	{
		super(__owner, MethodSymbol.class);
	}
	
	/**
	 * Is this method public?
	 *
	 * @return {@code true} if this method is public.
	 * @since 2016/03/01
	 */
	public boolean isPublic()
	{
		throw new Error("TODO");
	}
	
	/**
	 * Is this method static?
	 *
	 * @return {@code true} if this method is static.
	 * @since 2016/03/01
	 */
	public boolean isStatic()
	{
		throw new Error("TODO");
	}
}

