/*
 * C
 *
 * Copyright 2016-2017 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
#ifndef LLMYLIB_IMPL
#define LLMYLIB_IMPL

/**
 * @file
 * @brief MicroEJ factorial low level API
 * @author My Company
 * @version 1.0.0
 */

#include <stdint.h>
#include <intern/LLMYLIB_impl.h>

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Returns the factorial
 */
uint32_t LLMYLIB_IMPL_factorial(uint32_t number);

#ifdef __cplusplus
}
#endif
#endif
