// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import net.multiphasicapps.squirreljme.kernel.Kernel;

/**
 * This is the base class for the kernel interfaces which are defined by
 * systems to provide anything that the default kernel does not provide
 * when it comes to interfaces. The kernel manages the processes for the
 * system along with inter-process communication and inter-process objects.
 *
 * @since 2016/05/14
 */
public abstract class Kernel
{
	/** The kernel process. */
	private final KernelProcess _kernelprocess;
	
	/** Kernel processes. */
	private final List<KernelProcess> _processes =
		new LinkedList<>();
	
	/**
	 * Initializes the base kernel interface.
	 *
	 * @since 2016/05/16
	 */
	public Kernel()
	{
		// Setup kernel process
		KernelProcess kp = new KernelProcess(this, true);
		this._kernelprocess = kp;
		
		// Kernel process is a global one
		this._processes.add(kp);
		
		// Register the current thread
		kp.addThread(Thread.currentThread());
	}
	
	/**
	 * Attempts to quit the kernel, if the kernel cannot be quit then nothing
	 * happens.
	 *
	 * @since 2016/05/18
	 */
	public abstract void quitKernel();
	
	/**
	 * Returns the process which is associated with the current thread.
	 *
	 * @return The process for the current thread, if {@code null} then no
	 * process owns the thread.
	 * @since 2016/05/16
	 */
	public final KernelProcess currentProcess()
	{
		return processByThread(Thread.currentThread());
	}
	
	/**
	 * Returns the kernel process.
	 *
	 * @return The kernel process.
	 * @since 2016/05/16
	 */
	public final KernelProcess kernelProcess()
	{
		return _kernelprocess;
	}
	
	/**
	 * Locates the process that owns the given thread.
	 *
	 * @param __t The thread to get the process of.
	 * @return The process which owns the given thread or {@code null} if no
	 * process is bound to it.
	 * @since 2016/05/16
	 */
	public final KernelProcess processByThread(Thread __t)
	{
		// Lock
		List<KernelProcess> kps = this._processes;
		synchronized (kps)
		{
			// Go through all of them
			for (KernelProcess kp : kps)
				if (kp.containsThread(__t))
					return kp;
		}
		
		// Not found
		return null;
	}
	
	/**
	 * Does not return until there are no processes remaining.
	 *
	 * @throws InterruptedException If the process was interrupted during
	 * blocking.
	 * @since 2016/05/16
	 */
	public final void untilProcessless()
		throws InterruptedException
	{
		// Loop for a long time
		List<KernelProcess> processes = this._processes;
		Thread curt = Thread.currentThread();
		for (;;)
		{
			// {@squirreljme.error AY06 Thread interrupted.}
			if (curt.isInterrupted())
				throw new InterruptedException("AY06");
			
			// Lock
			synchronized (processes)
			{
				// Go through all processes
				Iterator<KernelProcess> it = processes.iterator();
				while (it.hasNext())
				{
					KernelProcess p = it.next();
					
					// If all threads in the process are dead, then remove
					// the process.
					if (!p.areAnyThreadsAlive())
						it.remove();
				}
				
				// Empty?
				if (processes.isEmpty())
					return;
			}
			
			// Sleep for a bit
			Thread.sleep(750L);
		}
	}
}

