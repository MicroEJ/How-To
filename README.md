# Overview

This project gathers the MicroEJ's how-tos. How-tos with some code are developed as standalone applications and as such can be run by following the associated instructions (see **README.md** file of each example).

Note that to run them on board :

* If you are using MicroEJ SDK
	* You need a supported board (see http://developer.microej.com/index.php?resource=JPF for a list of supported boards using MicroEJ SDK evaluation version)
	* And the associated platform reference implementation binary .jpf file (retrieve it from the previous link and import it into MicroEJ SDK)

* If you are using MicroEJ Studio
	* You need to convert them from standalone applications to sandboxed applications.
	* Follow the [How-To convert a standalone app into a sandboxed app](/StandaloneToSandboxed) guide.

# Details

**Available Examples**:
* [FoundationLibrary-Get-Started](FoundationLibrary-Get-Started): Contains a tutorial on how to add a Foundation Library for both simulated and embedded runtime.
* [MicroUI-Get-Started](MicroUI-Get-Started) : Contains simple examples to understand how to use the main MicroUI library APIs.
* [Proguard-Get-Started](Proguard-Get-Started) : Explains how to integrate Proguard in MicroEJ Studio/SDK.
* [Storage-Get-Started](Storage-Get-Started): Explains how to store/retrieve information in a sandboxed app.
* [StandaloneToSandboxed](StandaloneToSandboxed): Explains how to convert a standalone app into a sandboxed app.
* [Widgets-Get-Started](Widgets-Get-Started) : Contains simple examples to understand how to use the main widget library APIs.
* [Mock-Get-Started](Mock-Get-Started) : Explains how to create a mock with an  user interface for a SNI function for the simulator..
* [SNI-LED](SNI-LED): Use a native (C) function to toggle a LED.
* [Single-App-to-Multi-App-Platform](Single-App-to-Multi-App-Platform): Modify an existing standalone application associated with a Single-app Platform to be loaded dynamically.
* [Multi-App-Security-Manager](Multi-App-Security-Manager): Describes how to add a security manager to a multi-sandboxed platform.

# Requirements
* MicroEJ Studio or SDK 4.1.5 or later

# Usage
Each subfolder contains a distinct how-to described in the **README.md** file of each example.

# Changes
- See the change log file [CHANGELOG.md](CHANGELOG.md) located at the root of this repository.

# License
- See the license file [LICENSE.txt](LICENSE.txt) located at the root of this repository.

<!--
	Markdown
	Copyright 2017-2018 IS2T. All rights reserved.
	For demonstration purpose only.
	IS2T PROPRIETARY. Use is subject to license terms.
-->
