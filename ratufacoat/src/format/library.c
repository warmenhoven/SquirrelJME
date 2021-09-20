/* -*- Mode: C; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// -------------------------------------------------------------------------*/

#include "format/detect.h"
#include "format/library.h"
#include "format/sqc.h"
#include "format/zip.h"
#include "debug.h"

struct sjme_libraryInstance
{
	/** The driver used to interact with the library. */
	const sjme_libraryDriver* driver;
};

/** The library drivers which are available for usage. */
const sjme_libraryDriver* const sjme_libraryDrivers[] =
{
	&sjme_libraryZipDriver,
	&sjme_librarySqcDriver,
	
	NULL
};

sjme_jboolean sjme_libraryOpen(sjme_libraryInstance** outInstance,
	const void* data, sjme_jint size, sjme_error* error)
{
	const sjme_libraryDriver* tryDriver;
	
	/* Try to detect the format using the common means. */
	if (!sjme_detectFormat(data, size,
		(const void**)&tryDriver, (const void**)sjme_libraryDrivers,
		offsetof(sjme_libraryDriver, detect), error))
	{
		sjme_setError(error, SJME_ERROR_UNKNOWN_LIBRARY_FORMAT, 0);
		
		return sjme_false;
	}
	
	sjme_todo("TODO -- sjme_libraryOpen()");
}
