// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.jvm.mle.constants;

import cc.squirreljme.runtime.cldc.annotation.Exported;

/**
 * Specifies the byte order that is used.
 *
 * @since 2021/02/09
 */
@Exported
public interface ByteOrderType
{
	/** Big endian. */
	@Exported
	byte BIG_ENDIAN =
		0;
		
	/** Little endian. */
	@Exported
	byte LITTLE_ENDIAN =
		1;
}
