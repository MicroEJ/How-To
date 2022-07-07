.. Copyright 2019-2022 MicroEJ Corp. All rights reserved.
.. Use of this source code is governed by a BSD-style license that can be found with this software.

Overview
========

This document explains how to convert a standalone app into a sandboxed
app.

Usage
=====

CREATE THE APPLICATION
----------------------

1. Use the wizard to create a new sandboxed app

   1. **File** → **New** → **MicroEJ Sandboxed Application Project**

ADD THE DEPENDENCIES
--------------------

1. Open the ``module.ivy`` file from the **standalone project**
2. Copy all the dependencies located in
   ``<dependencies></dependencies>``
3. Open the ``module.ivy`` file from the **sandboxed project**
4. Paste the copied dependencies into ``<dependencies></dependencies>``
5. Add the following dependency : ``<dependency org="ej.library.wadapps"
   name="framework" rev="1.11.0" />``

COPY THE SOURCES AND RESOURCES
------------------------------

1. From the **standalone project**, copy the content of
   ``src/main/java``
2. Paste it into ``src/main/java`` in the **sandboxed project**.
3. From the **standalone project** copy the content of
   ``src/main/resources``
4. Paste it into ``src/main/resources`` in the **sandboxed project**.

LINK THE PROJECT TO A SANDBOXED APP ENTRY POINT
-----------------------------------------------

A sandboxed app needs a specific entry point which is not the main of
the standalone app.

-  If you have a GUI (i.e. using a GUI library such as MicroUI):

   1. Create a new class implementing ``ej.wadapps.app.Activity``.
   2. On the **onStart()**, call your project’s **main**.

-  If you do not have any GUI:

   1. Create a new class implementing
      ``ej.wadapps.app.BackgroundService``.
   2. On the **onStart()**, call your project’s **main**.

UPDATE THE META-INF
-------------------

1. Move the **system.properties** except the services declarations to
   the **application.properties** file

   -  In your code, replace the calls to ``System.getProperty()`` by
      calls to
      ``ServiceLoaderFactory.getServiceLoader().getService(ApplicationsManager.class).getCurrentApplication().getProperty()``

2. For each local service declared

   -  create a file with the fully qualified name of the service in the
      folder **services**
   -  write one line containing the fully qualified name of the
      implementation class into this file

3. Update the ``MANIFEST.MF`` :

   -  Set the **Application-Activities** to the created activities (if
      you have a GU).
   -  Set the **Application-BackgroundServices** to the created
      background services (if you do not have a GUI).

RUN ON MICROEJ SIMULATOR
------------------------

1. Right Click on the project
2. Select **Run as -> MicroEJ Application**
3. Select **BackgroundServicesStandalone** or **ActivitiesStandalone**
4. Select your virtual device
5. Press **Ok**

RUN ON A DEVICE
---------------

LOCAL DEPLOY
~~~~~~~~~~~~

1. Right Click on
   /src/.generated~/.java/**YourProject**/generated/YourProjectEntryPoint.java
2. Select **Run as -> Run Configurations…**
3. Select **MicroEJ Application** configuration kind
4. Click on **New launch configuration** icon
5. In **Execution** tab

   1. In **Target** frame, in **Platform** field, select a relevant virtual
      device
   2. In **Execution** frame

      1. Select **Execute on Device**
      2. In **Settings** field, select **Build & Deploy**

6. In **Configuration** tab

   1. In **Board** frame

      1. Set **Host** field to your board IP address

7. Press **Apply**
8. Press **Run**
