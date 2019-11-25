.. Copyright 2018-2019 MicroEJ Corp. All rights reserved.
.. Use of this source code is governed by a BSD-style license that can be found with this software.

Overview
========

This document describes how to add a native call to a monolithic
application running on a mono-sandbox platform.

Important notice, In order to ease the reader’s understanding, the
project from this HowTo is released in the final, working state one
should obtain when following the instructions below.

Requirements
============

Hardware Requirements
---------------------

The native code provided has been created for the STMicroelectronics
STM32F746G-DISCO development board available from
`st.com <https://www.st.com/en/evaluation-tools/32f746gdiscovery.html>`__.

It can be ported to any hardware with a **LED**.

Software Requirements
---------------------

This example has been tested on:

-  MicroEJ SDK 5.1
-  With a Platform Reference Platform that contains:

   -  EDC-1.2
   -  BON-1.2

STM32F746G-DISCO Platform Reference Implementation requirements
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

-  MicroEJ platform reference implementation STM32F746-DISCO Platform
   available from
   `developer.microej.com <http://developer.microej.com/index.php>`__
-  Keil MDK-ARM version 5.25 available from
   `keil.com <http://www2.keil.com/mdk5>`__

Prerequisite Knowledge
----------------------

This tutorial is built upon an understanding of the MicroEJ SDK, MicroEJ
architectures, MicroEJ Platforms and MicroEJ applications as well as at
least one native BSP compiler and IDE.

The user of this tutorial should be able to create a “Hello World”
application in Java, build the application, build the BSP and flash the
firmware image onto the targeted board.

Other development boards, reference platforms and compilers can be used,
however the instructions in this tutorial are specifically written for
these items and will not apply without changes to other environments.

Setup the workspace
===================

Import the example projects into the MicroEJ SDK: 

  - Click on **File** -> **Import**
  - Select **General** -> **Existing Project into Workspace**
  - **Browse** to root directory
  - Check **Search for nested projects** checkbox
  - Select all the projects
  - Click on **Finish**
     .. image:: screenshots/ImportProjects.png
       :alt: Import context


Projects Overview
-----------------

-  ``SNI-LED`` contains this README
-  ``HelloWorld`` is a standalone application
-  ``NativeAPIs`` is a project that defines the native functions to
   manage the LED

Add a Native Function in the Platform to Control a LED
======================================================

Modify the platform to add the capability to call a native C function
from Java.

1. Configure modules

   1. Open the ``[XXX]-configuration/[XXX].platform`` file
   2. Go to **Content** tab.
   3. Check **Java to C interface** -> **SNI API** module

     .. image:: screenshots/CheckSNI.png
       :alt: Check SNI

2. Build the platform

   1. Go to **Overview** tab
   2. Click on **Build Platform** in the **Overview**->**Build section**
   3. Once the platform is built, the ``[Platform-name]-[version]``
      project is created in the workspace

     .. image:: screenshots/BuildPlatform.png
       :alt: Build Platform

3. Add a native call to the HelloWorld Java application

   1. Native function definition

      -  The project ``NativeAPIs`` is used to define the native
         functions
      -  `com.microej.Led <java/NativeAPIs/src/main/java/com/microej/Led.java>`__
         defines the native function to manage the LED

         -  ``Led.initNative()`` is called at start-up to initialize the
            LED ``private static native void initNative();``
         -  ``Led.switchLed(boolean on)`` is called to set the state of
            the LED

            -  ``public static void switchLed(boolean on);`` provides
               the APIs to the java code.
            -  ``private static native void switchLedNative(boolean on);``

      -  Optional, but highly recommended: To use the simulator a Mock should
         be created. An example of a mock usage is provided in
         `Example-Standalone-Java-C-Interface <https://github.com/MicroEJ/Example-Standalone-Java-C-Interface/tree/master/CallingCFromJava#adding-a-mock-of-the-native-function-to-the-jpf>`__

   2. Call the native function in the HelloWorld application

      -  The project ``HelloWorld`` depends on ``NativeAPIs``
      -  `com.microej.feature.HelloWorld <java/HelloWorld/src/main/java/com/microej/feature/HelloWorld.java>`__
         uses ``LED`` to toggle the LED

4. Build the HelloWorld Java application

   1. Right-click on the HelloWorld project
   2. Select **Run-As**->**Run Configuration**
   3. Right-click on **MicroEJ Application**
   4. Select **New**
   5. In **Execution** tab

      1. Set your platform that was built in step 3
      2. Check **Execute on device**
      3. Set **Settings** to **Build and deploy**

   6. **Run** will generated a ``microejapp.o`` in the platform BSP
      folder

     .. image:: screenshots/RunAsDevice.png
       :alt: Execute on device


5. Add the native LED C implementation to the BSP in the third party C
   IDE

   -  `LEDs.c <native/src-led/LEDs.c>`__ provides the implementation of
      the native C function defined in ``NativeAPIs``
   -  This implementation is done for the STM32F746-DISCO board, to add
      it to Keil IDE follow these steps:

      1. Open the Keil project in the platform [XXX]-bsp/ project
      2. Right-click on the ``MicroEJ/Core`` folder
      3. Select **Add Existing Files to Group ‘MicroEJ/Core’**

         1. Browse to the file `LEDs.c <native/src-led/LEDs.c>`__ in the
            native repository
         2. Click **Add**
         3. Click **Close**

      4. Build the project by pressing **F7**
      5. Flash the firmware on the board pressing **F8**
      6. Verify the Green LED LD1 is blinking on for one second and off
         for one second

Going Further
=============

1. Learn more about Java/C communication with this example:
   `Example-Standalone-Java-C-Interface <https://github.com/MicroEJ/Example-Standalone-Java-C-Interface>`__
2. Adapt this HelloWorld to run into a Multi Sandbox kernel
   `Single-App-to-Multi-App-Platform <../Single-App-to-Multi-App-Platform/>`__.


