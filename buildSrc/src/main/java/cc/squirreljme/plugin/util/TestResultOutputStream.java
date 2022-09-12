// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.plugin.util;

import org.gradle.api.internal.tasks.testing.DefaultTestOutputEvent;
import org.gradle.api.internal.tasks.testing.TestResultProcessor;
import org.gradle.api.tasks.testing.TestOutputEvent;

/**
 * Pushes lines to result output.
 *
 * @since 2022/09/11
 */
public class TestResultOutputStream
	extends LinePushOutputStream
{
	/** Line ending character. */
	public static final String LINE_ENDING =
		System.getProperty("line.ending");
	
	/** The destination output. */
	protected final TestOutputEvent.Destination destination;
	
	/** The test ID. */
	protected final Object id;
	
	/** The processor for test results. */
	protected final TestResultProcessor processor;
	
	/**
	 * Initializes the output stream for test result output.
	 * 
	 * @param __processor The processor for test results.
	 * @param __id The test ID.
	 * @param __destination The processor for test results.
	 * @throws NullPointerException On null arguments.
	 * @since 2022/09/11
	 */
	public TestResultOutputStream(TestResultProcessor __processor, Object __id,
		TestOutputEvent.Destination __destination)
		throws NullPointerException
	{
		if (__processor == null || __id == null || __destination == null)
			throw new NullPointerException("NARG");
		
		this.processor = __processor;
		this.id = __id;
		this.destination = __destination;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2022/09/11
	 */
	@Override
	protected void push(String __string)
	{
		TestResultProcessor processor = this.processor;
		Object id = this.id;
		TestOutputEvent.Destination destination = this.destination;
		
		// Output string and the line ending
		processor.output(id,
			new DefaultTestOutputEvent(destination, __string));
		processor.output(id,
			new DefaultTestOutputEvent(destination,
				TestResultOutputStream.LINE_ENDING));
	}
}
