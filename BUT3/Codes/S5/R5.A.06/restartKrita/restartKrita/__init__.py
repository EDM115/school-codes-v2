from .restartKrita import RestartKritaExtension

Krita.instance().addExtension(RestartKritaExtension(Krita.instance()))
