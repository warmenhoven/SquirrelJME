// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package squirreljme.mle;

import cc.squirreljme.jvm.mle.BusTransportShelf;
import cc.squirreljme.jvm.mle.TaskShelf;
import cc.squirreljme.jvm.mle.brackets.BusTransportBracket;
import net.multiphasicapps.tac.TestRunnable;

/**
 * Tests that the IPC pipe works properly when communicating within the same
 * task.
 *
 * @since 2022/12/03
 */
public class TestIpcPipe
	extends TestRunnable
{
	/**
	 * {@inheritDoc}
	 * @since 2022/12/03
	 */
	@Override
	public void test()
	{
		// Setup listening connection
		BusTransportBracket listen =
			BusTransportShelf.listen("test", 0);
		
		this.secondary("key", "value");
	}
}
