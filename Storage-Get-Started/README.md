<!--
	Markdown
	Copyright 2016 IS2T. All rights reserved.
	Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
-->

# Overview
MicroEJ firmware's disables access to the file system in application context. To store/retrieve information you should use a service provided by the firmware.
This service is called <code>Storage</code> and like any other services it can be retrieved by using the default `ServiceLoader` (if you want more information about Service, please read the firmware documentation).
> `Storage storage = ServiceLoaderFactory.getServiceLoader().getService(Storage.class);`

This service is based on key/value principe. The value is java's `InputStream` and the keys are java's `String` (with some restrictions on the allowed characters, see the javadoc). 

# Usage
## Store data
The `store` method needs an input stream and a key. During the method execution the given input stream is entirely read, closed and stored in the underlying file system (can be volatile or not depend on your firmware).

```java
String key = "MY_DATA"; //$NON-NLS-1$
try (ByteArrayInputStream bais = new ByteArrayInputStream("mydata".getBytes())) { //$NON-NLS-1$
   storage.store(key, bais);
} catch (IOException e) {
   e.printStackTrace();
}
```

## Retrieve data
Call the `load` method with the same key used to store the data, to retrieve it.

```java
try (InputStream stream = storage.load(key)) {
    // Do something with the input stream.
} catch (IOException e) {
    e.printStackTrace();
}
```

The application must close the input stream when it is no longer needed.

## List all stored data
An applciation can list all stored data by calling the <code>getIds</code> method. This method returns all data key's already stored.
```java		
try {
    for (String k : storage.getIds()) {
        System.out.println("Data available " + k);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

## Delete data
Call the `remove` method with the data key's to remove it.

```java
try {
    storage.remove(key);
} catch (IOException e) {
    e.printStackTrace();
}
```

## Run on MicroEJ Simulator
1. Right Click on the project
2. Select **Run as -> MicroEJ Application**
3. Select your virtual device. 
4. Press **Ok**

## Run on device
### Build
1. Right Click on the example to build `.java` file
2. Select **Run as -> Run Configuration** 
3. Select **MicroEJ Application** configuration kind
4. Click on **New launch configuration** icon
5. In **Execution** tab
	1. In **Target** frame, in **Platform** field, select a relevant virtual device.
	2. In **Execution** frame
		1. Select **Execute on Device**
		2. In **Settings** field, select **Local Deployment(...)**
6. In **Configurations** tab, set the options of the deployment
7. Press **Apply**
8. Press **Run**
	
# Requirements
* MicroEJ Studio or SDK 4.1 or later
* A virtual device

# Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.

# Source
N/A

# Restrictions
None.
