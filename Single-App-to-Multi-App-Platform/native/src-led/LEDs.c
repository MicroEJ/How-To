/*
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
#include "sni.h"
#include "stm32f7xx_hal.h"

/**************************************************************
 * LLLEDS implementation functions
 **************************************************************/

void Java_com_microej_Led_initNative(void)
{
	__GPIOI_CLK_ENABLE();
	GPIO_InitTypeDef GPIO_InitStruct;

	/* Configure LED pin as output */
	GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
	GPIO_InitStruct.Pull = GPIO_NOPULL;
	GPIO_InitStruct.Speed = GPIO_SPEED_LOW;
	GPIO_InitStruct.Pin = GPIO_PIN_1;

	HAL_GPIO_Init(GPIOI, &GPIO_InitStruct);
}

void Java_com_microej_Led_switchLedNative(jboolean on)
{
    GPIO_PinState value;

    if(on == JTRUE){
        value = GPIO_PIN_SET;
    } else {
        value = GPIO_PIN_RESET;
    }

	HAL_GPIO_WritePin(GPIOI, GPIO_PIN_1, value);
}

