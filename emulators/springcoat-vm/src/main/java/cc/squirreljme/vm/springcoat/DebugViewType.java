// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.vm.springcoat;

import cc.squirreljme.jdwp.JDWPState;
import cc.squirreljme.jdwp.JDWPViewType;
import java.lang.ref.Reference;

/**
 * A viewer around class types.
 *
 * @since 2021/04/10
 */
public class DebugViewType
	implements JDWPViewType
{
	/** The state of the debugger. */
	protected final Reference<JDWPState> state;
	
	/**
	 * Initializes the type viewer.
	 * 
	 * @param __state The state.
	 * @throws NullPointerException On null arguments.
	 * @since 2021/04/10
	 */
	public DebugViewType(Reference<JDWPState> __state)
	{
		if (__state == null)
			throw new NullPointerException("NARG");
		
		this.state = __state;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2021/04/11
	 * @param __which
	 */
	@Override
	public Object componentType(Object __which)
	{
		return ((SpringClass)__which).componentType();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2021/04/11
	 * @param __which
	 */
	@Override
	public int flags(Object __which)
	{
		return ((SpringClass)__which).flags().toJavaBits();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2021/04/10
	 * @param __which
	 */
	@Override
	public boolean isValid(Object __which)
	{
		return (__which instanceof SpringClass);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2021/04/11
	 * @param __which
	 */
	@Override
	public String signature(Object __which)
	{
		return ((SpringClass)__which).name.field().toString();
	}
}
