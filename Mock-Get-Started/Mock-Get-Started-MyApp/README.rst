.. Copyright 2019-2021 MicroEJ Corp. All rights reserved.
.. Use of this source code is governed by a BSD-style license that can be found with this software.

Overview
========

This project uses different ways to communicate with the native world.

Code highlight
--------------

-  `com.microej.example.mock.MySNI <src/main/java/com/microej/example/mock/MySNI.java>`__
   describes the SNI functions
-  `com.microej.example.mock.MySP <src/main/java/com/microej/example/mock/MySP.java>`__
   uses the Shielded Plug
-  `database-definition.xml <src/main/resources/database-definition.xml>`__
   describes the database used for Shielded plug.

Usage
=====

1. Right Click on the project
2. Select **Run as -> Run Configuration**
3. Select **Run Configuration** configuration kind
4. Click on **New launch configuration** icon
5. In **Execution** tab

   -  Set the target platform

6. In **Configuration** tab

   -  Go to **Libraries -> Shielded Plug**

      -  Set the database to
         **${project_loc:Mock-Get-Started-MyApp}/src/main/resources/database-definition.xml**

7. Press **Apply**
8. Press **Run**

Requirements
============

This example has been tested on:

-  MicroEJ SDK 5.1
-  With a platform that contains:

   -  EDC-1.2
   -  BON-1.3
   -  MICROUI-2.0
   -  SP-2.0
   -  Mock UI

Dependencies
============

*All dependencies are retrieved transitively by Ivy resolver*.

Source
======

N/A

Restrictions
============

None.