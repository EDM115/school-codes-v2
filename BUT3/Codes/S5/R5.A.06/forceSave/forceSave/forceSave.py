from krita import *
from PyQt5.QtWidgets import QAction, QShortcut
from PyQt5.QtGui import QKeySequence

class ForceSaveExtension(Extension):
    def __init__(self, parent):
        super().__init__(parent)

    def setup(self):
        pass

    def force_save(self):
        currentDocument = Krita.instance().activeDocument()
        currentDocument.setBatchmode(True) # do not display export dialog box

        pngOptions=InfoObject()
        pngOptions.setProperty("compression", 5) # 0 (no compression) to 9 (max compression)
        pngOptions.setProperty("indexed", False)
        pngOptions.setProperty("interlaced", False)
        pngOptions.setProperty("saveSRGBProfile", False)
        pngOptions.setProperty("forceSRGB", True)
        pngOptions.setProperty("alpha", True)

        currentDocument.exportImage(currentDocument.fileName(), pngOptions)
    
    def createActions(self, window):
        force_save_action = QAction("Force Save", window.qwindow())
        force_save_action.setShortcut("Ctrl+Shift+Alt+S")
        force_save_action.triggered.connect(self.force_save)

        main_menu = window.qwindow().menuBar()
        custom_menu = main_menu.addMenu("EDM115")
        custom_menu.addAction(force_save_action)

        shortcut = QShortcut(QKeySequence("Ctrl+Shift+Alt+S"), window.qwindow())
        shortcut.activated.connect(self.force_save)

# Adapted from the source : https://krita-artists.org/t/script-save-to-png-directly-and-skip-the-png-image-dialog/80689
