// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
//     Copyright (C) Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.runtime.javase.lcdui;

import cc.squirreljme.runtime.lcdui.gfx.PixelFormat;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.IndexColorModel;
import java.awt.Transparency;

/**
 * The type of color information to use.
 *
 * @since 2018/03/24
 */
public final class ColorInfo
{
	/**
	 * {@squirreljme.error cc.squirreljme.runtime.javase.lcdui.pixelformat=f
	 * This selects the format to use for the display when creating images.}
	 */
	public static final String PIXEL_FORMAT_PROPERTY =
		"cc.squirreljme.runtime.javase.lcdui.pixelformat";
	
	/** The pixel format of the frame. */
	public static final PixelFormat PIXEL_FORMAT;
	
	/** The type of image to create. */
	public static final int IMAGE_TYPE;
	
	/** The color model to use. */
	public static final IndexColorModel COLOR_MODEL;
	
	/**
	 * Initializes the color information.
	 *
	 * @since 2018/03/24
	 */
	static
	{
		
		// Set the pixel format
		PixelFormat pf;
		PIXEL_FORMAT = (pf = PixelFormat.valueOf(
			System.getProperty(PIXEL_FORMAT_PROPERTY, "INT_RGB888")));
		
		// Set the type of data to use for buffered images
		int btype;
		IndexColorModel icm;
		switch (pf)
		{
			case BYTE_INDEXED1:
				btype = BufferedImage.TYPE_BYTE_BINARY;
				icm = new IndexColorModel(1, 2, new int[]{
					0xFF000000, 0xFFFFFFFF}, 0, false, Transparency.OPAQUE,
					DataBuffer.TYPE_BYTE);
				break;
				
			case BYTE_INDEXED2:
				btype = BufferedImage.TYPE_BYTE_BINARY;
				icm = new IndexColorModel(2, 4, new int[]{
					0xFF_000000,
					0xFF_55FFFF,
					0xFF_FF55FF,
					0xFF_FFFFFF}, 0, false, Transparency.OPAQUE,
					DataBuffer.TYPE_BYTE);
				break;
				
			case BYTE_INDEXED4:
				btype = BufferedImage.TYPE_BYTE_BINARY;
				icm = new IndexColorModel(4, 16, new int[]{
					0xFF_000000,
					0xFF_808080,
					0xFF_C0C0C0,
					0xFF_FFFFFF,
					0xFF_800000,
					0xFF_FF0000,
					0xFF_808000,
					0xFF_FFFFFF,
					0xFF_008000,
					0xFF_00FF00,
					0xFF_008080,
					0xFF_00FFFF,
					0xFF_000080,
					0xFF_0000FF,
					0xFF_800080,
					0xFF_FF00FF,
					}, 0, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
				break;
				
			case BYTE_INDEXED8:
				btype = BufferedImage.TYPE_BYTE_INDEXED;
				throw new todo.TODO();
				
			case SHORT_RGB565:
				btype = BufferedImage.TYPE_USHORT_565_RGB;
				icm = null;
				break;
				
			case INT_RGBA8888:
				btype = BufferedImage.TYPE_INT_ARGB;
				icm = null;
				break;
				
			case INT_RGB888:
				btype = BufferedImage.TYPE_INT_RGB;
				icm = null;
				break;
			
				// {@squirreljme.error AF09 Cannot use the specified pixel
				// format. (The pixel format to use)}
			case BYTE_RGB332:
			case SHORT_INDEXED16:
			case SHORT_ARGB2222:
				throw new RuntimeException(String.format("AF09 %s", pf));
			
			default:
				throw new RuntimeException("OOPS");
		}
		
		// Store
		IMAGE_TYPE = btype;
		COLOR_MODEL = icm;
	}
	
	/**
	 * Creates a buffered image using the color parameters.
	 *
	 * @param __w The width.
	 * @param __h The height.
	 * @return The resulting image.
	 * @since 2018/03/24
	 */
	public static BufferedImage create(int __w, int __h)
	{
		IndexColorModel icm = ColorInfo.COLOR_MODEL;
		if (icm != null)
			return new BufferedImage(__w, __h, ColorInfo.IMAGE_TYPE, icm);
		return new BufferedImage(__w, __h, ColorInfo.IMAGE_TYPE);
	}
}

