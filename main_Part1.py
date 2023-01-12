from inspect import _void
from itertools import count
from xml.etree.ElementTree import tostring
from flask import Flask
import os

from flask import request
app = Flask(__name__)
 

def saveFile(gesture,number,file):
    counter = number
    path = 'static/uploads/'
    fileName = path + gesture + "_PRACTICE_" + str(counter) + "_Le.mp4"
    # print("Try " +fileName)
    if os.path.isfile(fileName):
        # print("File Exist")
        counter = counter + 1
        saveFile(gesture,counter,file)
    else:
        # print("Upload: "+fileName)
        file.save(os.path.join(fileName))
        return 


@app.route("/")
def showHomePage():
    return "Videos From John's Smart Home App Upload Here"
 
@app.route("/", methods=["POST"])
def upload():
    text = request.form["actionID"]
    file = request.files['file']
    saveFile(text,1,file)

    return "uploaded"
   
if __name__ == "__main__":
  app.run(host="0.0.0.0")
