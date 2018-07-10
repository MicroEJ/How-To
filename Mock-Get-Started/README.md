# Overview
This project explain how to create a mock for a SNI function for the simulator.

# Usage
## How to add a mock to your project
For this example a native function will be used: `public static native int foo();`
This function will be in `MyClass` at `my.package`

* Add a native method to your Java application. This native method will have two implementations:
	* One in C code in the platform BSP project (this part is not covered in this document)
	* One in Java (J2SE) in a new Java project for the simulator
	
### Implement the J2SE mock
* Create a new Java project `Mock`
	* Open `File` > `New` > `EasyAnt project`
	* Choose `com.is2t.easyant.skeleton#microej-mock;+` as skeleton
	* Fulfill the form
	* Click on `Finish`
* Create a class with the **same name, same package** as your SNI (e.g. `my.package.MyClass`)
* Implement the SNI function as public with the **same signature** (as `public`, without the `native` keyword) (e.g. `public native int foo()`)

### Export the mock
* Build the Mock project with EasyAnt (**right click on the project** > `build with EasyAnt`).
* A `.rip` file is created in the `target~/artifacts` folder in the Mock project.
* From the `.rip` unzip the content of `content` folder into the `dropins` folder of your platform configuration project (e.g. [Platform-Name]-configuration/dropins).
* **Build you platform**.
* Run your Java application on the simulator.

## Create a Mock with a UI
A mock can show a separate UI to ease input/output.
An example is provided 
 * An application using native function [Mock-Get-Started-MyApp](Mock-Get-Started-MyApp\)
 * A mock opening a UI [Mock-Get-Started-MyMock](Mock-Get-Started-MyMock\)

# Requirements
* MicroEJ SDK 4.1.4 or later
* A MicroEJ 4.1 Platform Reference Implementation imported into the MicroEJ repository.
* An activated Evaluation or Production license.
* A working Java application that runs on the above platform simulator

# Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.

# Source
N/A

# Restrictions
None.


[//]: # (Markdown)
[//]: # (Copyright 2017-2018 IS2T. All rights reserved.)
[//]: # (For demonstration purpose only.)
[//]: # (IS2T PROPRIETARY. Use is subject to license terms.)