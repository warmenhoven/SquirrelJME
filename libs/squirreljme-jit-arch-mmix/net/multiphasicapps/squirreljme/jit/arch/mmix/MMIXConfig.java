// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.jit.arch.mmix;

import java.io.InputStream;
import java.util.Map;
import net.multiphasicapps.squirreljme.jit.arch.MachineCodeOutput;
import net.multiphasicapps.squirreljme.jit.bin.FragmentBuilder;
import net.multiphasicapps.squirreljme.jit.JITConfig;
import net.multiphasicapps.squirreljme.jit.JITConfigKey;
import net.multiphasicapps.squirreljme.jit.JITConfigValue;
import net.multiphasicapps.squirreljme.jit.JITException;

/**
 * This configuration is used for MMIX based systems.
 *
 * @since 2017/08/11
 */
public class MMIXConfig
	extends JITConfig
{
	/**
	 * Initializes the MIPS configuration.
	 *
	 * @param __o The input JIT configuration.
	 * @since 2017/08/11
	 */
	public MMIXConfig(Map<JITConfigKey, JITConfigValue> __o)
	{
		super(__o);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2017/08/11
	 */
	@Override
	public MachineCodeOutput createMachineCodeOutput(FragmentBuilder __f)
		throws JITException, NullPointerException
	{
		// Check
		if (__f == null)
			throw new NullPointerException("NARG");
		
		return new MMIXCodeOutput(this, __f);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2017/08/11
	 */
	@Override
	protected JITConfigKey[] targetDefaultKeys()
	{
		return new JITConfigKey[]
			{
			};
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2017/08/11
	 */
	@Override
	protected JITConfigValue targetTranslateValue(JITConfigKey __k,
		JITConfigValue __v)
		throws NullPointerException
	{
		// Check
		if (__k == null)
			throw new NullPointerException("NARG");
		
		// Translate?
		switch (__k.toString())
		{
				// Unchanged
			default:
				break;
		}
		
		// Unchanged
		return __v;
	}
}

