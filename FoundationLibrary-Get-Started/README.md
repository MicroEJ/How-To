# How to create a Foundation Library #
A MicroEJ Foundation Library is a MicroEJ library that provides core runtime APIs or hardware-dependent functionality. It is often connected to underlying C low-level APIs.

![LLAPI](assets\schema1.png)

## Prerequisites ##
1. MicroEJ DSK 4.1.1  installed
2. A Platform available in your workspace
	- [Getting started](http://developer.microej.com/getting-started-sdk.html)
3. Knowledge about Java and C programming
4. Basic knowledge about MicroEJ (building platform and deploy app)

# Create a Foundation library
## Define your API
* Select **File > EasyAnt Project **
	* Select  **build-microej-javaapi** Skeleton.
 <br/> If you use MicroEJ SDK 4.1.1, you have to change **microej.lib.name** and **rip.printableName** in [module.ivy](mylib-api/module.ivy) (microej.lib.name="mylib-1.0-api" rip.printableName="mylib-1.0-api")

	* Select  **File > Java > Class** menu item. <br />
In this class, define all methods but don't write the implementation. Each methods throw a RuntimeException.
```
public class MyLib {
	/**
	 * Returns the factorial of number.
	 *
	 * @param number
	 *            Number to returns the factorial
	 *
	 * @return the factorial of number.
	 */
		public static int factorial(int number) {
			throw new RuntimeException();
		}
}
```
Right-click on the chosen firmware project and select **Build With EasyAnt**. This may take several minutes.
After successful build, the application artifacts are available in application project build folder [target~/artifacts] and contains:
		* **Jar** file (MyLib-api.jar)
		* **Rip** file (MyLib-api.rip)

Unzip the file rip and copy the content directory in your **platform-configuration/dropins/javaAPIs**. <br />
**Rebuild** your platform. <br />After successful build, the Javadoc of your API is available in the MicroEJ Resource Center View.

## Write implementation

### Java
 * **File > EasyAnt Project**
 	* Select **build-microej-javaimpl** Skeleton.

Only if you use MicroEJ SDK 4.1.1 :<br/>
 * In [module.ivy](mylib-impl/module.ivy)
     * Update the revision to 3.+
     * Change microej.lib.name="mylib-impl-1.0" rip.printableName="mylib-impl-1.0"
     * Update dependency :
		 ```
		 	  <dependency org="ej.api" name="edc" rev="[1.2.0-RC0,2.0.0-RC0[" conf="provided->*" />
		 ```
     * Copy and paste the following code inside the [.project](mylib-impl/.project) file :
		 ```
			<buildSpec>
				<buildCommand>
					<name>org.eclipse.jdt.core.javabuilder</name>
					<arguments>
					</arguments>
				</buildCommand>
			</buildSpec>
			<natures>
				<nature>org.eclipse.jdt.core.javanature</nature>
				<nature>org.apache.ivyde.eclipse.ivynature</nature>
			</natures>
	```

* Select **File > Java > Class** menu item.
	* Class Name : **MyLib**  [MyLib.java](/mylib-impl/src/main/java/com/mycompany/MyLib.java)
	* Copy and paste the following code :  
	```
		/**
		* @see API javadoc
		*/
		public static int factorial(int number) {
			if (number < 0) {
				throw new IllegalArgumentException("Factorial cannot be negative");
			}
			if (number == 0 || number == 1) {
				return 1;
			}
			return MyLibNatives.factorial(number);
		}
	```
	In **Java**, you can test input value and throw an exception if the input value isn't correct. But we use the c language is faster for mathematical operations.

* Select **File > Java > Class** menu item.
	* Class Name : **MyLibNatives**
	* Copy and paste the following code :
	```
		/**
		 * Class define all native.
		 */
		 public class MyLibNatives {
			public native static int factorial(int number);
		 }
	```
Java and Native calls are separate. With this organization, it is more easy to write the MOCK and to split the two parts if one wants to change support of implementation.

### Native C

Copy and paste the following code inside the file content/intern/include/LLMYLIB_impl.h . 	
```
	 #define LLMYLIB_IMPL_factorial Java_com_mycompany_MyLibNatives_factorial
```

<br/ > **Note** : This file allows to cut the Java part and the C. If you change the packaging of your Java API, you don't have to change your LLAPI, just need to edit this file.

Copy and paste the following code inside the file content/include/LLMYLIB_impl.h.
```
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
```

Right-click on the chosen firmware project and select Build With EasyAnt. This may take several minutes.
After successful build, the application artifacts are available in application project build folder target~/artifacts and contains:
- **Jar** file (MyLib-api.jar)
- **Rip** file (MyLib-api.rip)

Unzip the **rip** file and copy the content directory in **platform-configuration/dropins/**.

**Rebuild your Platform**

### Write Example Application ###
* Select **File > MicroEJ Standalone Application Project**
	* Project Name : **mylib-test**
	* MicroEJ librairies
		* Check :
			* **EDC-1.2**
			* **MYLIB-1.0**

* Select **File > Class >**
	* Class Name : **TestMyLib**
	* Copy and paste the following code :
```
	public class TestMyLib {

		public static void main(String[] args) {
			System.out.println("(5!)=" + MyLib.factorial(5));
		}
	}
```

# Building for the simulator
## Getting a java.lang.UnsatisfiedLinkError exception
* Right-click on the project > **Run As > MicroEJ Application**
```
	The result of the previous step shall lead to this error message
	Exception in thread "main" java.lang.UnsatisfiedLinkError: No HIL client implementor found (timeout)
		at java.lang.Throwable.fillInStackTrace(Throwable.java:79)
		at java.lang.Throwable.<init>(Throwable.java:30)
		at java.lang.Error.<init>(Error.java:10)
		at java.lang.LinkageError.<init>(LinkageError.java:10)
		at java.lang.UnsatisfiedLinkError.<init>(UnsatisfiedLinkError.java:10)
		at com.mycompany.MyLib.factorial(MyLib.java:16)
		at my.company.TestMyLib.main(TestMyLib.java:8)
		at java.lang.MainThread.run(Thread.java:836)
		at java.lang.Thread.runWrapper(Thread.java:372)
	=============== [ Completed Successfully ] ===============

	SUCCESS
```
This is perfectly normal since in MyLibTest.java we declared **factorial** as a native function, when running the simulator, the Hardware In the Loop (HIL) engines expects to find some Java implementation emulating the behavior of the native function.


## Adding a mock of the native function to the JPF

Since our Java application relies on native C functions, on an embedded target, we would need to provide a C implementation. But given that we are running it on a Java simulator, we can emulate those functions using a Java mock.

The [mylib-mock project](/mylib-mock/) provides the mocks required for running the Java application on simulator.

Note that the mock method in /mylib-mock/.../MyLibNatives.java has the same fully qualified name (package-name.class-name.method-name) as the one declaring the native in /mylib-impl/.../MyLibNatives.java. This allows the linker to find which method simulates the native function.

* New **Java Project** > mylib-mock
* Click on **Next**
	* Select **Libraries**
		* Add **Variables**
			* Select **HILENGINE 2.1** (Use the last version)
* Click on **Finish**

Copy and paste the following code inside the project (**Tips** : If you don't want to make a mistake in definition of methods, copy directly [MyLibNatives.java](mylib-impl\src\main\java\com\mycompany\MyLibNatives.java) from your implementation in the project ) :

```
package com.mycompany;

/**
 * @see javadoc API
 */
public class MyLibNatives {

	public int factorial(int number) {
		if (number == 0) {
			return 1;
		}
		int fact = 1; // this will be the result
		for (int i = 1; i <= number; i++) {
			fact *= i;
		}
		return fact;
	}
}
```
## Export this mock in your platform ##
* Right-Click on [mylib-mock]  
	* **Export**
		* **Java**
			* **Jar** File
				* **Export** destination platform-configuration/dropins/mocks/dropins
				* **Finish**

* Right-click on the project  **Run As > MicroEJ Application**.
```
		=============== [ Initialization Stage ] ===============
		=============== [ Launching on Simulator ] ===============
		(5!)=120
		=============== [ Completed Successfully ] ===============

		SUCCESS
```
# Running the application on target
## Building for the target
	* Copy the simulator launcher
	* In **Execution** tab
		* In Target frame
			* Click the Browse button next to the JPF Field and select your platform
		* In Execution frame
			* Notice that "Execute on Device" radio button option is checked
	* Click on "Run"

### Opening the generated C project (BSP specific)
* From the **Project Explorer** view
	* Navigate to the [FoundationLibrary-bsp/Project/MicroEJ/MDK-ARM] folder
	* Double-click on the [Project.uvproj] file (this will open the BSP project in the MicroVision IDE)

### Getting a linker error (BSP specific)
* From the MicroVision IDE
	* Select **Project > Build Target** menu item (or press F7 keyboard shortcut)
	* A linker error message shall appear :
		```
			Undefined symbol Java_com_mycompany_MyLibNatives_factorial (referred from microejapp.o).
		```

This is perfectly normal since in [MyLibTest.java] we declared **factorial** as a native function, when building the MicroEJ project, the generated linker configuration file expects to find a C function definition matching the qualified name of the function.

## Fixing the linker error
### C Native function implementation
* Select **File > New > Source Folder** menu item
	* Set the **Folder Name** field to "src/main/c"
* Right-Click on the folder that you just created
	* Select **New > File** context menu item
	* Set the **File Name** field to "[LLMYLIB_impl.c](/mylib-impl/src/main/c/LLMYLIB_impl.c)"
	* Copy and paste the following code inside the generated [LLMYLIB_impl.c]. Notice the C function follows the strict naming define in the **content/intern/include/LLMYLIB_impl.h**.
```
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
```
* Right-click on the file that you just created
	* Select **Properties** context menu item
		* Copy the value of the **Resource > Location** field into the clipboard

#### Adding the C file to the BSP IDE project structure (BSP specific)
* Select the root node of your project
	* Right-Click and select **Add Group** this will add a group called "New Group"
	* Select this group and hit **F2** key so as to rename it to "Natives"
	* Right-Click on the **Natives** group and select **Add Existing Files to group 'Natives'...**
	* Navigate to the "[LLMYLIB_impl.c](/mylib-impl/src/main/c/LLMYLIB_impl.c)"
	* Click **Add**
	* Click **Close**

#### Getting a clean link (BSP specific)
* Select **Project > Build Target** menu item (or press F7 keyboard shortcut)
```
		*** Using Compiler 'V5.06 update 5 (build 528)', folder: 'C:\Keil_v5\ARM\ARMCC\Bin'
		Build target 'standalone'
		".\standalone\standalone.axf" - 0 Error(s), 0 Warning(s).
		Build Time Elapsed:  00:00:02
```
### Flashing the board (BSP specific)
* Connect your board
* Select **Flash > Download** menu item (or press F8 keyboard shortcut).

### Checking the behavior
* Set up a terminal on the board serial port and press the reset input. You shall get the following output :
	```
	VM START
	(5!)=120
	VM END (exit code = 0)
		```
