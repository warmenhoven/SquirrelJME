// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.plugin.general;

import cc.squirreljme.plugin.util.FossilExe;
import org.gradle.api.Action;
import org.gradle.api.Task;

/**
 * Task action for {@link FossilExeTask}.
 *
 * @since 2022/07/10
 */
public class FossilExeTaskAction
	implements Action<Task>
{
	/**
	 * {@inheritDoc}
	 *
	 * @since 2020/06/24
	 */
	@Override
	public void execute(Task __task)
	{
		__task.getLogger().lifecycle(
			FossilExe.instance().exePath().toString());
	}
}
