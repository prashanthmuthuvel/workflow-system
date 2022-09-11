import cv2
import matplotlib.pyplot as plt
from skimage import measure, morphology
from skimage.measure import regionprops
import numpy as np
import pytesseract
from pdf2image import convert_from_path


# GLOBAL VARIABLES
IMAGE_NAME = 'image.jpg'

class SignatureRecognition:

    def __init__(self, pdf_file_path, text_to_be_found):
        self.image = self.getImage(pdf_file_path)
        # the parameters are used to remove small size connected pixels outliar
        self.constant_parameter_1 = 84
        self.constant_parameter_2 = 250
        self.constant_parameter_3 = 100
        # the parameter is used to remove big size connected pixels outliar
        self.constant_parameter_4 = 18
        # image position
        self.x = 0
        self.y = 0
        self.w = 0
        self.h = 0
        self.text_to_be_found = text_to_be_found

    def getImage(self, pdf_file_path):
        images = convert_from_path(pdf_file_path, 300)

        # convert pdf to image
        for image in images:
            image.save(IMAGE_NAME)

        image = cv2.imread(IMAGE_NAME, 0)
        return image

    def extractSignature(self):
        # read the input image
        # img = cv2.imread('0001.jpg', 0)
        img = cv2.threshold(self.image, 127, 255, cv2.THRESH_BINARY)[1]  # ensure binary

        # connected component analysis by scikit-learn framewnent analysis by scikit-learn framework
        blobs = img > img.mean()
        blobs_labels = measure.label(blobs, background=1)

        the_biggest_component = 0
        total_area = 0
        counter = 0
        average = 0.0
        for region in regionprops(blobs_labels):
            if (region.area > 10):
                total_area = total_area + region.area
                counter = counter + 1
            # print region.area # (for debugging)
            # take regions with large enough areas
            if (region.area >= 250):
                if (region.area > the_biggest_component):
                    the_biggest_component = region.area

        average = (total_area / counter)
        print("the_biggest_component: " + str(the_biggest_component))
        print("average: " + str(average))

        # experimental-based ratio calculation, modify it for your cases
        # a4_small_size_outliar_constant is used as a threshold value to remove connected outliar connected pixels
        # are smaller than a4_small_size_outliar_constant for A4 size scanned documents
        a4_small_size_outliar_constant = ((average / self.constant_parameter_1) * self.constant_parameter_2) + self.constant_parameter_3
        print("a4_small_size_outliar_constant: " + str(a4_small_size_outliar_constant))

        # experimental-based ratio calculation, modify it for your cases
        # a4_big_size_outliar_constant is used as a threshold value to remove outliar connected pixels
        # are bigger than a4_big_size_outliar_constant for A4 size scanned documents
        a4_big_size_outliar_constant = a4_small_size_outliar_constant * self.constant_parameter_4
        print("a4_big_size_outliar_constant: " + str(a4_big_size_outliar_constant))

        # remove the connected pixels are smaller than a4_small_size_outliar_constant
        pre_version = morphology.remove_small_objects(blobs_labels, a4_small_size_outliar_constant)
        # remove the connected pixels are bigger than threshold a4_big_size_outliar_constant
        # to get rid of undesired connected pixels such as table headers and etc.
        component_sizes = np.bincount(pre_version.ravel())
        too_small = component_sizes > (a4_big_size_outliar_constant)
        too_small_mask = too_small[pre_version]
        pre_version[too_small_mask] = 0
        # save the the pre-version which is the image is labelled with colors
        # as considering connected components
        plt.imsave('pre_version.png', pre_version)

        # read the pre-version
        img = cv2.imread('pre_version.png', 0)
        # ensure binary
        img = cv2.threshold(img, 0, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)[1]
        # save the the result
        cv2.imwrite("extracted_signature.png", img)
        self.extractedSignatureImage = img

    def findSignatureLocation(self):
        # For the utilization of Otsu threshold its needed to apply a gaussian blur
        # we use a big kernel to instensify the noising remove
        blur = cv2.GaussianBlur(self.extractedSignatureImage, (11, 11), 0)
        kernel = cv2.getStructuringElement(cv2.MORPH_CROSS, (5, 5))
        grad = cv2.morphologyEx(blur, cv2.MORPH_GRADIENT, kernel)

        # Applying Otsu binarization its very useful because
        # he uses the advantage of a gaussian blured image
        # and calculates an optimal threshold based on the variance and means
        # wich is useful since we have many different images
        # http://docs.opencv.org/trunk/d7/d4d/tutorial_py_thresholding.html
        _, thresh = cv2.threshold(grad, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
        # dilation
        # we dilate to remove some noise resulted of the transformation
        dilated = cv2.dilate(thresh, kernel, iterations=2)  # dilate

        kernel = cv2.getStructuringElement(cv2.MORPH_CROSS, (5, 5))
        connected = cv2.morphologyEx(thresh, cv2.MORPH_CLOSE, kernel)
        contours, _ = cv2.findContours(connected, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_NONE)
        self.contours = contours

    # apply the rectangle draw on the interesting regions
    def lookNearByText(self):
        for contour in self.contours:
            # get rectangle bounding contour
            [self.x, self.y, self.w, self.h] = cv2.boundingRect(contour)

            # being smart and draw only in commonly signature regions
            valid = self.extractedSignatureImage.shape[1] * 0.50
            # bootom of the figure
            # horizontal text
            if (self.w > self.h and self.y > valid and self.w > 20 and self.h > 20):
                # draw rectangle around the contour on the original image
                marked = cv2.rectangle(self.extractedSignatureImage, (self.x, self.y), (self.x + self.w, self.y + self.h), (0, 255, 0), 2)
                text_in_image = self.extractTextFromImage()
                for key in self.text_to_be_found:
                    if self.text_to_be_found[key] != 'Found':
                        if key in text_in_image:
                            self.text_to_be_found[key] = 'Found'
                            break

    def extractTextFromImage(self):
        test = cv2.imread(IMAGE_NAME)
        crop = test[self.y:self.y + self.h, 0:self.x + self.w]
        text_in_image = pytesseract.image_to_string(crop)
        return text_in_image

    def process(self):
        self.extractSignature()
        self.findSignatureLocation()
        self.lookNearByText()
        return self.text_to_be_found

if __name__ == "__main__":

    #Text to be found
    object = SignatureRecognition('save.pdf', {'Student signature':'Not Found', 'Professor signature': 'Not Found'})
    result = object.process()
    print(result)