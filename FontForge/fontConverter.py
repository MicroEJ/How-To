# Copyright 2019 IS2T. All rights reserved.
# For demonstration purpose only.
# IS2T PROPRIETARY. Use is subject to license terms.

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
	file.write("height = outputSizeInPx\n")
	file.write("if algorithm == 0:\n")
	file.write("	height=int(math.floor(outputSizeInPx*emHeight/capHeight))\n")
	file.write("while(maxSize>outputSizeInPx):\n")
	file.write("	maxSize=0\n")
	file.write("	print(\"Mapping em to \"+str(height)+'px')\n")
	file.write("	for glyph in F.glyphs('encoding'):\n")
	file.write("		unicode = glyph.unicode\n")
	file.write("		if unicode > endRange:\n")
	file.write("			break\n")
	file.write("		elif unicode >= startRange:\n")
	file.write("			try:\n")
	file.write("				filename = dirName+'/'+hex(unicode) + \".png\"\n")
	file.write("				glyph.export(filename, height, bpp)\n")
	file.write("				if algorithm==2:\n")
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

def resize_img(image_path, outputSizeInPx):
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

def resize(outputSizeInPx, directory):
	outputSizeInPx = 0
	for entry in os.listdir(directory):
		srcPath = os.path.join(directory, entry)
		if os.path.isfile(srcPath):
			with open(srcPath, 'rb') as fp:
				head = fp.read(24)
				check = struct.unpack('>i', head[4:8])[0]
				if check == 0x0d0a1a0a:
					img_width, img_height = struct.unpack('>ii', head[16:24])
					outputSizeInPx = max(outputSizeInPx, img_height)
				
	print("Resizing images to "+str(outputSizeInPx)+'px')
	for entry in os.listdir(directory):
		srcPath = os.path.join(directory, entry)
		if os.path.isfile(srcPath):
			resize_img(srcPath, outputSizeInPx)

def generateEJF(directory):
	print('Generate EJF')
	header = open(os.path.join(directory, 'Header'),"w")
	header.write('<?xml version="1.0" encoding="UTF-8" standalone="no"?><FontGenerator><Informations Vendor="IS2T" Version="0.8"/><FontCharacterProperties>')
	height = 0
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
	header.write('</FontCharacterProperties><FontProperties Baseline="'+str(int(height*2/3))+'" Filter="" Height="'+str(height)+'" Name="'+directory[2:]+'" Space="2" Style="p" Width="-1"><Identifier Value="34"/></FontProperties></FontGenerator>')
	header.close()
	make_archive(directory,'zip',directory)
	ejf = directory+'.ejf'
	if os.path.exists(ejf):
		os.remove(ejf)
	os.rename(directory+'.zip', ejf)
	shutil.rmtree(directory, ignore_errors=True)
	print("EJF file can be found at "+os.path.abspath(ejf))	
			
def main(fontFile, outputSizeInPx, startRange, endRange, bpp, algorithm):
	algo = "capitalHeight"
	if algorithm==1:
		algo = "emHeight"
	elif algorithm==2:
		algo = "bestFit"

	directory = os.path.splitext(fontFile)[0]+'_'+algo+'_'+str(outputSizeInPx)+'px'
	print("generating images")
	fileName = "gen.fontforge.py.tmp"
	genFontForgeFile(fileName)
	os.system("ffpython.exe "+fileName+' '+ fontFile+' '+ str(outputSizeInPx)+' '+ str(startRange)+' '+ str(endRange)+' '+ str(bpp) +' '+ directory+' '+str(algorithm))
	os.remove(fileName)
	resize(outputSizeInPx, directory)
	generateEJF(directory)

def checkparam():
	if len(sys.argv)<3 or len(sys.argv)==4:
		print("USAGE:\tfontConverter fontFilePath sizeInPixel [startRange endRange [bpp [algorithm]]")
		print("\t\tfontFilePath: the path to the font file")
		print("\t\tsizeInPixel: the size in pixel to use for the output (c.f. algorithm)")
		print("\t\tstartRange: (default = 0x21) hexadecimal value of the first unicode character to export")
		print("\t\tendRange: (default = 0x24F)  hexadecimal value of the last unicode character last character to export, it is recommanded to export a wide range")
		print("\t\tbpp: (default = 0x24F) the bpp to use for the export")
		print("\t\talgorithm: (default = 0) the algorithm to use : ")
		print("\t\t\t0: capitalHeight => The sizeInPixel define the size of a capital X (the generated image height zill be the bigest height required to print all the character in range)")
		print("\t\t\t1: emHeight => The sizeInPixel define the size of an em (the generated image height will be the bigest height required to print all the character in range)")
		print("\t\t\t2: bestFit => The sizeInPixel define the size of the final output (the size of the font used will be the biggest possible to fit all the characters within the sizeInPixel)")
		exit(-1)
	
checkparam()
fontFile = sys.argv[1]
outputSizeInPx = int(sys.argv[2])
startRange = 0x21
endRange = 0x24F
bpp = 8
algorithm = 0
if len(sys.argv)>3:
	startRange = int(sys.argv[3], 16)
	endRange = int(sys.argv[4], 16)
if len(sys.argv)>5:
	bpp = int(sys.argv[5])
if len(sys.argv)>6:
	algorithm = int(sys.argv[6])

main(fontFile, outputSizeInPx, startRange, endRange, bpp, algorithm)