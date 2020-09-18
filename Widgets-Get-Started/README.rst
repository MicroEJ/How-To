.. Copyright 2019 MicroEJ Corp. All rights reserved.
.. Use of this source code is governed by a BSD-style license that can be found with this software.

Overview
========

This library contains simple examples to understand how to use the main
Widgets library APIs. It is recommended to study them in the following
order :

-  `com.microej.howto.mwt.colors.Gradient <src/main/java/
   com/microej/howto/mwt/colors/Gradient.java>`__

.. figure:: screenshots/Gradient.png
   :alt: Gradient

Usage
=====

For each example:

Run on MicroEJ Simulator
------------------------

1. Right Click on the example to run ``.java`` file
2. Select **Run as -> MicroEJ Application**
3. Select your platform
4. Press **Ok**

Run on device
-------------

Build
~~~~~

1. Right Click on the example to build ``.java`` file
2. Select **Run as -> Run Configuration**
3. Select **MicroEJ Application** configuration kind
4. Click on **New launch configuration** icon
5. In **Execution** tab

   1. In **Target** frame, in **Platform** field, select a relevant
      platform (but not a virtual device)
   2. In **Execution** frame

      1. Select **Execute on Device**
      2. In **Settings** field, select **Build & Deploy**

6. Press **Apply**
7. Press **Run**
8. Copy the generated ``.out`` file path

Requirements
============

This example has been tested on:

-  MicroEJ SDK 5.1
-  With a ST STM32F746G-DISCO (480x272 display) board platform that contains:

   -  EDC-1.2
   -  BON-1.3
   -  MICROUI-2.2

Dependencies
============

*All dependencies are retrieved transitively by Ivy resolver*.

Source
======

N/A

Restrictions
============

None.
