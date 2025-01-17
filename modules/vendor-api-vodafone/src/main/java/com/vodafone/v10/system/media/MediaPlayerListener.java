// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package com.vodafone.v10.system.media;

import cc.squirreljme.runtime.cldc.annotation.Api;

public interface MediaPlayerListener
{
	@Api
	int PLAYED = 0;
	
	@Api
	int STOPPED = 1;
	
	@Api
	int PAUSED = 2;
	
	@Api
	void mediaStateChanged(int var1);
}
