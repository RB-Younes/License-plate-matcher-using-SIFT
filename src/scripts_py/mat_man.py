import datetime
import numpy as np
import cv2
import argparse
import os
from pytesseract import pytesseract
import pandas as pd


def parse_args():
    # DEFAULT PATHS TO CHANGE
    script_dir = os.path.dirname(__file__)  # <-- absolute dir the script is in
    parser = argparse.ArgumentParser(description="Datasets Processing.")
    parser.add_argument('--path_image', nargs='?', default=os.path.join(script_dir + '/DATASET TOP/0.jpg'),
                        help='Path of the image.')
    return parser.parse_args()


def mouseHandler(event, x, y, flags, param):
    global im_temp, pts_src

    if event == cv2.EVENT_LBUTTONDOWN:
        cv2.circle(im_temp, (x, y), 3, (0, 255, 255), 1, cv2.LINE_AA)
        cv2.imshow("Image", im_temp)
        if len(pts_src) < 4:
            pts_src = np.append(pts_src, [(x, y)], axis=0)


args = parse_args()
path_ = args.path_image
#https://www.codementor.io/@innat_2k14/extract-a-particular-object-from-images-using-opencv-in-python-jfogyig5u
# initialize the list of reference points and boolean indicating
# whether cropping is being performed or not
ref_point = []
cropping = False


def shape_selection(event, x, y, flags, param):
    # grab references to the global variables
    global ref_point, cropping

    # if the left mouse button was clicked, record the starting
    # (x, y) coordinates and indicate that cropping is being
    # performed
    if event == cv2.EVENT_LBUTTONDOWN:
        ref_point = [(x, y)]
        cropping = True

    # check to see if the left mouse button was released
    elif event == cv2.EVENT_LBUTTONUP:
        # record the ending (x, y) coordinates and indicate that
        # the cropping operation is finished
        ref_point.append((x, y))
        cropping = False

        # draw a rectangle around the region of interest
        cv2.rectangle(image, ref_point[0], ref_point[1], (0, 255, 0), 2)
        cv2.imshow("image", image)


# load the image, clone it, and setup the mouse callback function
image = cv2.imread(path_)
clone = image.copy()
cv2.namedWindow("image")
cv2.setMouseCallback("image", shape_selection)

# keep looping until the 'q' key is pressed
while True:
    # display the image and wait for a keypress
    cv2.imshow("image", image)
    key = cv2.waitKey(1)

    # if the 'r' key is pressed, reset the cropping region
    if key == ord("r"):
        image = clone.copy()

    # if the 'c' key is pressed, break from the loop
    elif key == ord("c"):
        break

# if there are two reference points, then crop the region of interest
# from teh image and display it
if len(ref_point) == 2:
    crop_img = clone[ref_point[0][1]:ref_point[1][1], ref_point[0][0]:ref_point[1][0]]
    cv2.imshow("crop_img", crop_img)
    script_dir = os.path.dirname(__file__)
    cv2.imwrite(script_dir + '/out.png', crop_img)
    cv2.waitKey(0)

# close all open windows
cv2.destroyAllWindows()

script_dir = os.path.dirname(__file__)
img_path = os.path.join(script_dir + '/out.png')
img = cv2.imread(img_path)

# folder path
dir_path = script_dir + '/DATASET MAT'
count = 0
# Iterate directory
for path in os.listdir(dir_path):
    # check if current path is a file
    if os.path.isfile(os.path.join(dir_path, path)):
        count += 1
cv2.imwrite(script_dir + '/DATASET MAT/mat' + str(count + 1) + '.png', img)

