// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package squirreljme.mle.forms;

import cc.squirreljme.jvm.mle.UIFormShelf;
import cc.squirreljme.jvm.mle.brackets.UIDisplayBracket;
import cc.squirreljme.jvm.mle.brackets.UIFormBracket;
import cc.squirreljme.jvm.mle.brackets.UIItemBracket;
import cc.squirreljme.jvm.mle.constants.UIItemType;
import cc.squirreljme.jvm.mle.exceptions.MLECallError;
import cc.squirreljme.runtime.cldc.debug.Debugging;

/**
 * Tests that items cannot be used after deletion.
 *
 * @since 2020/07/19
 */
public class TestUseItemAfterDelete
	extends __BaseFormTest__
{
	/**
	 * {@inheritDoc}
	 * @since 2020/07/19
	 */
	@Override
	protected void uiTest(UIDisplayBracket __display, UIFormBracket __form)
	{
		// Create the item
		UIItemBracket item = UIFormShelf.itemNew(UIItemType.BUTTON);
		
		// Quickly delete it so it is not valid
		UIFormShelf.itemDelete(item);
		
		// Attempt to place it on the form
		try
		{
			UIFormShelf.formItemPosition(__form, item, 0);
		}
		catch (MLECallError e)
		{
			throw new FormTestException(e);
		}
	}
}
