<!--
	Markdown
-->

# Overview
This mock opens an UI frame to play with the SNI functions.

## Code highlight
* [com.microej.example.mock.MySNI](src\main\java\com\microej\example\mock\MySNI.java) describes the SNI functions
* [com.microej.example.mock.MySP](src\main\java\com\microej\example\mock\MySP.java) uses the Shielded Plug
* [com.microej.example.mock.ui.MyMockFrame](src\main\java\com\microej\example\mock\ui\MyMockFrame.java) is the main UI frame, the constructor will create the window and show it.
* [MySNI.init()](src\main\java\com\microej\example\mock\MySNI.java) uses the Singleton of MyMockFrame, it will call the constructor that will popup the mock UI

# Usage
* Build the Mock project with EasyAnt (**right click on the project** > `build with EasyAnt`).
* A `.rip` file is created in the `target~/artifacts` folder in the Mock project.
* From the `.rip` unzip the content of `content` folder into the `dropins` folder of your platform configuration project (e.g. [Platform-Name]-configuration/dropins).
* **Build you platform**.
* Run the Java application on the simulator.

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