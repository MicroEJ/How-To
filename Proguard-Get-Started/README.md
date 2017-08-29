# Overview
This document explains how to use Proguard in your application. For example, Proguard can be used to remove all log message in binary production.

# Usage
## ADD THE DEPENDENCIES
1. Open the `module.ivy`
2. Copy following code inside `<ea:build organisation=...></ea:build>`
```
<ea:plugin organisation="com.is2t.easyant.plugins" module="obf-proguard" revision="+"/>
```

## ADD PROGUARD RULES
1.  **File** → **New** → **File**
    * File name : **module.pro** (you shall use this name)
2. Copy following code inside this file
```
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
```

## BUILD EASYANT
1. Click on **Build selected EasyAnt projects**
2. Verify **Proguard** is call during the build. Open **EasyAnt Console** and search this following line :  
```
obf-proguard:obfuscate:
     [move] Moving 1 file to C:\Users\mmartins\Documents\Work\SupportWorkspace\a\target~\build-env
     [copy] Copying 1 file to C:\Users\mmartins\Documents\Work\SupportWorkspace\a\target~\proguard
[proguard] ProGuard, version 5.1
 ```

## CHECKING
**Without Proguard**
```
14  ldc <String "1"> [1]
16  invokevirtual java.io.PrintStream.println(java.lang.String) : void [17]
19  aload_1
20  getfield com.mycompany.aA.a : java.util.logging.Logger [15]
23  ldc <String "Second"> [6]
25  invokevirtual java.util.logging.Logger.severe(java.lang.String) : void [23]
28  getstatic java.lang.System.out : java.io.PrintStream [16]
31  ldc <String "2"> [2]
33  invokevirtual java.io.PrintStream.println(java.lang.String) : void [17]
36  aload_1
37  getfield com.mycompany.aA.a : java.util.logging.Logger [15]
40  ldc <String "Third"> [7]
42  invokevirtual java.util.logging.Logger.config(java.lang.String) : void [23]
45  getstatic java.lang.System.out : java.io.PrintStream [16]
48  ldc <String "3"> [3]
```
**With Proguard**
```
5  ldc <String "1"> [1]
7  invokevirtual java.io.PrintStream.println(java.lang.String) : void [13]
10  getstatic java.lang.System.out : java.io.PrintStream [12]
13  ldc <String "2"> [2]
15  invokevirtual java.io.PrintStream.println(java.lang.String) : void [13]
18  getstatic java.lang.System.out : java.io.PrintStream [12]
21  ldc <String "3"> [3]
23  invokevirtual java.io.PrintStream.println(java.lang.String) : void [13]
```
