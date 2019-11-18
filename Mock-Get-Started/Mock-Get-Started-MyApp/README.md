<!--
	Markdown
-->

# Overview
This project uses different ways to communicate with the native world.

## Code highlight
* [com.microej.example.mock.MySNI](src\main\java\com\microej\example\mock\MySNI.java) describes the SNI functions
* [com.microej.example.mock.MySP](src\main\java\com\microej\example\mock\MySP.java) uses the Shielded Plug
* [database-definition.xml](resources\database-definition.xml) describes the database used for Shielded plug.

# Usage
1. Right Click on the project
2. Select **Run as -> Run Configuration**
3. Select **Run Configuration** configuration kind
5. Click on **New launch configuration** icon
5. In **Execution** tab
    * Set the target platform
6. In **Configuration** tab
    * Go to **Libraries -> Shielded Plug**
        * Set the database to **${project_loc:Mock-Get-Started-MyApp}/src/main/resources/database-definition.xml**
7. Press **Apply**
8. Press **Run**

# Requirements
* MicroEJ SDK 4.1.4 or later
* A MicroEJ 4.1 Platform with the Mock UI.
* An activated Evaluation or Production license.

# Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.

# Source
N/A

# Restrictions
None.

---
_Copyright 2017-2019 MicroEJ Corp. All rights reserved. 
_Use of this source code is governed by a BSD-style license that can be found with this software._  