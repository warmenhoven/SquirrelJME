// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.narf.interpreter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import net.multiphasicapps.descriptors.ClassLoaderNameSymbol;
import net.multiphasicapps.descriptors.ClassNameSymbol;
import net.multiphasicapps.narf.classinterface.NCIClass;
import net.multiphasicapps.narf.NARFClassLibrary;

/**
 * This is the core of the interpreter, this dispatches and maintains all of
 * the threads.
 *
 * @since 2016/04/21
 */
public class NICore
{
	/** The library which contains classes to load. */
	protected final NILibrary classlib;
	
	/** The mapping of real threads to interpreter threads. */
	protected final Map<Thread, NIThread> threadmap =
		new HashMap<>();
	
	/** Already loaded binary classes? */
	protected final Map<ClassNameSymbol, Reference<NIClass>> loaded =
		new HashMap<>();
	
	/** Is the virtual machine running? */
	private volatile boolean _isrunning;
	
	/**
	 * This initializes the interpreter.
	 *
	 * @param __cl The library which contains classes.
	 * @param __main The main class.
	 * @param __args Main program arguments.
	 */
	public NICore(NILibrary __cl,
		ClassLoaderNameSymbol __main, String... __args)
		throws NullPointerException
	{
		// Check
		if (__cl == null || __main == null || __args == null)
			throw new NullPointerException("NARG");
		
		// Set
		classlib = __cl;
		
		// Map in the current thread as the system "daemon" thread, although
		// Java ME lacks daemon threads, the interpreter internally will need
		// a thread so it can determine where execution is being performed.
		Thread mt = Thread.currentThread();
		threadmap.put(mt, new NIThread(this, mt));
		
		// Locate the main class
		NIClass maincl = initClass(__main.asClassName());
		
		// {@squirreljme.error NI08 The main class could not be found.
		// (The main class)}
		if (maincl == null)
			throw new IllegalArgumentException(String.format("NI08 %s",
				__main));
		
		if (true)
			throw new Error("TODO");
		
		// Is running
		_isrunning = true;
	}
	
	/**
	 * Locates and initializes the given class.
	 *
	 * @param __cn The class to initialize.
	 * @return The initialized interpreter class.
	 * @throws NullPointerException On null arguments.
	 * @since 2016/04/21
	 */
	public NIClass initClass(ClassNameSymbol __cn)
		throws NullPointerException
	{
		// Check
		if (__cn == null)
			throw new NullPointerException("NARG");
		
		// Lock on the loaded classes
		Map<ClassNameSymbol, Reference<NIClass>> map = loaded;
		synchronized (map)
		{
			// Get ref
			Reference<NIClass> ref = map.get(__cn);
			NIClass rv;
			
			// Needs to be loaded?
			if (ref == null || null == (rv = ref.get()))
			{
				// An array
				if (__cn.isArray())
					throw new Error("TODO");
				
				// Primitive type
				else if (__cn.isPrimitive())
					throw new Error("TODO");
				
				// Normal class
				else
					rv = new NIClass(this,
						classlib.loadClass(__cn.asBinaryName()), __cn, map);
			}
			
			// Return it
			return rv;
		}
	}
	
	/**
	 * Returns {@code true} if there is at least one thread running.
	 *
	 * @return If the virtual machine is still running.
	 * @since 2016/04/21
	 */
	public boolean isRunning()
	{
		return _isrunning;
	}
	
	/**
	 * Returns the class library interface which is used to obtain classes for
	 * initialization.
	 *
	 * @return The class library interface.
	 * @since 2016/04/22
	 */
	public NILibrary library()
	{
		return classlib;
	}
	
	/**
	 * Obtains the interpreter based thread which wraps the given thread.
	 *
	 * @param __t The thread to obtain the interpreter based thread from.
	 * @return The interpreter thread which owns the given thread, {@code null}
	 * is returned if there is no mapped thread.
	 * @throws NullPointerException On null arguments.
	 * @since 2016/04/21
	 */
	public NIThread thread(Thread __t)
		throws NullPointerException
	{
		// Check
		if (__t == null)
			throw new NullPointerException("NARG");
		
		// Lock on the thread map
		Map<Thread, NIThread> tm = threadmap;
		synchronized (tm)
		{
			return tm.get(__t);
		}
	}
}

