// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package dev.shadowtail.jarfile;

import java.io.IOException;
import java.io.InputStream;

/**
 * The table of contents for a given JAR, contains offsets and otherwise for
 * the data.
 *
 * @since 2020/12/08
 */
public final class MinimizedJarTOC
{
	/**
	 * Decodes the input JAR table of contents.
	 * 
	 * @param __in The stream to decode from.
	 * @return The resultant table of contents.
	 * @throws IOException On read errors.
	 * @throws NullPointerException On null arguments.
	 * @since 2020/12/09
	 */
	public static MinimizedJarTOC decode(InputStream __in)
		throws IOException, NullPointerException
	{
		if (__in == null)
			throw new NullPointerException("NARG");
		
		throw cc.squirreljme.runtime.cldc.debug.Debugging.todo();
	}
}
