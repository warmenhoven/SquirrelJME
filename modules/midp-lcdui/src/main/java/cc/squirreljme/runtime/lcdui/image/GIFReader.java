// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.runtime.lcdui.image;

import cc.squirreljme.runtime.cldc.debug.Debugging;
import java.io.IOException;
import javax.microedition.lcdui.Image;
import net.multiphasicapps.io.ExtendedDataInputStream;

/**
 * This class is used to read and parse GIF images.
 *
 * @since 2021/12/04
 */
public class GIFReader
{
	/** The source data stream. */
	protected final ExtendedDataInputStream in;
	
	/** The factory used to create the final images. */
	protected final ImageFactory factory;
	
	/**
	 * Initializes the GIF reader.
	 * 
	 * @param __in The stream to read from.
	 * @param __factory The factory used for creating images.
	 * @throws NullPointerException On null arguments.
	 * @since 2021/12/04
	 */
	public GIFReader(ExtendedDataInputStream __in, ImageFactory __factory)
		throws NullPointerException
	{
		if (__in == null)
			throw new NullPointerException("NARG");
		
		this.in = __in;
		this.factory = __factory;
	}
	
	/**
	 * Parses the image.
	 * 
	 * @return The resultant image.
	 * @throws IOException On null arguments.
	 * @since 2021/12/04
	 */
	protected Image parse()
		throws IOException
	{
		
		
		throw Debugging.todo();
	}
}
