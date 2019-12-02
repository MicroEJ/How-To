.. Copyright 2019 MicroEJ Corp. All rights reserved.
.. Use of this source code is governed by a BSD-style license that can be found with this software.

Overview
========

This project explain how to create a mock for SNI and SP functions on
the simulator.

Usage
=====

How to add a mock to your project
---------------------------------

For this example a native function will be used:
``public static native int foo();`` This function will be in ``MyClass``
at ``my.package``

-  Add a native method to your Java application. This native method will
   have two implementations:

   -  One in C code in the platform BSP project (this part is not
      covered in this document)
   -  One in Java (J2SE) in a new Java project for the simulator

Implement the J2SE mock
~~~~~~~~~~~~~~~~~~~~~~~

-  Create a new Java project ``Mock``

   -  Open ``File`` > ``New`` > ``EasyAnt project``
   -  Choose ``com.is2t.easyant.skeleton#microej-mock;+`` as skeleton
   -  Fulfill the form
   -  Click on ``Finish``

-  Create a class with the **same name, same package** as your SNI
   (e.g. ``my.package.MyClass``)
-  Implement the SNI function as public with the **same signature** (as
   ``public``, without the ``native`` keyword)
   (e.g. ``public native int foo()``)

Export the mock
~~~~~~~~~~~~~~~

-  Build the Mock project with EasyAnt (**right click on the project** >
   ``build with EasyAnt``).
-  A ``.rip`` file is created in the ``target~/artifacts`` folder in the
   Mock project.
-  From the ``.rip`` unzip the content of ``content`` folder into the
   ``dropins`` folder of your platform configuration project
   (e.g. [Platform-Name]-configuration/dropins).
-  **Build you platform**.
-  Run your Java application on the simulator.

Create a Mock with a UI
-----------------------

A mock can show a separate UI to ease input/output. An example is
provided

-  An application using native function `Mock-Get-Started-MyApp
   <Mock-Get-Started-MyApp>`__
-  A mock opening a UI `Mock-Get-Started-MyMock <Mock-Get-Started-MyMock>`__


Requirements
============

This example has been tested on:

-  MicroEJ SDK 5.1
-  With a platform that contains:

   -  EDC-1.2
   -  BON-1.3
   -  MICROUI-2.0
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
