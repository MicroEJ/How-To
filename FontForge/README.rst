.. Copyright 2019 MicroEJ Corp. All rights reserved.
.. Use of this source code is governed by a BSD-style license that can be found with this software.

Overview
========

This document explains how to use Fontforge to generate a MicroEJ Font.

Requirements
============

This how-to has been tested on

-  MicroEJ Studio or SDK 5.1
-  Python (3.8.0) with Pillow module (6.2.1)
-  FontForge (FontForge-2019-08-01 64 bits)
   -  if you have a 64 bits OS, install a 64 bits font forge
-  Windows 10

Usage
=====

Install the dependencies
------------------------

1. Download and Install Python and Pillow `how
   to <https://pillow.readthedocs.io/en/3.0.x/index.html>`__
2. Download and install FontForge
   `website <https://fontforge.github.io>`__
3. Add ffpython installed by Fontforge to the system path

Usage
-----

Generate the images
~~~~~~~~~~~~~~~~~~~

Use a command line to execute fontConverter:

::

   python fontConverter fontFilePath sizeInPixel [startRange endRange [bpp [algorithm]]
                   fontFilePath: the path to the font file
                   sizeInPixel: the size in pixel to use for the output (c.f. algorithm)
                   startRange: (default = 0x21) hexadecimal value of the first unicode character to export
                   endRange: (default = 0x24F)  hexadecimal value of the last unicode character last character to export, it is recommanded to export a wide range
                   bpp: (default = 8) the bpp to use for the export
                   algorithm: (default = 0) the algorithm to use :
                           0: bodyHeight => The sizeInPixel define the height of `Xg` (the generated image height will be the biggest height required to print all the character in range)
                           1: capitalHeight => The sizeInPixel define the size of a capital `X` (the generated image height will be the biggest height required to print all the character in range)
                           2: emHeight => The sizeInPixel define the size of an em (the generated image height will be the biggest height required to print all the character in range)
                           3: bestFit => The sizeInPixel define the size of the final output (the size of the font used will be the biggest possible to fit all the characters within the sizeInPixel)

The output should looks like:

::

   Generating images
   Mapping em font size to 14px
   Resizing images to 15px
   Generating EJF
   EJF file can be found at [PATH]\NotoSerif-Regular_capitalHeight_10px.ejf

Algorithm
~~~~~~~~~

-  **bodyHeight** will use the height of ``Xg`` (the generated image
   height will be the biggest height required to print all the character
   in range). In case of increase of the characters range, the size of
   the printed characters will be the same, but the size of the EJF file
   may be different.
-  **capitalHeight** will use the height of a capital ``X`` (the
   generated image height will be the biggest height required to print
   all the character in range). In case of increase of the characters
   range, the size of the printed characters will be the same, but the
   size of the EJF file may be different.
-  **emHeight** will use the height of an em (the generated image height
   will be the biggest height required to print all the character in
   range). In case of increase of the characters range, the size of the
   printed characters will be the same, but the size of the EJF file may
   be different.
-  **bestFit** will use the height of the final output (the size of the
   font used will be the biggest possible to fit all the characters
   within the sizeInPixel). In case of increase of the characters range,
   the size of the printed characters may vary, but the size of the EJF
   file will stay the same.

Import the images to an EJF
~~~~~~~~~~~~~~~~~~~~~~~~~~~

In MicroEJ SDK
1. Import the MicroEJ font
2. Adapt the base line to Map your font's base line

