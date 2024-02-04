import getpass
import glob

import cv2 as cv
import argparse
import os


def parse_args():
    # DEFAULT PATHS TO CHANGE
    script_dir = os.path.dirname(__file__)  # <-- absolute dir the script is in
    parser = argparse.ArgumentParser(description="Datasets Processing.")
    parser.add_argument('--path_image', nargs='?', default=os.path.join(script_dir + '/DATASET TOP/Cars73.png'),
                        help='Path of the image.')
    parser.add_argument('--distance', nargs='?', default="0.5",
                        help='Distance.')
    parser.add_argument('--nbr', nargs='?', default="8",
                        help='Minimum number of good matches.')
    return parser.parse_args()


def search_car(path_img, distance, nbr):
    sift = cv.SIFT_create()
    script_dir = os.path.dirname(__file__)  # <-- absolute dir the script is in

    imdir = script_dir + '/DATASET MAT/'
    ext = ['png', 'jpg', 'gif']  # Add image formats here

    files = []
    [files.extend(glob.glob(imdir + '*.' + e)) for e in ext]

    images = [cv.imread(file) for file in files]

    kp_trains = []
    img_query = cv.imread(path_img)
    gray_query = cv.cvtColor(img_query, cv.COLOR_BGR2GRAY)
    kp_query, des_query = sift.detectAndCompute(gray_query, None)
    goods = []
    if len(images) > 0:
        for img_train in images:
            gray_train = cv.cvtColor(img_train, cv.COLOR_BGR2GRAY)
            kp_train, des_train = sift.detectAndCompute(gray_train, None)
            kp_trains.append(kp_train)

            bf = cv.BFMatcher()
            matches = bf.knnMatch(des_query, des_train, k=2)
            good = []
            for m, n in matches:
                if m.distance < distance * n.distance:
                    good.append([m])
            goods.append(good)
        max = 0
        max_pos = 0
        for k in range(len(goods)):
            if max < len(goods[k]):
                max = len(goods[k])
                max_pos = k
        if len(goods[max_pos]) >= nbr:
            img_match = cv.drawMatchesKnn(img_query, kp_query, images[max_pos],
                                          kp_trains[max_pos], goods[max_pos],
                                          None, flags=cv.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS)

            cv.imshow('Knn', img_match)
            cv.waitKey(0)
            cv.destroyAllWindows()
            return 1
        else:
            return 0
    else:
        return -1


if __name__ == "__main__":

    u = getpass.getuser()
    if os.path.exists("C:/users/" + u + "/Desktop/output.txt"):
        os.remove("C:/users/" + u + "/Desktop/output.txt")

    args = parse_args()
    path_ = args.path_image
    d = float(args.distance)
    n = int(args.nbr)

    res = search_car(path_, d, n)

    if res == 1:
        u = getpass.getuser()
        print("VOITURE TROUVEE ! ", file=open('C:/users/' + u + '/Desktop/output.txt', "w"))
    elif res == 0:
        u = getpass.getuser()
        print("VOITURE NON TROUVEE ! ", file=open('C:/users/' + u + '/Desktop/output.txt', "w"))
    elif res == -1:
        u = getpass.getuser()
        print("PAS DE VOITURE BLACK LISTE! ", file=open('C:/users/' + u + '/Desktop/output.txt', "w"))
