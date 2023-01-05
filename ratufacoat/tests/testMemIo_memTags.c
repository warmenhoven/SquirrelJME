/* -*- Mode: C; indent-tabs-mode: t; tab-width: 4 -*-
// ---------------------------------------------------------------------------
// Multi-Phasic Applications: SquirrelJME
//     Copyright (C) Stephanie Gawroriski <xer@multiphasicapps.net>
// ---------------------------------------------------------------------------
// SquirrelJME is under the GNU General Public License v3+, or later.
// See license.mkd for licensing and copyright information.
// -------------------------------------------------------------------------*/

#include "tests.h"
#include "memio/memtag.h"
#include "debug.h"

/** The number of pointers to allocate. */
#define TEST_NUM_POINTERS 16

/**
 * Structure.
 *
 * @since 2022/12/20
 */
typedef struct testStruct
{
	/** First. */
	sjme_jint a;

	/* Second. */
	sjme_jint b;

	/* Third. */
	sjme_jint c;
} testStruct;

/** Tagged version of the struct. */
SJME_MEMIO_DECL_TAGGED(testStruct);

/**
 * Tests that JNI memory tags work.
 *
 * @since 2022/12/20
 */
SJME_TEST_PROTOTYPE(testMemIo_memTags)
{
	sjme_memIo_tagGroup* tagGroup;
	sjme_jint i;
	SJME_MEMIO_TAGGED(testStruct)* alloc;
	SJME_MEMIO_TAGGED(testStruct)* nullIsh;
	SJME_MEMIO_TAGGED(testStruct)* notValid = (void*)INT32_C(0xCAFE);
	SJME_MEMIO_TAGGED(testStruct)* alreadySet;
	SJME_MEMIO_TAGGED(testStruct)* set[TEST_NUM_POINTERS];

	sjme_message("Deref: %d\n", sizeof(**(alloc)));

	/* Setup tag group. */
	tagGroup = NULL;
	if (!sjme_memIo_taggedGroupNew(&tagGroup, &shim->error))
		return FAIL_TEST(1);

	/* Sizeof the base should be the same. */
	if (sizeof(**(alloc)) != sizeof(testStruct)) /* NOLINT */
		return FAIL_TEST(2);

	/* Allocating with sizeof() should fail. */
	nullIsh = NULL;
	if (sjme_memIo_taggedNew(tagGroup, nullIsh, sizeof(nullIsh), /* NOLINT */
			SJME_MEM_TAG_STATIC, &shim->error))
		return FAIL_TEST(3);

	if (shim->error.code != SJME_ERROR_NULLARGS)
		return FAIL_TEST_SUB(3, 1);

	/* Allocating with sizeof() should fail. */
	alloc = NULL;
	if (sjme_memIo_taggedNew(tagGroup, &alloc, sizeof(alloc),
		SJME_MEM_TAG_STATIC, &shim->error))
		return FAIL_TEST(4);

	if (shim->error.code != SJME_ERROR_TAGGED_WRONG_SIZE_OF)
		return FAIL_TEST_SUB(4, 1);

	/* Allocating not using &alloc, should fail due to protection. */
	if (sjme_memIo_taggedNew(tagGroup, notValid, /* NOLINT */
			sjme_memIo_taggedNewSizeOf(notValid),
			SJME_MEM_TAG_STATIC, &shim->error))
		return FAIL_TEST(5);

	if (shim->error.code != SJME_ERROR_PROTECTED_MEM_VIOLATION)
		return FAIL_TEST_SUB(5, 1);

	/* Allocating over a pointer that already has something there. */
	alreadySet = (void*)INT32_C(0xCAFE);
	if (sjme_memIo_taggedNew(tagGroup, &alreadySet,
			sjme_memIo_taggedNewSizeOf(alreadySet), SJME_MEM_TAG_STATIC,
			&shim->error))
		return FAIL_TEST(6);

	if (shim->error.code != SJME_ERROR_TAG_NOT_NULL)
		return FAIL_TEST_SUB(6, 1);

	/* Try allocating memory. */
	if (!sjme_memIo_taggedNew(tagGroup, &alloc,
		sjme_memIo_taggedNewSizeOf(alloc), SJME_MEM_TAG_STATIC, &shim->error))
		return FAIL_TEST(7);

	/* Access it. */
	(*alloc)->a = 1;
	(*alloc)->b = 2;
	(*alloc)->c = 3;

	/* Allocate sub-test pointers. */
	memset(set, 0, sizeof(set));
	for (i = 0; i < TEST_NUM_POINTERS; i++)
		if (!sjme_memIo_taggedNew(tagGroup, &set[i],
			sjme_memIo_taggedNewSizeOf(set[i]), SJME_MEM_TAG_STATIC,
			&shim->error))
			return FAIL_TEST_SUB(8, i);

	/* Free only half of them. */
	for (i = 0; i < TEST_NUM_POINTERS; i += 2)
		if (!sjme_memIo_taggedFree(&set[i], &shim->error))
			return FAIL_TEST_SUB(9, i);

	/* Free the memory group, this should also free the pointer. */
	if (!sjme_memIo_taggedGroupFree(&tagGroup, &shim->error))
		return FAIL_TEST(10);

	/* Pointer should be cleared here. */
	if ((*alloc) != NULL)
		return FAIL_TEST(11);

	return PASS_TEST();
}