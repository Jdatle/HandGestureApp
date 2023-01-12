# HandGestureApp
Android Hand Gesture Prediction App (Individual Class Project)

This my first android app. It was an individual class project for Mobile Computing at ASU.

PART 1 DEMO VIDEO: https://youtu.be/RBehzwY9Ads

.

INCLUDED FILES

>PART 1:

>>MainActivity.java 			        (Screen 1)

>>MainActivity2.java			        (Screen 2)

>>MainActivity3.java			        (Screen 3)

>>main_Part1.py				            (Python app for Local Server)

>>Screen_1.jpg				            (Screenshot of Screen 1)

>>Screen_2.jpg				            (Screenshot of Screen 2)

>>Screen_3_Pre_Record.jpg			    (Screenshot of Screen 3)

>>Screen_3_Post_Record.jpg		    (Screenshot of Screen 3)

>>https://youtu.be/RBehzwY9Ads		(Part 1 demo video)

>PART 2:

>>main_Part2.py				            (Python app for Local Server)

>>gestures_trained_cnn_model.h5		(Pre-trained CNN model)

>>handshape_feature_extractor.py	(Support file to extract features)

>>frameextractor.py			          (Support file to extract middle frame)

.

SUMMARY OF REQUIREMENTS

-Develop android mobile that teaches and records hand gestures.

-Hand gestures are uploaded to local server

-Develop python app that classifies specific gestures

-Train & test CNN model on uploaded videos and predict gesture

.

PART 1: (FRONT END)

>Required Functions:

>>A. User shown a video of a gesture

>>B. User can replay video atleast 3 times

>>C. Clicking "PRACTICE" button allows user to record front camera for 5s max

>>D. Videos are upload to a server

>Required 3 screens:

>>Screen 1: 

>>>Drop-down menu of 17 gestures 

>>>Once single gesture selected user will be taken to screen 2.

>>Screen 2: 

>>>Video of gesture is shown

>>>Once "PRACTICE" button is selected user will be taken to screen 3.

>>Screen 3: 

>>>Front camera interface opens, allows user to record themselves performing the gesture.

>>>Once "UPLOAD" button is selected, video will be uploaded to local server. (Flask Server)

.

PART 2: (BACK END)

>Task 1: Extract middle frame of training videos & extract feature vectors from middle frame.

>Task 2: Extract middle frame of uploaded (test) videos & extract feature vectors from middle frame.

>Task 3: Apply cosine similarity and save results
