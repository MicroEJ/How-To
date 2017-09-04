#
# Module.pro 1.0
#
# Copyright 2017 IS2T. All rights reserved.
# Use of this source code is subject to license terms.
#

-verbose
-printusage

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
