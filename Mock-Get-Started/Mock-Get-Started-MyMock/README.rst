.. Copyright 2019 MicroEJ Corp. All rights reserved.
.. Use of this source code is governed by a BSD-style license that can be found with this software.

Overview
========

This mock opens an UI frame to play with the SNI functions.

Code highlight
--------------

-  `com.microej.example.mock.MySNI <src\main\java\com\microej\example\mock\MySNI.java>`__
   describes the SNI functions
-  `com.microej.example.mock.MySP <src\main\java\com\microej\example\mock\MySP.java>`__
   uses the Shielded Plug
-  `com.microej.example.mock.ui.MyMockFrame <src\main\java\com\microej\example\mock\ui\MyMockFrame.java>`__
   is the main UI frame, the constructor will create the window and show
   it.
-  `MySNI.init() <src\main\java\com\microej\example\mock\MySNI.java>`__
   uses the Singleton of MyMockFrame, it will call the constructor that
   will popup the mock UI

Usage
=====

-  Build the Mock project with EasyAnt (**right click on the project** >
   ``build with EasyAnt``).
-  A ``.rip`` file is created in the ``target~/artifacts`` folder in the
   Mock project.
-  From the ``.rip`` unzip the content of ``content`` folder into the
   ``dropins`` folder of your platform configuration project
   (e.g. [Platform-Name]-configuration/dropins).
-  **Build you platform**.
-  Run the Java application on the simulator.

Requirements
============

This example has been tested on:

-  MicroEJ SDK 5.1
-  With a platform that contains:

   -  EDC-1.2
   -  BON-1.3
   -  SP-2.0

Dependencies
============

*All dependencies are retrieved transitively by Ivy resolver*.

Source
======

N/A

Restrictions
============

None.
