// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
//     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU Affero General Public License v3+, or later.
// For more information see license.mkd.
// ---------------------------------------------------------------------------

package net.multiphasicapps.squirreljme.zips;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;

/**
 * This provides abstract access to a ZIP file.
 *
 * The used {@link SeekableByteChannel} is not sharable and reads lock on it
 * to prevent other threads from changing the position during a read.
 *
 * @since 2016/02/26
 */
public abstract class StandardZIPFile
{
	/** The base channel to read from. */
	protected final SeekableByteChannel channel;
	
	/** File channel if this is one. */
	protected final FileChannel filechannel;
	
	/** Read buffer to prevent a thousand allocations at the cost of speed. */
	protected final ByteBuffer readbuffer =
		ByteBuffer.allocateDirect(8);
	
	/**
	 * Initializes the zip file using the given byte channel which contains
	 * the ZIP file data.
	 *
	 * @param __sbc The source channel to read from.
	 * @throws IOException On read errors.
	 * @throws NullPointerException On null arguments.
	 * @throws ZIPFormatException If this is not a valid ZIP file.
	 * @since 2016/02/26
	 */
	public StandardZIPFile(SeekableByteChannel __sbc)
		throws IOException, NullPointerException, ZIPFormatException
	{
		// Check
		if (__sbc == null)
			throw new NullPointerException();
		
		// Set
		channel = __sbc;
		
		// If a file channel, some speed could be gained by not requiring a
		// channel lock
		if (channel instanceof FileChannel)
			filechannel = (FileChannel)channel;
		else
			filechannel = null;
	}
	
	/**
	 * Reads a little endian integer at the given position.
	 *
	 * @param __pos Position to read from.
	 * @return The read value.
	 * @throws IOException On read errors.
	 * @since 2016/03/02
	 */
	protected final int readInt(long __pos)
		throws IOException
	{
		synchronized (readbuffer)
		{
			return readRaw(__pos, 4).getInt();
		}
	}
	
	/**
	 * Reads a little endian long value at the given position.
	 *
	 * @param __pos Position to read from.
	 * @return The read value.
	 * @throws IOException On read errors.
	 * @since 2016/03/02
	 */
	protected final long readLong(long __pos)
		throws IOException
	{
		synchronized (readbuffer)
		{
			// ByteBuffer does not support reading long values sadly
			ByteBuffer val = readRaw(__pos, 8);
			
			// Build values
			int lo = val.getInt();
			return ((long)lo) | (((long)val.getInt()) << 32L);
		}
	}
	
	/**
	 * Reads raw data from the ZIP.
	 *
	 * @param __pos The position to read from.
	 * @param __len The number of bytes to read.
	 * @return The field {@code readbuffer} automatically position to the
	 * started and limited to the length.
	 * @throws IllegalArgumentException If the length exceeds the read buffer
	 * size, or the read length is zero or negative.
	 * @throws IOException On read errors.
	 * @since 2016/03/02
	 */
	protected final ByteBuffer readRaw(long __pos, int __len)
		throws IllegalArgumentException, IOException
	{
		// Check
		if (__len <= 0)
			throw new IllegalArgumentException("The read length is zero " +
				"or negative.");
		if (__len > readbuffer.capacity())
			throw new IllegalArgumentException("Read of " +
				"length " + __len + " exceeds the buffer size " +
				readbuffer.capacity() + ".");
		
		// Lock on the read buffer
		synchronized (readbuffer)
		{
			// Setup buffer for read
			ByteBuffer rv = readbuffer;
			rv.order(ByteOrder.LITTLE_ENDIAN);
			rv.clear();
			rv.limit(__len);
			
			// Read count
			int rc;
			
			// FileChannel has its own internal locking so it can be directly
			// read.
			if (filechannel != null)
				rc = filechannel.read(rv, __pos);
			
			// Otherwise lock on the channel since it may be used elsewhere or
			// shared between multiple ZIPs and threads potentially
			else
				synchronized (channel)
				{
					// Seek
					channel.position(__pos);
			
					// Perform the read
					rc = channel.read(rv);
				}
			
			// Check to make sure all the data was read
			if (rc < __len)
				throw new IOException("Short read, expected " + __len +
					" bytes but read " + Math.max(rc, 0) + " bytes.");
		
			// Flip the buffer
			rv.flip();
		
			// Return the input buffer
			return rv;
		}
	}
	
	/**
	 * Attempts to open this ZIP file using ZIP64 extensions first, then if
	 * that fails it will fall back to using ZIP32.
	 *
	 * @param __sbc The channel to attempt an open as a ZIP with.
	 * @throws IOException If the channel could not be read from.
	 * @throws NullPointerException On null arguments.
	 * @throws ZIPFormatException If the ZIP was not valid.
	 * @since 2016/03/02
	 */
	public static StandardZIPFile open(SeekableByteChannel __sbc)
		throws IOException, NullPointerException, ZIPFormatException
	{
		// Check
		if (__sbc == null)
			throw new NullPointerException();
		
		// Try opening as a 64-bit ZIP
		try
		{
			return new StandardZIP64File(__sbc);
		}
		
		// Not a ZIP64
		catch (ZIPFormatException zfe)
		{
			// Try treating it as a 32-bit ZIP
			try
			{
				return new StandardZIP32File(__sbc);
			}
			
			// Not a ZIP32 either
			catch (ZIPFormatException zfeb)
			{
				zfeb.addSuppressed(zfe);
				throw zfeb;
			}
		}
	}
}

