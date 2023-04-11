// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package javax.bluetooth;

import cc.squirreljme.runtime.cldc.annotation.Api;
import cc.squirreljme.runtime.cldc.debug.Debugging;
import java.io.IOException;

@Api
public class ServiceRegistrationException
	extends IOException
{
	@Api
	public ServiceRegistrationException()
	{
		throw Debugging.todo();
	}
	
	@Api
	@SuppressWarnings("unused")
	public ServiceRegistrationException(String __s)
	{
		throw Debugging.todo();
	}
}
