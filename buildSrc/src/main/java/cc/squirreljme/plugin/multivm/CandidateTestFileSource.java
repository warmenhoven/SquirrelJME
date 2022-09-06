// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.plugin.multivm;

import cc.squirreljme.plugin.util.FileLocation;
import java.util.Collection;
import lombok.AllArgsConstructor;

/**
 * Candidate test file sources.
 *
 * @since 2022/09/05
 */
@AllArgsConstructor
public final class CandidateTestFileSource
{
	/** Primary set of sources? */
	public final boolean primary;
	
	/** The files making up the sources. */
	public final Collection<FileLocation> collection;
}