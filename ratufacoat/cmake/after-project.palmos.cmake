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

# Disallow MiniZ to be used
set(SQUIRRELJME_BLOCK_LIBMINIZ YES)

# Disallow Nuklear to be used
set(SQUIRRELJME_BLOCK_LIBNUKLEAR YES)

# Use bundled LibStdC
set(SQUIRRELJME_BUNDLED_STDC YES)

# FPIC is not supported
# cc1: -fPIC is not currently supported on the 68000 or 68010
set(SQUIRRELJME_FPIC_MODE NO)