// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.kernel.impl.jvm.javase;

import java.lang.ref.Reference;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.multiphasicapps.squirreljme.classpath.ClassUnitProvider;
import net.multiphasicapps.squirreljme.classpath.jar.fs.FSJarClassUnitProvider;
import net.multiphasicapps.squirreljme.kernel.impl.autoboot.AutoBootKernel;
import net.multiphasicapps.squirreljme.kernel.impl.jvm.javase.swing.
	SwingManager;
import net.multiphasicapps.squirreljme.kernel.Kernel;
import net.multiphasicapps.squirreljme.kernel.KernelProcess;
import net.multiphasicapps.squirreljme.kernel.KIOException;
import net.multiphasicapps.squirreljme.kernel.KIOSocket;
import net.multiphasicapps.squirreljme.ui.PIManager;
import net.multiphasicapps.squirreljme.ui.UIManager;

/**
 * This contains the launcher used by the host Java SE system.
 *
 * @since 2016/05/14
 */
public class JVMJavaSEKernel
	extends AutoBootKernel
{
	/** The display manager which utilizes Swing. */
	protected final UIManager displaymanager;
	
	/**
	 * This initializes the launcher which uses an existing full Java SE JVM.
	 *
	 * @param __args The arguments to the launcher.
	 * @since 2016/05/14
	 */
	public JVMJavaSEKernel(String... __args)
	{
		// Must always exist
		if (__args == null)
			__args = new String[0];
		
		// Setup the display manager
		this.displaymanager = new UIManager(new SwingManager(this));
		
		// Finished booting
		bootFinished(JVMJavaSEKernel.class);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/05/25
	 */
	@Override
	protected ClassUnitProvider[] classUnitProviders()
	{
		return new ClassUnitProvider[]
			{
				new FSJarClassUnitProvider(
					Paths.get(System.getProperty("user.dir")))
			};
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/05/21
	 */
	@Override
	protected UIManager getDisplayManager()
	{
		return this.displaymanager;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2016/05/18
	 */
	@Override
	public void quitKernel()
	{
		// Can quit
		System.exit(0);
	}
}

