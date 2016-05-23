// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.ui;

/**
 * This represents a menu which contains items and other menus and is used as
 * a quick means to select specific options when needed.
 *
 * @since 2016/05/23
 */
public final class UIMenu
	extends UIElement
{
	/** The display this is associated with. */
	private volatile UIDisplay _display;
	
	/**
	 * This initializes the menu.
	 *
	 * @param __dm The owning display manager.
	 * @since 2016/05/23
	 */
	UIMenu(UIDisplayManager __dm)
	{
		super(__dm);
	}
	
	/**
	 * Returns the display which is associated with this menu.
	 *
	 * @return The display which is associated with this menu or {@code null}
	 * if there is no currently associated display.
	 * @since 2016/05/23
	 */
	public UIDisplay getDisplay()
	{
		// Lock
		synchronized (this.lock)
		{
			return this._display;
		}
	}
	
	/**
	 * Sets the new display that this menu is associated with.
	 *
	 * @param __disp The display to associate with, if {@code null} then the
	 * association is removed.
	 * @return The old display this was associated with.
	 * @since 2016/05/23
	 */
	UIDisplay __setDisplay(UIDisplay __disp)
	{
		// Lock
		synchronized (this.lock)
		{
			UIDisplay rv = this._display;
			this._display = __disp;
			return rv;
		}
	}
}

