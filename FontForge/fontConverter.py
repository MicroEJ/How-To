# Copyright 2019 MicroEJ Corp. All rights reserved.
# Use of this source code is governed by a BSD-style license that can be found with this software.

import os
import sys
import struct
import shutil
from PIL import Image
from shutil import make_archive

def genFontForgeFile(fileName):
	file = open(fileName,"w")
	file.write("import fontforge\n")
	file.write("import os\n")
	file.write("import struct\n")
	file.write("import sys\n")
	file.write("import math\n")
	file.write("# Get parameters\n")
	file.write("fontFile = sys.argv[1]\n")
	file.write("outputSizeInPx = int(sys.argv[2])\n")
	file.write("startRange = int(sys.argv[3])\n")
	file.write("endRange = int(sys.argv[4])\n")
	file.write("bpp = int(sys.argv[5])\n")
	file.write("dirName = sys.argv[6]\n")
	file.write("algorithm = int(sys.argv[7])\n")
	file.write("if not os.path.exists(dirName):\n")
	file.write("	os.mkdir(dirName)\n")
	file.write("F = fontforge.open(fontFile)\n")
	file.write("emHeight = F.em\n")
	file.write("capHeight = F.capHeight\n")
	file.write("maxSize = outputSizeInPx + 1\n")
	file.write("# Computing the size required depending on the algorithm\n")
	file.write("height = outputSizeInPx\n")
	file.write("if algorithm == 0:\n")
	file.write("	height=int(math.floor(outputSizeInPx*emHeight/(capHeight+F.descent)))\n")
	file.write("elif algorithm == 1:\n")
	file.write("	height=int(math.floor(outputSizeInPx*emHeight/capHeight))\n")
	file.write("# In case the algorithm tries a size too big to fit in the request, reduce it\n")
	file.write("while(maxSize>outputSizeInPx):\n")
	file.write("	maxSize=0\n")
	file.write("	print(\"Mapping em font size to \"+str(height)+'px')\n")
	file.write("    # Filter only the requested characters, this will return them in unicode order.\n")
	file.write("	for glyph in F.glyphs('encoding'):\n")
	file.write("		unicode = glyph.unicode\n")
	file.write("		if unicode > endRange:\n")
	file.write("			break\n")
	file.write("		elif unicode >= startRange:\n")
	file.write("			try:\n")
	file.write("				filename = dirName+'/'+hex(unicode) + \".png\"\n")
	file.write("				glyph.export(filename, height, bpp)\n")
	file.write("                # bestFit will check the generated char size to generate a smaller one if it was too big.\n")
	file.write("				if algorithm==3:\n")
	file.write("					with open(filename, 'rb') as fp:\n")
	file.write("						head = fp.read(24)\n")
	file.write("						check = struct.unpack('>i', head[4:8])[0]\n")
	file.write("						img_width, img_height = struct.unpack('>ii', head[16:24])\n")
	file.write("						maxSize = max(maxSize, img_height)\n")
	file.write("						if img_height>outputSizeInPx:\n")
	file.write("							height=height-1\n")
	file.write("							break;\n")
	file.write("			except:\n")
	file.write("				pass\n")
	file.close()

# Resize an image to a fixed size, adding white place on top of it if it is smoller.
def resize_img(image_path, outputSizeInPx):
	# Takes only the png.
	with open(image_path, 'rb') as fp:
				head = fp.read(24)
				check = struct.unpack('>i', head[4:8])[0]
				if check != 0x0d0a1a0a:
					return
	im = Image.open(image_path)
	old_width, old_height = im.size

	# Center the image
	x1 = 0
	y1 = outputSizeInPx - old_height

	newImage = Image.new("RGB", (old_width, outputSizeInPx), "white")
	newImage.paste(im, (x1, y1, x1 + old_width, y1 + old_height))
	im.close()
	if not image_path.endswith('.png'):
		os.remove(image_path)
		image_path = image_path + '.png'

	newImage.save(image_path)
	newImage.close()

# Resize the images to be the size of the tallest image
def resize(directory):
	outputSizeInPx = 0
	# Compute the size of the tallest image.
	for entry in os.listdir(directory):
		srcPath = os.path.join(directory, entry)
		# Takes only the png images.
		if os.path.isfile(srcPath):
			with open(srcPath, 'rb') as fp:
				head = fp.read(24)
				check = struct.unpack('>i', head[4:8])[0]
				if check == 0x0d0a1a0a:
					img_width, img_height = struct.unpack('>ii', head[16:24])
					outputSizeInPx = max(outputSizeInPx, img_height)

	print("Resizing images to "+str(outputSizeInPx)+'px')
	# Resizing all the images
	for entry in os.listdir(directory):
		srcPath = os.path.join(directory, entry)
		if os.path.isfile(srcPath):
			resize_img(srcPath, outputSizeInPx)

# Takes all the image in a directory to generate an EJF file from them.
# directory = directory to scan.
def generateEJF(directory):
	print('Generating EJF')
	header = open(os.path.join(directory, 'Header'),"w")
	header.write('<?xml version="1.0" encoding="UTF-8" standalone="no"?><FontGenerator><Informations Vendor="IS2T" Version="0.8"/><FontCharacterProperties>')
	height = 0
	# Check the images available to create the header that will list them as well as the height.
	for entry in os.listdir(directory):
		srcPath = os.path.join(directory, entry)
		if os.path.isfile(srcPath) and entry.endswith('.png'):
			if height==0:
				with open(srcPath, 'rb') as fp:
					head = fp.read(24)
					check = struct.unpack('>i', head[4:8])[0]
					img_width, img_height = struct.unpack('>ii', head[16:24])
					height = img_height
			id = entry[:-4]
			os.rename(srcPath, os.path.join(directory, id))
			header.write('<Character Index="'+id+'" LeftSpace="0" RightSpace="0"/>')
    # Compute the style from the font file name.
	style = ""
	if "bold" in directory.lower():
		style = 'b'
	if "italic" in directory.lower():
		style = style + 'i'
	if "underline" in directory.lower():
		style = style + 'u'

	if len(style) == 0:
		style = 'p'
	header.write('</FontCharacterProperties><FontProperties Baseline="'+str(int(height*2/3))+'" Filter="" Height="'+str(height)+'" Name="'+directory[2:]+'_out_'+str(height)+'px" Space="2" Style="'+style+'" Width="-1"><Identifier Value="34"/></FontProperties></FontGenerator>')
	header.close()
	# An EJF file is just a zip.
	make_archive(directory,'zip',directory)
	ejf = directory+'_out_'+str(height)+'px.ejf'
	if os.path.exists(ejf):
		os.remove(ejf)
	os.rename(directory+'.zip', ejf)
	shutil.rmtree(directory, ignore_errors=True)
	print("EJF file can be found at "+os.path.abspath(ejf))

# Main entry point
# fontFile = font file to convert
# outputSizeInPx = the size in pixel to use for the output
# startRange = start of the range of char to generate
# endRange = end of the range of char to generate
# bpp = bpp to use for the generation
# algorithm = id of the algorithm to use [0-3]
def main(fontFile, outputSizeInPx, startRange, endRange, bpp, algorithm):
	algo = "bodyHeight"
	if algorithm==1:
		algo = "capitalHeight"
	elif algorithm==2:
		algo = "emHeight"
	elif algorithm==3:
		algo = "bestFit"


	# Working dir like fontName_bodyHeight_15px
	directory = os.path.splitext(fontFile)[0]+'_'+algo+'_'+str(outputSizeInPx)+'px'
	print("Generating images")
	fileName = "gen.fontforge.py.tmp"
	genFontForgeFile(fileName)
	os.system("ffpython "+fileName+' '+ fontFile+' '+ str(outputSizeInPx)+' '+ str(startRange)+' '+ str(endRange)+' '+ str(bpp) +' '+ directory+' '+str(algorithm))
	os.remove(fileName)
	resize(directory)
	generateEJF(directory)

def checkparam():
	if len(sys.argv)<3 or len(sys.argv)==4:
		print("USAGE:\tfontConverter fontFilePath sizeInPixel [startRange endRange [bpp [algorithm]]")
		print("\t\tfontFilePath: the path to the font file")
		print("\t\tsizeInPixel: the size in pixel to use for the output (c.f. algorithm)")
		print("\t\tstartRange: (default = 0x21) hexadecimal value of the first unicode character to export")
		print("\t\tendRange: (default = 0x24F)  hexadecimal value of the last unicode character last character to export, it is recommanded to export a wide range")
		print("\t\tbpp: (default = 8) the bpp to use for the export")
		print("\t\talgorithm: (default = 0) the algorithm to use : ")
		print("\t\t\t0: bodyHeight => The sizeInPixel define the height of `Xg` (the generated image height will be the biggest height required to print all the character in range)")
		print("\t\t\t1: capitalHeight => The sizeInPixel define the size of a capital X (the generated image height will be the biggest height required to print all the character in range)")
		print("\t\t\t2: emHeight => The sizeInPixel define the size of an em (the generated image height will be the biggest height required to print all the character in range)")
		print("\t\t\t3: bestFit => The sizeInPixel define the size of the final output (the size of the font used will be the biggest possible to fit all the characters within the sizeInPixel)")
		exit(-1)

checkparam()
fontFile = sys.argv[1]
outputSizeInPx = int(sys.argv[2])
# Default values
startRange = 0x21
endRange = 0x24F
bpp = 8
algorithm = 0

# Add extended parameters
if len(sys.argv)>3:
	startRange = int(sys.argv[3], 16)
	endRange = int(sys.argv[4], 16)
if len(sys.argv)>5:
	bpp = int(sys.argv[5])
if len(sys.argv)>6:
	algorithm = int(sys.argv[6])

main(fontFile, outputSizeInPx, startRange, endRange, bpp, algorithm)
