// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.jvm.mle.brackets;

import cc.squirreljme.jvm.mle.annotation.GhostObject;
import cc.squirreljme.runtime.cldc.annotation.Api;
import cc.squirreljme.runtime.cldc.annotation.SquirrelJMEVendorApi;

/**
 * This represents a single display head which {@link UIFormBracket}s may be
 * attached to.
 *
 * @since 2020/07/01
 */
@SquirrelJMEVendorApi
@GhostObject
public interface UIDisplayBracket
	extends UIDrawableBracket
{
}
