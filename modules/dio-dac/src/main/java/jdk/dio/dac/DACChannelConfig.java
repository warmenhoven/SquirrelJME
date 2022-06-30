// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package jdk.dio.dac;

import cc.squirreljme.runtime.cldc.annotation.ApiDefinedDeprecated;
import cc.squirreljme.runtime.cldc.debug.Debugging;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jdk.dio.DeviceConfig;

public final class DACChannelConfig
	implements DeviceConfig<DACChannel>, DeviceConfig.HardwareAddressing
{
	@ApiDefinedDeprecated
	public DACChannelConfig(int __a, int __b, int __c, int __d)
	{
		throw Debugging.todo();
	}
	
	@ApiDefinedDeprecated
	public DACChannelConfig(String __a, int __b, int __c, int __d)
	{
		throw Debugging.todo();
	}
	
	@Override
	public boolean equals(Object __a)
	{
		throw Debugging.todo();
	}
	
	public int getChannelNumber()
	{
		throw Debugging.todo();
	}
	
	@Override
	public String getControllerName()
	{
		throw Debugging.todo();
	}
	
	@Override
	public int getControllerNumber()
	{
		throw Debugging.todo();
	}
	
	public int getOutputBufferSize()
	{
		throw Debugging.todo();
	}
	
	public int getResolution()
	{
		throw Debugging.todo();
	}
	
	public int getSamplingInterval()
	{
		throw Debugging.todo();
	}
	
	public double getScaleFactor()
	{
		throw Debugging.todo();
	}
	
	@Override
	public int hashCode()
	{
		throw Debugging.todo();
	}
	
	@Override
	public int serialize(OutputStream __a)
		throws IOException
	{
		if (false)
			throw new IOException();
		throw Debugging.todo();
	}
	
	public static DACChannelConfig deserialize(InputStream __a)
		throws IOException
	{
		if (false)
			throw new IOException();
		throw Debugging.todo();
	}
	
	public static final class Builder
	{
		public Builder()
		{
			throw Debugging.todo();
		}
		
		public DACChannelConfig build()
		{
			throw Debugging.todo();
		}
		
		public Builder setChannelNumber(int __a)
		{
			throw Debugging.todo();
		}
		
		public Builder setControllerName(String __a)
		{
			throw Debugging.todo();
		}
		
		public Builder setControllerNumber(int __a)
		{
			throw Debugging.todo();
		}
		
		public Builder setOutputBufferSize(int __a)
		{
			throw Debugging.todo();
		}
		
		public Builder setResolution(int __a)
		{
			throw Debugging.todo();
		}
		
		public Builder setSamplingInterval(int __a)
		{
			throw Debugging.todo();
		}
		
		public Builder setScaleFactor(double __a)
		{
			throw Debugging.todo();
		}
	}
}


