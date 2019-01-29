# Overview
This document explains how to use Fontforge to generate a MicroEJ Font.

# Requirements
* MicroEJ Studio or SDK 4.1.5 or later
* Python with PIL module (2 or later)
* FontForge (FontForge-2017-07-31 or later)

# Usage
## Install the dependencies
1. Download and Install Python and Pillow [how to](https://pillow.readthedocs.io/en/3.0.x/index.html)
2. Download and install FontForge [website](https://fontforge.github.io)
3. Add ffpython installed by Fontforge to the system path

## Usage
### Generate the images
1. Copy fontConverter.py to directory where it can find the Font file
2. Use a command line to execute fontConverter:
```
python fontConverter fontFilePath sizeInPixel [startRange endRange [bpp [algorithm]]
                fontFilePath: the path to the font file
                sizeInPixel: the size in pixel to use for the output (c.f. algorithm)
                startRange: (default = 0x21) hexadecimal value of the first unicode character to export
                endRange: (default = 0x24F)  hexadecimal value of the last unicode character last character to export, it is recommanded to export a wide range
                bpp: (default = 0x24F) the bpp to use for the export
                algorithm: (default = 0) the algorithm to use :
                        0: capitalHeight => The sizeInPixel define the size of a capital X (the generated image height zill be the bigest height required to print all the character in range)
                        1: emHeight => The sizeInPixel define the size of an em (the generated image height will be the bigest height required to print all the character in range)
                        2: bestFit => The sizeInPixel define the size of the final output (the size of the font used will be the biggest possible to fit all the characters within the sizeInPixel)
```

The output should looks like:
```
generating images
Mapping em to 14px
Resizing images to 15px
Generate EJF
EJF file can be found at [PATH]\NotoSerif-Regular_capitalHeight_10px.ejf
```

### Import the images to an EJF
In MicroEJ SDK:
1. Import the MicroEJ font
2. Adapt the base line to Map your font 's base line

[//]: # (Markdown)
[//]: # (Copyright 2019 IS2T. All rights reserved.)
[//]: # (For demonstration purpose only.)
[//]: # (IS2T PROPRIETARY. Use is subject to license terms.)
