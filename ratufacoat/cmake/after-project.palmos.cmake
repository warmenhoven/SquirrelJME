# ---------------------------------------------------------------------------
# SquirrelJME
#     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
# ---------------------------------------------------------------------------
# SquirrelJME is under the GNU General Public License v3+, or later.
# See license.mkd for licensing and copyright information.
# ---------------------------------------------------------------------------
# DESCRIPTION: Palm OS Setup

# Allow PalmOS interface
set(SQUIRRELJME_ALLOW_PALMOS YES)

# Disable unit testing, we cannot reliably test on this platform
set(SQUIRRELJME_ALLOW_TESTING NO)

# Use bundled LibStdC
set(SQUIRRELJME_BUNDLED_STDC YES)
