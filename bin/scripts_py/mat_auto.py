import datetime
import numpy as np
import cv2
import imutils
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


def extract_mat(path_img):
    # https://morioh.com/p/c7e711123e91
    script_dir = os.path.dirname(__file__)
    img = cv2.imread(path_img)
    img = imutils.resize(img, width=500)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # convert to grey scale
    gray = cv2.bilateralFilter(gray, 11, 17, 17)  # Blur to reduce noise
    edged = cv2.Canny(gray, 30, 200)  # Perform Edge detection
    cnts, new = cv2.findContours(edged.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
    cnts = sorted(cnts, key=cv2.contourArea, reverse=True)[:30]
    screenCnt = None  # will store the number plate contour

    count = 0
    idx = 7
    # loop over contours
    for c in cnts:
        # approximate the contour
        peri = cv2.arcLength(c, True)
        approx = cv2.approxPolyDP(c, 0.018 * peri, True)
        if len(approx) == 4:  # chooses contours with 4 corners
            screenCnt = approx
            x, y, w, h = cv2.boundingRect(c)  # finds co-ordinates of the plate
            new_img = img[y:y + h, x:x + w]
            cv2.imwrite(script_dir + '/result.png', new_img)  # stores the new image
            idx += 1
            break
        # draws the selected contour on original image
    cv2.drawContours(img, [screenCnt], -1, (0, 255, 0), 3)
    cv2.imshow("Final image with plate detected", img)

    Cropped_loc = script_dir + '/result.png'  # the filename of cropped image
    cv2.imshow("cropped", cv2.imread(Cropped_loc))
    cv2.waitKey(0)
    cv2.destroyAllWindows()


def save_mat():
    script_dir = os.path.dirname(__file__)
    img_path = os.path.join(script_dir + '/result.png')
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


if __name__ == "__main__":
    args = parse_args()
    path_ = args.path_image
    extract_mat(path_)
    save_mat()
