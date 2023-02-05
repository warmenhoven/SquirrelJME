// -*- Mode: Java; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// ---------------------------------------------------------------------------

package cc.squirreljme.plugin.multivm;

import cc.squirreljme.plugin.SquirrelJMEPluginConfiguration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.gradle.api.Action;
import org.gradle.api.Task;
import proguard.ClassPath;
import proguard.ClassPathEntry;
import proguard.Configuration;
import proguard.ConfigurationParser;
import proguard.ProGuard;

/**
 * Performs the actual compaction of the Jar.
 *
 * @since 2023/02/01
 */
public class VMCompactLibraryTaskAction
	implements Action<Task>
{
	/** Settings to use in the configuration for keeping, etc. */
	static final String[] _PARSE_SETTINGS = new String[]
		{
			// Adjust manifest resources
			"-adaptresourcefilenames", "**",
			"-adaptresourcefilecontents",
				"META-INF/MANIFEST.MF,META-INF/services/**",
			
			// Consumers of the libraries/APIs need to see the annotation
			// information if it is there, to make sure it is retained
			"-keepattributes", "*Annotation*",
			
			// Keep anything with main in it
			"-keepclasseswithmembers", "class", "*", "{",
				"public", "static", "void", "main", "(",
					"java.lang.String[]", ")", ";",
			"}",
			
			// Keep any MIDlet
			"-keep", "class", "*", "extends",
				"javax.microedition.midlet.MIDlet",
			
			// Keep classes annotation with @Api and
			"-keep", "public",
				"@cc.squirreljme.runtime.cldc.annotation.Api",
				"class", "*", "{",
				"public", "protected", "*", ";",
				"}",
			"-keep", "public",
				"@cc.squirreljme.runtime.cldc.annotation.SquirrelJMEVendorApi",
				"class", "*", "{",
				"public", "protected", "*", ";",
				"}",
			
			// Keep the names of these classes as well
			"-keepnames", "public",
				"@cc.squirreljme.runtime.cldc.annotation.Api",
				"class", "*",
			"-keepnames", "public",
				"@cc.squirreljme.runtime.cldc.annotation.SquirrelJMEVendorApi",
				"class", "*",
			
			// Keep members with these two annotations
			"-keepclassmembers", "public", "class", "*", "{",
				"@cc.squirreljme.runtime.cldc.annotation.Api",
				"public", "protected", "*", ";",
				"}",
			"-keepclassmembers", "public", "class", "*", "{",
				"@cc.squirreljme.runtime.cldc.annotation.SquirrelJMEVendorApi",
				"public", "protected", "*", ";",
				"}",
			
			// Keep names as well
			"-keepclassmembernames", "public", "class", "*", "{",
				"@cc.squirreljme.runtime.cldc.annotation.Api",
				"public", "protected", "*", ";",
				"}",
			"-keepclassmembernames", "public", "class", "*", "{",
				"@cc.squirreljme.runtime.cldc.annotation.SquirrelJMEVendorApi",
				"public", "protected", "*", ";",
				"}",
		};
	
	/** The source set used. */
	public final String sourceSet;
	
	/**
	 * Initializes the task action.
	 * 
	 * @param __sourceSet The source set used.
	 * @throws NullPointerException On null arguments.
	 * @since 2023/02/01
	 */
	public VMCompactLibraryTaskAction(String __sourceSet)
		throws NullPointerException
	{
		if (__sourceSet == null)
			throw new NullPointerException("NARG");
		
		this.sourceSet = __sourceSet;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2023/02/01
	 */
	@Override
	public void execute(Task __task)
	{
		VMCompactLibraryTask compactTask = (VMCompactLibraryTask)__task;
		
		// Where are we reading/writing to/from?
		Path inputPath = compactTask.inputBaseJarPath().get();
		Path outputJarPath = compactTask.outputJarPath().get();
		Path outputMapPath = compactTask.outputMapPath().get();
		
		// Some settings may be configured
		SquirrelJMEPluginConfiguration projectConfig =
			SquirrelJMEPluginConfiguration.configuration(__task.getProject());
		
		// Run the task
		Path tempJarFile = null;
		Path tempInputMapFile = null;
		Path tempOutputMapFile = null;
		try
		{
			// If we are not shrinking, since we cannot check the config at
			// initialization stage... just do a copy operation here
			if (projectConfig.noShrinking)
			{
				Files.copy(inputPath, outputJarPath,
					StandardCopyOption.REPLACE_EXISTING);
				
				return;
			}
			
			// Setup temporary file to output to when finished
			tempJarFile = Files.createTempFile("out", ".jar");
			tempInputMapFile = Files.createTempFile("in", ".map");
			tempOutputMapFile = Files.createTempFile("out", ".map");
			
			// Need to delete the created temporary file, otherwise Proguard
			// will just say "The output appears up to date" and do nothing
			Files.delete(tempJarFile);
			Files.delete(tempOutputMapFile);
			
			// We need to include all the inputs that were already ran through
			// ProGuard, so we basically need to look at the dependencies and
			// map them around accordingly
			// We also need to combine the mapping files as well
			ClassPath libraryJars = new ClassPath();
			boolean applyMapping = false;
			for (VMCompactLibraryTask compactDep :
				VMHelpers.compactLibTaskDepends(__task.getProject(),
					this.sourceSet))
			{
				// Add the library, but the pre-obfuscated form since we need
				// to know what it is
				libraryJars.add(new ClassPathEntry(
					compactDep.baseJar.getOutputs().getFiles().getSingleFile(),
					false));
				
				// If the mapping file exists, concatenate it
				if (Files.exists(compactDep.outputMapPath().get()))
				{
					// Do use mapping now
					applyMapping = true;
					
					// Add all the information
					Files.write(tempInputMapFile,
						Files.readAllLines(compactDep.outputMapPath().get()),
						StandardOpenOption.APPEND, StandardOpenOption.WRITE);
				}
			}
			
			// Base options to use
			List<String> proGuardOptions = new ArrayList<>();
			proGuardOptions.addAll(
				Arrays.asList(VMCompactLibraryTaskAction._PARSE_SETTINGS));
			
			// Add any additional options as needed
			if (projectConfig.proGuardOptions != null &&
				!projectConfig.proGuardOptions.isEmpty())
				proGuardOptions.addAll(projectConfig.proGuardOptions);
			
			// Parse initial configuration with settings
			Configuration config = new Configuration();
			try (ConfigurationParser parser = new ConfigurationParser(
				proGuardOptions.toArray(new String[proGuardOptions.size()]),
				new Properties()))
			{
				parser.parse(config);
			}
			
			// We are neither of these platforms, we say we are not Java ME
			// because it will remove StackMapTable and instead use StackMap
			// which is not what we want
			config.android = false;
			config.microEdition = false;
			
			// Reduce space and obfuscate, but we cannot remove everything at
			// this time
			config.shrink = false;
			config.optimize = false;
			config.flattenPackageHierarchy = "$" +
				(projectConfig.javaDocErrorCode == null ? "??" :
				projectConfig.javaDocErrorCode);
			
			// For mapping files, members do need to be unique
			config.useUniqueClassMemberNames = true;
			
			// Do not use mix case class names, so that more strings can
			// be compacted together accordingly
			config.useMixedCaseClassNames = false;
			
			// Write mapping to the output file, since we will use it later on
			config.printMapping = tempOutputMapFile.toFile();
			
			// Utilize the combined mapping file that was made so that we can
			// use everything we have?
			if (applyMapping)
				config.applyMapping = tempInputMapFile.toFile();
			
			// Be noisy
			config.verbose = true;
			//config.dump = Configuration.STD_OUT;
			//config.printUsage = Configuration.STD_OUT;
			config.printConfiguration = Configuration.STD_OUT;
			
			// Use whatever libraries were found
			config.libraryJars = libraryJars;
			
			// Setup input and output Jar
			ClassPath programJars = new ClassPath();
			config.programJars = programJars;
			
			// Input source Jar
			programJars.add(
				new ClassPathEntry(inputPath.toFile(), false));
			
			// Output temporary Jar
			programJars.add(new ClassPathEntry(
				tempJarFile.toFile(), true));
			
			// Run the shrinking/obfuscation
			try
			{
				new ProGuard(config).execute();
			}
			finally
			{
				Files.move(tempInputMapFile,
					outputMapPath.resolveSibling(
						outputMapPath.getFileName() + ".in"),
					StandardCopyOption.REPLACE_EXISTING);
			}
			
			// Insurance
			if (Files.size(tempJarFile) <= 12)
				throw new RuntimeException("Nothing happened?");
			
			// Move to output
			Files.move(tempJarFile,
				outputJarPath,
				StandardCopyOption.REPLACE_EXISTING);
			Files.move(tempOutputMapFile,
				outputMapPath,
				StandardCopyOption.REPLACE_EXISTING);
		}
		catch (Exception __e)
		{
			throw new RuntimeException("Failed to shrink/obfuscate.", __e);
		}
		
		// Cleanup anything left over
		finally
		{
			if (tempJarFile != null)
				try
				{
					Files.delete(tempJarFile);
				}
				catch (IOException ignored)
				{
				}
			
			if (tempInputMapFile != null)
				try
				{
					Files.delete(tempInputMapFile);
				}
				catch (IOException ignored)
				{
				}
			
			if (tempOutputMapFile != null)
				try
				{
					Files.delete(tempOutputMapFile);
				}
				catch (IOException ignored)
				{
				}
		}
	}
}
