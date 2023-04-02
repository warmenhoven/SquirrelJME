/* -*- Mode: C; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// -------------------------------------------------------------------------*/

#if defined(SJME_HAS_LINUX)
	#include <unistd.h>
	#include <sys/syscall.h>
#endif

#if defined(SQUIRRELJME_THREADS_PTHREAD)
	#include <pthread.h>

	#if defined(SQUIRRELJME_THREADS_PTHREAD_BSD)
		#include <pthread_np.h>
	#endif
#elif defined(SQUIRRELJME_THREADS_WIN32)
	#include <windows.h>
#endif

#include "memio/thread.h"

sjme_memIo_threadId sjme_memIo_threadCurrentId(void)
{
#if defined(SQUIRRELJME_THREADS_PTHREAD_BSD)
	return (sjme_memIo_threadId)pthread_getthreadid_np();
#elif defined(SJME_HAS_LINUX)
	return syscall(SYS_gettid);
#elif defined(SQUIRRELJME_THREADS_PTHREAD)
	return (sjme_memIo_threadId)pthread_self();
#elif defined(SQUIRRELJME_THREADS_WIN32)
	return (sjme_memIo_threadId)GetCurrentThreadId();
#else
	return 7061989;
#endif
}

void sjme_memIo_threadYield(void)
{
#if defined(SQUIRRELJME_THREADS_PTHREAD)
	return pthread_yield();
#elif defined(SQUIRRELJME_THREADS_WIN32)
	YieldProcessor();
#endif
}
