/*
 * Copyright 2014-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
#include "ff_gen_drv.h"
#include "sd_diskio.h"
#include <errno.h>
#include <stdio.h>
#include "sni.h"

FIL fd;       
FATFS MySDFatFs;
char SDpath[4];
int applicationID = 1;
char path[25] = "/tmp/application_1.fo";

void Java_com_microej_kernel_FeatureInputStream_init(){
	FRESULT res = FR_NO_FILESYSTEM;
	if(FATFS_LinkDriver(&SD_Driver, SDpath) == 0){
		res = f_mount(&MySDFatFs, (TCHAR const*)SDpath, 0);
	}
	if(res!=FR_OK){
		printf("Error while init FS\n");
	} else {
		printf("Looking for feature %s\n",path);
	}
}

jboolean Java_com_microej_kernel_FeatureInputStream_isFeatureAvailable() {
	FRESULT res;
	int available = 0;
	FILINFO fno;
	res = f_stat(path, &fno);
	if(res==FR_OK){
		res = f_open(&fd, path, FA_READ);
		if(res==FR_OK){
			available = fno.fsize;
		}
	}

	return (available>0)?JTRUE:JFALSE;
}

jint Java_com_microej_kernel_FeatureInputStream_readIntoArray(jbyte *out, jint outOffset, jint outLen){
	UINT numBytesRead = 0;

	f_read (&fd, out+outOffset,  outLen, &numBytesRead);
	return numBytesRead;
};

void Java_com_microej_kernel_FeatureInputStream_closeFeature(){
	printf("close\n");
	f_close(&fd);
	// Wait for the next app
	applicationID++;
	sprintf(path, "/tmp/application_%d.fo", applicationID);
	printf("Looking for feature %s\n",path);
}


WCHAR ff_convert(WCHAR wch, UINT dir)
{
	WCHAR c_return;

	/* test if wch is in simple ascii table */
	if (wch < 0x80)
	{
		c_return = wch;
	}
	else
	{
		c_return = 0;
	}

	return(c_return);
}

/** @ brief function needed by FatFs */
WCHAR ff_wtoupper(WCHAR wch)
{
	WCHAR c_return;

	/* test if wch is in simple ascii table */
	if (wch < 0x80)
	{
		/* upper case */
		if (wch>= 'a' && wch<= 'z')
		{
			wch &= ~0x20;
		}
		else
		{
			/* nothing to do */
		}

		c_return = wch;
	}
	else
	{
		c_return = 0;
	}

	return(c_return);
}
