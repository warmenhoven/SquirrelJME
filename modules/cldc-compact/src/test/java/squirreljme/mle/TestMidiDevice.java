// ---------------------------------------------------------------------------
// SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package squirreljme.mle;

import cc.squirreljme.jvm.mle.MidiShelf;
import cc.squirreljme.jvm.mle.brackets.MidiDeviceBracket;
import cc.squirreljme.jvm.mle.brackets.MidiPortBracket;
import cc.squirreljme.runtime.cldc.debug.Debugging;
import net.multiphasicapps.tac.TestRunnable;

/**
 * Tests that all MIDI devices, if any, work correctly.
 *
 * @since 2022/04/21
 */
public class TestMidiDevice
	extends TestRunnable
{
	/** MIDI note on event. */
	private static final byte[] _MIDI_NOTE_ON =
		new byte[]{(byte)0b1001_0000, 60, 127};
	
	/** MIDI note off event. */
	private static final byte[] _MIDI_NOTE_OFF =
		new byte[]{(byte)0b1000_0000, 60, 127};
	
	/**
	 * {@inheritDoc}
	 *
	 * @since 2022/04/21
	 */
	@Override
	public void test()
	{
		// Go through every MIDI device
		for (MidiDeviceBracket device : MidiShelf.devices())
		{
			// Try to get the device name
			System.err.printf("MIDI Device: %s%n",
				MidiShelf.deviceName(device));
			
			// And for every direction for that device
			for (Direction direction : Direction.values())
			{
				// And for every port in that direction
				for (MidiPortBracket port : MidiShelf.ports(device,
					direction.flag))
				{
					Debugging.debugNote("Ports: %s for %s%n",
						port, direction);
					
					switch (direction)
					{
						case RECEIVE:
							// Call it but receive no actual data
							MidiShelf.dataReceive(port,
								new byte[0], 0, 0);
							break;
						
							// Send the MIDI event over
						case TRANSMIT:
							MidiShelf.dataTransmit(port,
								TestMidiDevice._MIDI_NOTE_ON, 0,
								TestMidiDevice._MIDI_NOTE_ON.length);
							
							try
							{
								Thread.sleep(1_000);
							}
							catch (InterruptedException e)
							{
							}
							
							MidiShelf.dataTransmit(port,
								TestMidiDevice._MIDI_NOTE_OFF, 0,
								TestMidiDevice._MIDI_NOTE_OFF.length);
							break;
					}
				}
			}
		}
	}
	
	/**
	 * Indicates the direction of the flow of events.
	 * 
	 * @since 2022/04/21
	 */
	public static enum Direction
	{
		/** Receiver. */
		RECEIVE(false),
		
		/** Transmitter. */
		TRANSMIT(true),
		
		/** End. */
		;
		
		/** The flag used for the direction. */
		public final boolean flag;
		
		/**
		 * Initializes the direction.
		 * 
		 * @param __flag The flag used for the data.
		 * @since 2022/04/21
		 */
		Direction(boolean __flag)
		{
			this.flag = __flag;
		}
	}
}
