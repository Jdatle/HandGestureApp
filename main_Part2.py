# -*- coding: utf-8 -*-
"""
Created on Thu Jan 28 00:44:25 2021

@author: chakati
"""

# John Le's CSE 535 Spring A 2022 Part II Individual Project

from cgi import test
from wsgiref.simple_server import sys_version
import cv2
import numpy as np
import os
import tensorflow as tf

# Additional Imports
import csv
from os import listdir
from os.path import isfile, join

from frameextractor import frameExtractor
from handshape_feature_extractor import HandShapeFeatureExtractor


## import the handfeature extractor class

# =============================================================================
# Get the penultimate layer for trainig data
# =============================================================================
# your code goes here
# Extract the middle frame of each gesture video

# Varibles to extract the middle frame of each gesture video
pathTrainData = "traindata/"
pathTrainImages = "trainimages/"
imageFileType = ".png"
trainList = [f for f in listdir(pathTrainData) if isfile(join(pathTrainData, f))]

# Extract Middle Frame
# for i in range(0,len(trainList),1):
#     videoName = pathTrainData + trainList[i]
#     imageName = pathTrainImages + trainList[i].split(".")[0] + imageFileType    
#     frameExtractor(videoName,pathTrainData,0)
#     os.rename(pathTrainData+"00001.png",imageName)

print("Tensor Flow Version: " + tf.__version__)
print("Python Version: " + sys_version)
print("cv2 Version: " + cv2.__version__)
print("numpy Version: " + np.version.version)

test_file = pathTrainImages + trainList[0].split(".")[0] + imageFileType
print(test_file)
testImage = cv2.imread(test_file,0)
HandShapeFeatureExtractor.__init__
test_handshape = HandShapeFeatureExtractor.extract_feature(HandShapeFeatureExtractor,testImage)
# test_handshape = HandShapeFeatureExtractor.extract_feature(HandShapeFeatureExtractor,testImage).flatten().tolist



# =============================================================================
# Get the penultimate layer for test data
# =============================================================================
# your code goes here 

# Varibles to extract the middle frame of each gesture video
pathTestData = "test/"
pathTestImages = "testimages/"
imageFileType = ".png"
testList = [f for f in listdir(pathTestData) if isfile(join(pathTestData, f))]

# Extract Middle Frame
# for i in range(0,len(testList),1):
#     videoName = pathTestData + testList[i]
#     imageName = pathTestImages + testList[i].split(".")[0] + imageFileType    
#     frameExtractor(videoName,pathTestData,0)
#     os.rename(pathTestData+"00001.png",imageName)



# =============================================================================
# Recognize the gesture (use cosine similarity for comparing the vectors)
# =============================================================================



# ***************************
# Generate Result.CSV File
# ***************************
outfile = open("results.csv","w")

for i in range(0,len(testList),1):
    preOutput = "Filename_OutputLabel: "
    predictLabel = "TBD"
    outfile.write(preOutput + testList[i] + " " + predictLabel + "\n")

outfile.close()
print("Finished")
