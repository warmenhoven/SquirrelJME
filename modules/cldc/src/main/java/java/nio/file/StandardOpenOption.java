// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package java.nio.file;

import cc.squirreljme.runtime.cldc.annotation.Api;
import cc.squirreljme.runtime.cldc.debug.Debugging;

@Api
public enum StandardOpenOption
	implements OpenOption
{
	@Api
	APPEND(),
	
	@Api
	CREATE(),
	
	@Api
	CREATE_NEW(),
	
	@Api
	DELETE_ON_CLOSE(),
	
	@Api
	DSYNC(),
	
	@Api
	READ(),
	
	@Api
	SPARSE(),
	
	@Api
	SYNC(),
	
	@Api
	TRUNCATE_EXISTING(),
	
	@Api
	WRITE(),
	
	/** End. */
	;
	
	StandardOpenOption()
	{
		throw Debugging.todo();
	}
}

