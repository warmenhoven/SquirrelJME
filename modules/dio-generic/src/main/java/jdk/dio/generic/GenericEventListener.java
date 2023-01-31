// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package jdk.dio.generic;

import cc.squirreljme.runtime.cldc.annotation.Api;
import jdk.dio.DeviceEventListener;

@Api
public interface GenericEventListener
	extends DeviceEventListener
{
	@Api
	void eventDispatched(GenericEvent __a);
}


