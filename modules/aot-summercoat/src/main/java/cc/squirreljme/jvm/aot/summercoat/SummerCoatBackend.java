// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.jvm.aot.summercoat;

import cc.squirreljme.jvm.aot.Backend;
import cc.squirreljme.jvm.aot.CompileSettings;
import cc.squirreljme.jvm.aot.LinkGlob;
import cc.squirreljme.jvm.aot.RomSettings;
import cc.squirreljme.jvm.aot.summercoat.target.TargetBang;
import cc.squirreljme.runtime.cldc.debug.Debugging;
import cc.squirreljme.vm.VMClassLibrary;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ServiceLoader;

/**
 * Backend for SummerCoat compilation.
 *
 * @since 2022/08/05
 */
public class SummerCoatBackend
	implements Backend
{
	/**
	 * {@inheritDoc}
	 * @since 2022/08/05
	 */
	@Override
	public void compileClass(LinkGlob __glob, String __name, InputStream __in,
		OutputStream __out)
		throws IOException, NullPointerException
	{
		if (__glob == null || __name == null ||
			__in == null || __out == null)
			throw new NullPointerException("NARG");
		
		throw Debugging.todo();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2022/08/05
	 */
	@Override
	public void dumpGlob(byte[] __inGlob, String __name, PrintStream __out)
		throws IOException, NullPointerException
	{
		throw Debugging.todo();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2022/08/05
	 */
	@Override
	public LinkGlob linkGlob(CompileSettings __settings, String __name,
		OutputStream __out)
		throws IOException, NullPointerException
	{
		if (__settings == null || __name == null || __out == null)
			throw new NullPointerException("NARG");
			
		// Lookup the target bang, this specifies the target we are aiming for
		TargetBang targetbang = new TargetBang(__settings.variant);
		
		// Find handler for this target bang
		SummerCoatHandlerFactory foundHandler = null;
		for (SummerCoatHandlerFactory handler :
			ServiceLoader.load(SummerCoatHandlerFactory.class))
			if (handler.supportsBang(targetbang))
			{
				foundHandler = handler;
				break;
			}
		
		// {@squirreljme.error RA04 No handler supports the given target
		// bang. (The target bang)}
		if (foundHandler == null)
			throw new IllegalArgumentException("RA04 " + targetbang);
		
		throw Debugging.todo();
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2022/08/05
	 */
	@Override
	public String name()
	{
		return "summercoat";
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2022/08/05
	 */
	@Override
	public void rom(RomSettings __settings, OutputStream __out,
		VMClassLibrary... __libs)
		throws IOException, NullPointerException
	{
		throw Debugging.todo();
	}
}
