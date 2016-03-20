#!/bin/sh
# ---------------------------------------------------------------------------
# Multi-Phasic Applications: SquirrelJME
#     Copyright (C) 2013-2016 Steven Gawroriski <steven@multiphasicapps.net>
#     Copyright (C) 2013-2016 Multi-Phasic Applications <multiphasicapps.net>
# ---------------------------------------------------------------------------
# SquirrelJME is under the GNU Affero General Public License v3, or later.
# For more information see license.mkd.
# ---------------------------------------------------------------------------
# DESCRIPTION: Builds whatever is needed to support hairball and then invokes
# it using the host virtual machine.

# Force C locale
export LC_ALL=C

# Directory of this script
__exedir="$(dirname -- "$0")"

# No compression JARs?
if [ -z "$__jzero" ]
then
	if [ "$#" -ge "0" ] && [ "$1" = "-0" ]
	then
		export __jzero=0
		shift
	else
		__jzero=
	fi
fi

# If no arguments, then assume hairball
if [ "$#" -le "0" ]
then
	"$0" "target"
	exit $?

# Run tests on the host
elif [ "$1" = "host-tests" ]
then
	# Build all tests
	if ! "$0" "build" "test-all"
	then
		echo "Failed to build all tests." 2>&1
		exit 1
	fi
	
	# Launch the tests
	"$0" "launch" "test-all"
	exit $?

# Run tests on the interpreter
elif [ "$1" = "interpreter-tests" ]
then
	# Build all tests
	if ! "$0" "build" "test-all"
	then
		echo "Failed to build all tests." 2>&1
		exit 1
	fi
	
	# Launch the tests
	"$0" "interpreter-launch" "test-all"
	exit $?

# Target a specific OS/Arch
elif [ "$1" = "target" ]
then
	# Build hairball
	if ! "$0" "build" "hairball"
	then
		echo "Failed to build hairball." 2>&1
		exit 1
	fi
	
	# Launch hairball
	"$0" "launch" "hairball" "." "$__exedir/src" "$2" "$3"
	exit $?

# Interpreter test variant of target
elif [ "$1" = "interpreter-target" ]
then
	# Build hairball
	if ! "$0" "build" "hairball"
	then
		echo "Failed to build hairball." 2>&1
		exit 1
	fi
	
	# Launch hairball
	"$0" "interpreter-launch" "hairball" "." "$__exedir/src" "$2" "$3"
	exit $?

# Launch a package
elif [ "$1" = "launch" ]
then
	# Need arguments?
	if [ "$#" -le "1" ]
	then
		echo "Usage: $0 $1 (package)" 2>&1
		exit 1
	fi
	
	# The package to launch
	__pack="$2"
	
	# Shift down two so "launch foo" are not included
	shift 2
	
	# Launch the given command
	echo "*** Launch $__pack ***" 1>&2
	java -cp "$("$0" "launch-classpath" "$__pack")" \
		"$("$0" "main-class" "$__pack")" $*
	exit $?

# Launch a package (using the interpreter, for testing)
elif [ "$1" = "interpreter-launch" ]
then
	# Need arguments?
	if [ "$#" -le "1" ]
	then
		echo "Usage: $0 $1 (package)" 2>&1
		exit 1
	fi
	
	# Build the local interpreter
	if ! "$0" "build" "java-interpreter-local"
	then
		echo "Failed to build the local intepreter." 2>&1
		exit 1
	fi
	
	# The package to launch
	__pack="$2"
	
	# Shift down two so "launch foo" are not included
	shift 2
	
	# Launch the given command
	echo "*** Interpreted Launch $__pack ***" 1>&2
	java -cp "$("$0" "launch-classpath" "java-interpreter-local")" \
		"$("$0" "main-class" "java-interpreter-local")" \
		-cp "$("$0" "launch-classpath" "$__pack")" \
		"$("$0" "main-class" "$__pack")" $*
	exit $?

# Calculate dependenices for a package to launch it
elif [ "$1" = "launch-classpath" ]
then
	# Need arguments?
	if [ "$#" -le "1" ]
	then
		echo "Usage: $0 $1 (package)" 2>&1
		exit 1
	fi
	
	# The package to get dependencies for
	__pack="$2"
	
	# Get the dependent class path
	__dcp="$("$0" depends-classpath "$__pack")"
	
	# If there are none, then just print this package
	if [ -z "$__dcp" ]
	then
		echo "$__pack.jar"
	
	# Otherwise add ours on top
	else
		echo "$__dcp:$__pack.jar"
	fi

# Prints the main class (if any) for a package
elif [ "$1" = "main-class" ]
then
	# Need arguments?
	if [ "$#" -le "1" ]
	then
		echo "Usage: $0 $1 (package)" 2>&1
		exit 1
	fi
	
	# The package to get dependencies for
	__pack="$2"
	
	# Needs a manifest
	__manf="$__exedir/src/$__pack/META-INF/MANIFEST.MF"
	if [ ! -f "$__manf" ]
	then
		echo "The package $__pack does not have a manifest." 2>&1
		exit 1
	fi
	
	# Get the main class
	__usemain="$(tr '\n' '\v' < $__manf | sed 's/\v / /g' | \
		tr '\v' '\n' | \
		grep '^Main-Class[ \t]*:' | sed 's/^[^:]*:[ \t]*//g')"
	
	# If a main is missing, find it in a dependency
	if [ -z "$__usemain" ]
	then
		if !("$0" "dependsnorecurse" "$__pack" | while read __dep
		do
			# Get it
			__maybemain="$("$0" "main-class" "$__dep")"
			
			# If found print it out and stop
			if [ -n "$__maybemain" ]
			then
				echo "$__maybemain"
				exit 0
			fi
		done)
		then
			exit 1
		fi
	
	# Otherwise print it
	else
		echo "$__usemain"
	fi

# Calculate the dependent classpath for a specific package
elif [ "$1" = "depends-classpath" ]
then
	# Need arguments?
	if [ "$#" -le "1" ]
	then
		echo "Usage: $0 $1 (package)" 2>&1
		exit 1
	fi
	
	# The package to get dependencies for
	__pack="$2"
	
	# Get dependencies
	__i=0
	"$0" depends "$__pack" | while read __line
	do
		# Flagged?
		if [ "$__i" -ne "0" ]
		then
			echo -n ":"
		fi
		
		# Set to use colon next time
		__i=1
		
		# Print it
		echo -n "$__line.jar"
	done

# Get all package dependencies without recursion
elif [ "$1" = "dependsnorecurse" ]
then
	# Need arguments?
	if [ "$#" -le "1" ]
	then
		echo "Usage: $0 $1 (package)" 2>&1
		exit 1
	fi
	
	# The package to get dependencies for
	__pack="$2"

	# Needs a manifest
	__manf="$__exedir/src/$__pack/META-INF/MANIFEST.MF"
	if [ ! -f "$__manf" ]
	then
		echo "The package $__pack does not have a manifest." 2>&1
		exit 1
	fi
	
	# Echo them out
	(for __dep in $(tr '\n' '\v' < $__manf | sed 's/\v / /g' | \
		tr '\v' '\n' | \
		grep '^X-Hairball-Depends[ \t]*:' | sed 's/^[^:]*:[ \t]*//g')
	do
		# Print this dependency
		echo "$__dep"
	done) | sort | uniq

# Get all package dependencies (recursive)
elif [ "$1" = "depends" ]
then
	# Need arguments?
	if [ "$#" -le "1" ]
	then
		echo "Usage: $0 $1 (package)" 2>&1
		exit 1
	fi
	
	# The package to get dependencies for
	__pack="$2"
	
	# Call non-recursive getter
	("$0" "dependsnorecurse" "$__pack" | while read __dep
	do
		# Print this dependency
		echo "$__dep"
		
		# Print dependencies of that dependency
		if ! "$0" "depends" "$__dep"
		then
			echo "Failed to print dependency $__dep for package $__pack."
			exit 1
		fi
	done) | sort | uniq
	
# Building a package
elif [ "$1" = "build" ]
then
	# Need arguments?
	if [ "$#" -le "1" ]
	then
		echo "Usage: $0 $1 (package)" 2>&1
		exit 1
	fi
	
	# The package to compile
	__pack="$2"

	# Needs a manifest
	__manf="$__exedir/src/$__pack/META-INF/MANIFEST.MF"
	if [ ! -f "$__manf" ]
	then
		echo "The package $__pack does not have a manifest." 2>&1
		exit 1
	fi

	# Build package dependencies
	if !("$0" "dependsnorecurse" "$__pack" | while read __dep
	do
		# Build dependency
		if ! "$0" "build" "$__dep"
		then
			echo "Failed to build dependency $__dep for $__pack." 2>&1
			exit 1
		fi
	done)
	then
		exit 1
	fi

	# Get the output JAR and source locations
	__ojar="$__pack.jar"
	__isrc="$__exedir/src/$__pack"

	# If the JAR exists, check the date against the sources
	rm -f /tmp/$$
	if [ -f "$__ojar" ]
	then
		# Compare the JAR date against all files
		find "$__isrc" -type f | while read __file
		do
			if [ "$__file" -nt "$__ojar" ]
			then
				touch /tmp/$$
				break
			fi
		done
	
		# Compare dependencies (if they exist)
		"$0" "dependsnorecurse" "$__pack" | while read __dep
		do
			if [ -f "$__dep.jar" ] && [ "$__dep.jar" -nt "$__ojar" ]
			then
				touch /tmp/$$
				break
			fi
		done

	# Always out of date
	else
		touch /tmp/$$
	fi

	# Out of date?
	if [ -f /tmp/$$ ]
	then
		__ood=1
	else
		__ood=0
	fi
	
	# Always delete it, otherwise /tmp will be full of very small files
	rm -f /tmp/$$

	# Perform a rebuild?
	if [ "$__ood" -ne "0" ]
	then
		# Delete the output JAR first
		rm -f "$__ojar"
		
		# List source code which needs compilation
		find "$__isrc" -type f | grep '\.java$' > /tmp/$$
		
		# Place code here
		mkdir -p "/tmp/$$.$__pack"
		
		# Note it
		echo "*** Compiling $__pack ***" 2>&1
		
		# Calculate stuff to run it with
		__cp="$("$0" "depends-classpath" "$__pack")"
		
		# Compile code but only if there is source code to actually compile
		if [ -s "/tmp/$$" ]
		then
			if ! javac -d "/tmp/$$.$__pack" -source 1.7 -target 1.7 -g \
				-Xlint:unchecked -bootclasspath "$__cp" @/tmp/$$
			then
				# Note
				echo "Failed to compile $__pack." 2>&1
			
				# Delete temporaries
				rm -rf "/tmp/$$.$__pack"
				rm -f /tmp/$$
			
				# Failed
				exit 1
			fi
		fi
		
		# Copy non-sources from the source tree to the output tree
		if ! (cd "$__isrc"; find . -type f | grep -v '\.java$' | while read __f
		do
			# Make output directory
			if ! mkdir -p "/tmp/$$.$__pack/$(dirname "$__f")"
			then
				exit 1
			fi
			
			# Copy file
			if ! cp "$__f" "/tmp/$$.$__pack/$__f"
			then
				exit 1
			fi
		done)
		then
			# Note
			echo "Failed to copy non-sources for $__pack." 2>&1
			
			# Delete
			rm -rf "/tmp/$$.$__pack"
			rm -f /tmp/$$
			
			# Failed
			exit 1
		fi
		
		# Package it up
		if ! jar cfm$__jzero "$__ojar" "$__isrc/META-INF/MANIFEST.MF" \
			-C "/tmp/$$.$__pack" .
		then
			# Note
			echo "Failed to package $__pack." 2>&1
			
			# Delete
			rm -rf "/tmp/$$.$__pack"
			rm -f /tmp/$$
			
			# Failed
			exit 1
		fi 
		
		# Cleanup
		rm -rf "/tmp/$$.$__pack"
		rm -f /tmp/$$
	fi

# Unknown command
else
	echo "Unknown sub-command $1." 2>&1
	exit 1
fi

