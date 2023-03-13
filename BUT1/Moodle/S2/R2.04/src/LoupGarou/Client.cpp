#include <Arduino.h>
#include <WiFi.h>

#define LGFX_WT32_SC01 
#define LGFX_AUTODETECT // Autodetect board
#define LGFX_USE_V1     // set to use new version of library
#include <LovyanGFX.hpp> // main library
#include <lvgl.h>
#include "lv_conf.h"
LV_FONT_DECLARE (lib_sans_12);

#include "R204Map.h"
#include "SetupNet.h"
#include "Client.h"

static const int DELAY_TRY = 200;

//  =======================================
static char * labelsConnection [] = {
  "#ff0000 Déconnecté·e·s#",
  "#00ff00 Connecté·e·s#"
};

//  =======================================
static enum ClientState {
  ID_UNKNOWED,
  ID_WAIT,
  ID_SET,
  ROLE_DEFINED
} clientState (ID_UNKNOWED);

static String pseudo ("");

bool connected (false);

//  =======================================
static void
serverConnection (const String &methode, const String &req,
		  int &code, R204Map<String, String> &headers, String &rep) {
  code = 0;
  headers.clear ();
  rep = "";

  connected = false;
  for (int i (0); i < 3; ++i) {
    if (WiFi.status () == WL_CONNECTED) {
      connected = true;
      break;
    }
    WiFi.begin (getSSID ().c_str (), password);
    delay (DELAY_TRY);
  }
  WiFiClient client;
  if (!client.connect (webHost, webPort))
    return;
  client.println (methode+" /" + req + " HTTP/1.1\r\n"+
		  "Host: " + webHost + "\r\n" +
		  "Connection: close\r\n"+
		  "\r\n");
  client.flush ();
  String line, last;
  while (client.connected ()) {
    if (! client.available ())
      continue;
    char c = client.read ();
    if (c == '\r')
      // ignore MS
      continue;
    if (c != '\n') {
      // traiter ligne entière
      line += c;
      continue;
    }
    if (!line.length ())
      // fin des entêtes
      break;
    if (!code) {
      // première ligne = "HTTP/1.1 code text"
      int a (line.indexOf (' '));
      int b (line.indexOf (' ', a+1));
      if (a < 0 || b < 0)
	Serial.println ("\n   *** Bug HTTP");
      code = line.substring (a+1, b).toInt ();
    } else if (line[0] == ' ' || line[1]== '\t') {
      // valeur sur plusieurs lignes
      if (last.length ())
	last += line.substring (1);
      else
	Serial.println ("\n   *** Bug MIME +");
    } else {
      // variable d'entête
      int a (line.indexOf (':'));
      if (a < 1)
	Serial.println ("\n   *** Bug MIME :");
      String key (line.substring (0, a));
      key.toLowerCase ();
      headers [key] = line.substring (a+1);
    }
    // ligne suivante
    line = "";
  }
  // lire les données
  int contentLenght (headers ["content-length"].toInt ());
  char result [contentLenght+1];
  for (int i = 0; i < contentLenght; ++i)
    result [i] = client.read ();
  result [contentLenght] = '\0';
  rep = String (result);
  WiFi.mode (WIFI_OFF);
  Serial.println (String (code)+": "+rep);
}

//  =======================================
void sendPseudo (String pseudo) {
  int code;
  R204Map<String, String> headers;
  String rep;
  serverConnection ("GET", String ("pseudo?pseudo=")+pseudo, code, headers, rep);
  delay (3000);
}

//  =======================================
static void updateStatus () {
  static lv_obj_t *statusBar (NULL);
  String msg (getLocalMac ()+" "+getSSID ()+":"+labelsConnection [connected ? 1 : 0]+" >"+pseudo);
  if (!statusBar) {
    statusBar = lv_label_create (lv_scr_act ());
    lv_obj_set_style_text_font (statusBar, &lib_sans_12, 0);
    lv_label_set_recolor (statusBar, true);
    lv_label_set_text (statusBar, msg.c_str ());
    lv_obj_set_size (statusBar, 480, 20);
    lv_obj_align (statusBar, LV_ALIGN_TOP_MID, 0, 0);
    return;
  }
  if (lv_label_get_text (statusBar) != msg.c_str ())
    lv_label_set_text (statusBar, msg.c_str ());
}

//  =======================================
void
clientLoop () {
  switch (clientState) {
  case ID_UNKNOWED:
    // XXX affiche un clavier si necassaire pour saisir un pseudo
    // XXX la validation envoie le pseudo
    break;
  case ID_WAIT:
    // XXX si le pseudo est incorrecte revenir à la saisie
    break;
  case ID_SET:
    // XXX si le pseudo est bon attendre le rôle
    break;
  case ROLE_DEFINED:
    // XXX attendre les actions de vote
    break;
  }
  updateStatus ();
  sendPseudo ("toto");
}

//  =======================================
