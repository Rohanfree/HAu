import cv2
import time
cam=cv2.VideoCapture(1)
frame=cam.read()[1]
#time.sleep(5)
"""for i in range(0,100):
    frame=cam.read()[1]
    time.sleep(.1)"""
cv2.imwrite(filename='img2.jpg',img=frame)
    
   # cv2.waitKey(0)


