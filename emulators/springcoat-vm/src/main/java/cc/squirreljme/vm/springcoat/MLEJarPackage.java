// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.vm.springcoat;

import cc.squirreljme.jvm.mle.JarPackageShelf;
import cc.squirreljme.jvm.mle.brackets.JarPackageBracket;
import cc.squirreljme.vm.VMClassLibrary;
import cc.squirreljme.vm.springcoat.brackets.JarPackageObject;
import cc.squirreljme.vm.springcoat.exceptions.SpringVirtualMachineException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.multiphasicapps.classfile.MethodDescriptor;

/**
 * Functions for {@link JarPackageShelf}.
 *
 * @since 2020/06/18
 */
public enum MLEJarPackage
	implements MLEFunction
{
	/** {@link JarPackageShelf#classPath()}. */
	CLASS_PATH("classPath:()[Lcc/squirreljme/jvm/mle/brackets/" +
		"JarPackageBracket;")
	{
		/**
		 * {@inheritDoc}
		 * @since 2020/06/18
		 */
		@Override
		public Object handle(SpringThreadWorker __thread, Object... __args)
		{
			VMClassLibrary[] springPath = __thread.machine.classloader
				.classPath();
		
			// Wrap the classpath in package objects
			int n = springPath.length;
			SpringObject[] rv = new SpringObject[n];
			for (int i = 0; i < n; i++)
				rv[i] = new JarPackageObject(springPath[i]);
			
			// Wrap
			return __thread.asVMObjectArray(__thread.resolveClass(
				"[Lcc/squirreljme/jvm/mle/brackets/JarPackageBracket;"),
				rv);
		}
	},
	
	/** {@link JarPackageShelf#openResource(JarPackageBracket, String)}. */
	OPEN_RESOURCE("openResource:(Lcc/squirreljme/jvm/mle/brackets/" +
		"JarPackageBracket;Ljava/lang/String;)Ljava/io/InputStream;")
	{
		/**
		 * {@inheritDoc}
		 * @since 2020/06/18
		 */
		@Override
		public Object handle(SpringThreadWorker __thread, Object... __args)
		{
			JarPackageObject jar = (JarPackageObject)__args[0];
			String rcName = __thread.<String>asNativeObject(
				String.class, __args[1]);
			
			// Locate the resource
			try (InputStream in = jar.library().resourceAsStream(rcName))
			{
				// Not found
				if (in == null)
					return SpringNullObject.NULL;
				
				// Copy everything to the a byte array, since it is easier to
				// handle resources without juggling special resource streams
				// and otherwise
				try (ByteArrayOutputStream baos = new ByteArrayOutputStream(
					Math.max(1024, in.available())))
				{
					// Copy all the data
					byte[] copy = new byte[4096];
					for (;;)
					{
						int rc = in.read(copy);
						
						if (rc < 0)
							break;
						
						baos.write(copy, 0, rc);
					}
					
					// Use this as the stream input
					return __thread.newInstance(__thread.loadClass(
						"java/io/ByteArrayInputStream"),
						new MethodDescriptor("([B)V"),
						__thread.asVMObject(baos.toByteArray()));
				}
			}
			
			// Could not read it
			catch (IOException e)
			{
				throw new SpringVirtualMachineException(
					"Failed to read resource", e);
			}
		}
	}
	
	/* End. */
	;
	
	/** The dispatch key. */
	protected final String key;
	
	/**
	 * Initializes the dispatcher info.
	 *
	 * @param __key The key.
	 * @throws NullPointerException On null arguments.
	 * @since 2020/06/18
	 */
	MLEJarPackage(String __key)
		throws NullPointerException
	{
		if (__key == null)
			throw new NullPointerException("NARG");
		
		this.key = __key;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2020/06/18
	 */
	@Override
	public String key()
	{
		return this.key;
	}
}