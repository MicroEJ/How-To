#include "LLMYLIB_impl.h"
#include "sni.h"

/**
 * @file
 * @brief MicroEJ factorial low level API
 * @author My Company
 * @version 1.0.0
 */
uint32_t LLMYLIB_IMPL_factorial(uint32_t number)
{
	if(number == 0)
	      return 1;
	   else
		   return number * LLMYLIB_IMPL_factorial(number-1);
}
