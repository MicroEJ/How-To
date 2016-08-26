<!--
	Markdown
	Copyright 2015-2016 IS2T. All rights reserved.
	IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

# Overview
This library contains simple examples to understand how to use the main Widgets library APIs. It is recommended to study them in the following order :

- [com.microej.howto.mwt.colors.Gradient](/Widgets-Get-Started/src/main/java/com/microej/howto/mwt/colors/Gradient.java)

![Gradient](screenshots/Gradient.png)


# Usage
For each example:
## Run on MicroEJ Simulator
1. Right Click on the example to run `.java` file
2. Select **Run as -> MicroEJ Application**
3. Select your platform 
4. Press **Ok**


## Run on device
### Build
1. Right Click on the example to build `.java` file
2. Select **Run as -> Run Configuration** 
3. Select **MicroEJ Application** configuration kind
4. Click on **New launch configuration** icon
5. In **Execution** tab
	1. In **Target** frame, in **Platform** field, select a relevant platform (but not a virtual device)
	2. In **Execution** frame
		1. Select **Execute on Device**
		2. In **Settings** field, select **Build & Deploy**
6. Press **Apply**
7. Press **Run**
8. Copy the generated `.out` file path
	
# Requirements
* MicroEJ Studio or SDK 4.0 or later
* A platform with at least:
	* EDC-1.2 or later
	* BON-1.2 or later
	* MICROUI-2.0 or later

* Add the following line to your `module.ivy` or your `ivy.xml`:
> `<dependency org="ej.library.ui" name="widget" rev="2.0.0-alpha.10"/>`


# Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.


# Source
N/A

# Restrictions
None.