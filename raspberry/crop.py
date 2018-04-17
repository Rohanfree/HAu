import cv2
img = cv2.imread("img2.jpg")
crop_img = img[205:283, 174:469]
cv2.imshow("cropped", crop_img)
cv2.imwrite("img1.png",crop_img)
cv2.waitKey(0)
