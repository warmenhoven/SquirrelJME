// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.jit;

/**
 * This class provides access to the JIT, it is given a class, a set of target
 * instructions and provides a result which contains native code.
 *
 * For code generation performed by the JIT, this class is basically a factory
 * class which initializes the {@link JITTask}s which produce
 * {@link JITResult}s.
 *
 * @since 2016/06/15
 */
public final class JIT
{
	/**
	 * Initializes the JIT with the given settings.
	 *
	 * @since 2016/06/15
	 */
	public JIT()
	{
	}
}

