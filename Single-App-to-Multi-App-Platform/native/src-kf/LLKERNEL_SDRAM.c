/*
 * Copyright 2013-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */

/* Includes ------------------------------------------------------------------*/

#include "LLKERNEL_impl.h"
#include "BESTFIT_ALLOCATOR.h"
#include <stdio.h>
#include <string.h>

// declare MicroEJ KF Working buffer
int8_t KERNEL_WORKING_BUFFER[64000];

BESTFIT_ALLOCATOR allocatorInstance;
uint32_t KERNEL_allocationInitialized;

/* API -----------------------------------------------------------------------*/

void* LLKERNEL_IMPL_allocate(int32_t size){
	if(KERNEL_allocationInitialized == 0){
		// lazy init
		BESTFIT_ALLOCATOR_new(&allocatorInstance);
		BESTFIT_ALLOCATOR_initialize(&allocatorInstance, (int32_t)&KERNEL_WORKING_BUFFER, ((int32_t)&KERNEL_WORKING_BUFFER)+sizeof(KERNEL_WORKING_BUFFER));
		KERNEL_allocationInitialized = 1;
	}

	void* allocationStart = BESTFIT_ALLOCATOR_allocate(&allocatorInstance, size);
#ifdef ALLOCATOR_DEBUG
	printf("LLKERNEL_IMPL_allocate %d %p \n", size, allocationStart);
#endif
	return (void*)allocationStart;
}

void LLKERNEL_IMPL_free(void* block){
	BESTFIT_ALLOCATOR_free(&allocatorInstance, block);
#ifdef ALLOCATOR_DEBUG
	printf("LLKERNEL_IMPL_free %p \n", block);
#endif
}

