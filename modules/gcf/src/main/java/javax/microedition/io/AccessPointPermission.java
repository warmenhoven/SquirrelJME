// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package javax.microedition.io;

import cc.squirreljme.runtime.cldc.debug.Debugging;
import java.security.BasicPermission;
import java.security.Permission;

public final class AccessPointPermission
	extends BasicPermission
{
	public AccessPointPermission(String __a)
	{
		super((String)null);
		throw Debugging.todo();
	}
	
	@Override
	public boolean implies(Permission __a)
	{
		throw Debugging.todo();
	}
}


