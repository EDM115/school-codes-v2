import cv2
import numpy as np
from krita import *
from PyQt5.QtWidgets import QShortcut
from PyQt5.QtGui import QKeySequence

class AutoLineColoringExtension(Extension):
    def __init__(self, parent):
        super(AutoLineColoringExtension, self).__init__(parent)

    def setup(self):
        pass

    def colorize_lines(self):
        # Access the active document
        doc = Krita.instance().activeDocument()
        if doc is None:
            Krita.instance().showMessage("No active document")
            return

        # Access the active layer
        current_layer = doc.activeNode()
        if current_layer is None:
            Krita.instance().showMessage("No active layer")
            return

        # Convert the layer to an OpenCV image
        width, height = doc.width(), doc.height()
        pixel_data = current_layer.pixelData(0, 0, width, height)
        img = np.frombuffer(pixel_data, np.uint8).reshape((height, width, 4))  # RGBA format

        # Convert to grayscale and detect edges
        gray = cv2.cvtColor(img, cv2.COLOR_RGBA2GRAY)
        edges = cv2.Canny(gray, 100, 200)

        # Create a mask and fill detected contours
        mask = np.zeros_like(edges)
        contours, _ = cv2.findContours(edges, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

        # Assign unique identifiers to each contour region
        contour_value = 10
        for contour in contours:
            cv2.drawContours(mask, [contour], -1, contour_value, thickness=-1)
            contour_value = (contour_value + 10) % 256  # Avoid overflow

        # Apply unique colors based on the mask
        colorized_img = img.copy()
        unique_regions = np.unique(mask)[1:]  # Exclude background
        for region_id in unique_regions:
            color = np.random.randint(0, 255, 3)  # Random color for each region
            colorized_img[mask == region_id] = list(color) + [255]  # Adding alpha

        # Convert the colorized image back to a Krita format
        colorized_img_data = colorized_img.tobytes()

        # Create a new layer for colorization
        new_layer = doc.createNode("Auto Color Layer", "paintLayer")
        doc.rootNode().addChildNode(new_layer, current_layer)  # Add above the current layer
        new_layer.setPixelData(colorized_img_data, 0, 0, width, height)
        
        doc.refreshProjection()  # Refresh to see changes

    def createActions(self, window):
        # Create an action with shortcut to trigger colorization
        action = window.createAction("auto_line_coloring", "Automatic Line Coloring", "tools/scripts")
        action.triggered.connect(self.colorize_lines)

        # Optional: Add a shortcut for this action
        shortcut = QShortcut(QKeySequence("Ctrl+Shift+Alt+C"), window.qwindow())
        shortcut.activated.connect(self.colorize_lines)
