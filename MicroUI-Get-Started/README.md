<!--
	Markdown
	Copyright 2016 IS2T. All rights reserved.
	Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
-->

# Overview
This library contains simple examples to understand how to use the main MicroUI library APIs. It is recommended to study them in the following order :


- [Primitives](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/drawing/Primitives.java)

![Primitives](screenshots/Primitives.png)

- [PrimitivesAntiAliased](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/drawing/PrimitivesAntiAliased.java)

![PrimitivesAntiAliased](screenshots/PrimitivesAntiAliased.png)

- [Text](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/font/Text.java)

![Text](screenshots/Text.png)

- [PictosWithCustomFont](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/font/PictosWithCustomFont.java)

![PictosWithCustomFont](screenshots/PictosWithCustomFont.png)

- [TransparentImages](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/image/TransparentImages.java)

![TransparentImages](screenshots/TransparentImages.png)

- [FlippedImages](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/image/FlippedImages.java)

![FlippedImages](screenshots/FlippedImages.png)

- [RotatedImages](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/image/RotatedImages.java)

![RotatedImages](screenshots/RotatedImages.png)

- [ScaledImages](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/image/ScaledImages.java)

![ScaledImages](screenshots/ScaledImages.png)

- [TilingWithImages](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/image/TilingWithImages.java)

![TilingWithImages](screenshots/TilingWithImages.png)

- [DeformedImages](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/image/DeformedImages.java)

![DeformedImages](screenshots/DeformedImages.png)

- [InputEvents](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/events/InputEvents.java)

![InputEvents](screenshots/InputEvents.png)

- [AnimationSampleWithFullRepaint](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/animation/AnimationSampleWithFullRepaint.java)

- [AnimationSampleWithFlyingImage](/MicroUI-Get-Started/src/main/java/com/microej/howto/microui/animation/AnimationSampleWithFlyingImage.java)


Note that the same set of resources (images and fonts) is used across all of the examples.

# Usage
Add the following line to your `module.ivy` or your `ivy.xml`:
> <dependency org="ej.api" name="microui" rev="[2.0.0-RC0,3.0.0-RC0[" conf="provided->*" />
	
# Requirements
- EDC 1.2 or later
- BON 1.2 or later
- MICROUI 2 or later

This project gathers some basic examples of the foundation libraries. Those examples are developed as standalone applications and as such can be run by following the associated instructions (see **README.md** file of each example).

Note that to run them on board :

* If you are using MicroEJ SDK
	* You need a supported board (see http://developer.microej.com/index.php?resource=JPF for a list of supported boards using MicroEJ SDK evaluation version)
	* And the associated platform reference implementation binary .jpf file (retrieve it from the previous link and import it into MicroEJ SDK)

* If you are using MicroEJ Studio
	* You need to convert them from standalone applications to sandboxed applications.
	* Follow the [How-To convert a standalone app into a sandboxed app](https://github.com/MicroEJ/How-To/tree/master/StandaloneToSandboxed) guide.

# Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.

# Credits
Font Awesome by Dave Gandy - http://fontawseome.io
(SIL OFL 1.1 license - http://scripts.sil.org/OFL)

# Source
N/A

# Restrictions
None.
