.. Copyright 2019 MicroEJ Corp. All rights reserved.
.. Use of this source code is governed by a BSD-style license that can be found with this software.

Overview
========

This document explains how to use Proguard in your application. ProGuard
is an open source command-line tool that shrinks, optimizes and
obfuscates Java code. It is able to optimize bytecode as well as detect
and remove unused instructions. For example, Proguard can be used to
remove all log message in binary production. This project is based on
Proguard version 5.1.

Requirements
============

This how-to has been tested on:

-  MicroEJ SDK 5.1

Usage
=====

ADD THE DEPENDENCIES
--------------------

1. Open the ``module.ivy``.
2. Copy the following code inside
   ``<ea:build organisation=...></ea:build>`` :

   ::

      <ea:plugin organisation="com.is2t.easyant.plugins" module="obf-proguard" revision="+"/>

ADD PROGUARD RULES
------------------

1. **File** → **New** → **File**

   -  File name : **module.pro** → you must create a file with this
      name.

2. Copy the following code inside this file :

   ::

       -assumenosideeffects class java.util.logging.Logger {
       public static java.util.logging.Logger getLogger(...);
       public static Logger getLogger(...);
       public void log(...);
       public void severe(...);
       public void config(...);
       public void fine(...);
       public void finer(...);
       public void finest(...);
       public void info(...);
       public void warning(...);
       }

An example file is available @\ `module.pro <module.pro>`__.

BUILD EASYANT
-------------

1. Click on **Build selected EasyAnt projects**.
2. Verify **Proguard** is called during the build. Open **EasyAnt
   Console** and search for the following lines :

   ::

      obf-proguard:obfuscate:
         [move] Moving 1 file to C:\Users\mmartins\Documents\Work\SupportWorkspace\a\target~\build-env
         [copy] Copying 1 file to C:\Users\mmartins\Documents\Work\SupportWorkspace\a\target~\proguard
      [proguard] ProGuard, version 5.1

CHECKING
--------

Bytecode is compared. You can find the **.jar** of your application in
the folder **target~/artifacts**.

**Example Java**

   ::

      public Main() {
         System.out.println("1");
         LOGGER.severe("severe call");
         System.out.println("2");
         LOGGER.config("config call");
         System.out.println("3");
      }

**Bytecode generated**

- We can see than **severe(…) and config(…)** methods are called (L.25 & L.42).

   ::

      14  ldc <String "1"> [1]
      16  invokevirtual java.io.PrintStream.println(java.lang.String) : void [17]
      19  aload_1
      20  getfield com.mycompany.aA.a : java.util.logging.Logger [15]
      23  ldc <String "severe call"> [6]
      25  invokevirtual java.util.logging.Logger.severe(java.lang.String): void [23]
      28  getstatic java.lang.System.out : java.io.PrintStream [16]
      31  ldc <String "2"> [2]
      33  invokevirtual java.io.PrintStream.println(java.lang.String) : void [17]
      36  aload_1
      37  getfield com.mycompany.aA.a : java.util.logging.Logger [15]
      40  ldc <String "config cal"> [7]
      42  invokevirtual java.util.logging.Logger.config(java.lang.String) : void [23]
      45  getstatic java.lang.System.out : java.io.PrintStream [16]
      48  ldc <String "3"> [3]

**Proguard**

- After Proguard processing, we can see than **severe(…) and config(…)** are
  removed.

   ::

      5  ldc <String "1"> [1]
      7  invokevirtual java.io.PrintStream.println(java.lang.String) : void [13]
      10  getstatic java.lang.System.out : java.io.PrintStream [12]
      13  ldc <String "2"> [2]
      15  invokevirtual java.io.PrintStream.println(java.lang.String) : void [13]
      18  getstatic java.lang.System.out : java.io.PrintStream [12]
      21  ldc <String "3"> [3]
      23  invokevirtual java.io.PrintStream.println(java.lang.String) : void [13]

- Java code

   ::

      public Main() {
         System.out.println(“1”);
         System.out.println(“2”);
         System.out.println(“3”);
      }
