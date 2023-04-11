// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package jdk.dio;

import cc.squirreljme.runtime.cldc.annotation.Api;
import java.io.IOException;

@SuppressWarnings("DuplicateThrows")
@Api
public interface Transactional
{
	@Api
	void begin()
		throws ClosedDeviceException, IOException;
	
	@Api
	void end()
		throws ClosedDeviceException, IOException;
}


