# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## 3.0.0 - 2022-07-07

### Changed

* Migrate examples from UI2 to UI3.
* Clean code.
* Refactor class names to be more meaningful.

## 2.0.0 - 2019-11-22

### Changed

   * Update to MicroEJ 5.
   * Use MMM.
 
### Removed

   * Remove Flyingimage.
   
## 1.0.2 - 2018-10-11

### Fixed

   * Fix minor api versions.

## 1.0.1 - 2016-10-03

### Added

  * Bigger font size for the following samples
  
    * [InputEvents](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/events/InputEvents.java)
    * [Text](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/font/Text.java)
  	
  * Updated colors for the following samples
  
    * [DeformedImages](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/image/DeformedImages.java)
    * [PictosWithCustomFont](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/font/PictosWithCustomFont.java)
    * [TilingWithImages](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/image/TilingWithImages.java)
  	
  * Simplified [PrimitivesAntiAliased](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/drawing/PrimitivesAntiAliased.java) example by removing code emulating antialiased primitives that are not yet available
  * Use square tiles in [TilingWithImages](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/image/TilingWithImages.java)
  * Use float division in [ScaledImages](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/image/ScaledImages.java) to ensure scaling factor is non-zero when reducing, but round scaling factor when enlarging for better rendering

## 1.0.0 - 2016-08-25

### Fixed

  * Initial revision.
  
---  
_Markdown_   
_Copyright 2019-2022 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  