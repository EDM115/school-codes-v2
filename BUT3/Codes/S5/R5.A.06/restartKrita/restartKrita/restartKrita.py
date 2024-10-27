import os
import sys
from krita import *
from PyQt5.QtWidgets import QApplication, QShortcut
from PyQt5.QtGui import QKeySequence

class RestartKritaExtension(Extension):
    def __init__(self, parent):
        super().__init__(parent)

    def setup(self):
        pass

    def restart_krita(self):
        krita_path = QApplication.applicationFilePath()
        args = [krita_path] + sys.argv[1:]
        krita = Krita.instance()

        for doc in krita.documents():
            if doc.modified():
                doc.save()

        krita.action("file_quit").trigger()
        os.execv(krita_path, args)
    
    def createActions(self, window):
        action = window.createAction("restart_krita", "Restart Krita\tCtrl+Shift+Alt+R", "file")
        action.triggered.connect(self.restart_krita)

        shortcut = QShortcut(QKeySequence("Ctrl+Shift+Alt+R"), window.qwindow())
        shortcut.activated.connect(self.restart_krita)
