#include <Arduino.h>
#include <WiFi.h>
#include <WebServer.h>
#include <esp_wifi.h>

#define LGFX_WT32_SC01 
#define LGFX_AUTODETECT		// Autodetect board
#define LGFX_USE_V1		// set to use new version of library
#include <LovyanGFX.hpp>	// main library
#include <lvgl.h>
#include "lv_conf.h"
LV_FONT_DECLARE (lib_sans_12);

#include "R204Map.h"
#include "SetupNet.h"
#include "Server.h"

static const int DELAY_SERVER_FLUSH = 50;

//  =======================================
static WebServer webServer (80);
static bool started (false);

String getClientMac () {
  uint32_t ipClient = (uint32_t) webServer.client ().remoteIP ();
  wifi_sta_list_t stationList;
  esp_wifi_ap_get_sta_list (&stationList);
  esp_netif_sta_list_t macIpStationList;
  esp_netif_get_sta_list (&stationList, &macIpStationList);
  for (int i (0); i < macIpStationList.num; ++i)
    if (ipClient == macIpStationList.sta [i].ip.addr)
      return mac2string (macIpStationList.sta [i].mac);
  return "";
}

//  =======================================
R204Map<String, String> allMacPseudo;

void handlePseudo () {
  String pseudo = webServer.arg ("pseudo");
  String clientMac (getClientMac ());
  Serial.println (clientMac+": set pseudo "+pseudo);
  if (!pseudo.length ()) {
    webServer.send (204, "text/plain", "Pseudo is empty");
    Serial.println ("    Pseudo is empty");
  } else if (allMacPseudo[clientMac] != pseudo && allMacPseudo.valueExists (pseudo)) {
    webServer.send (409, "text/plain", "Pseudo exists");
    Serial.println ("    Pseudo exists");
  } else {
    if (allMacPseudo[clientMac] != pseudo)
      allMacPseudo[clientMac] = pseudo;
    webServer.send (200, "plain/html", String ()+"You are "+pseudo);
  }
  WiFiClient client (webServer.client ());
  client.flush ();
  delay (DELAY_SERVER_FLUSH);
  client.stop ();
}

//  =======================================
void handleNotYetImplemented () {
  String clientMac (getClientMac ());
  webServer.send (200, "plain/html", String ()+"Not yet implemented");
  WiFiClient client (webServer.client ());
  client.flush ();
  delay (DELAY_SERVER_FLUSH);
  client.stop ();
}

//  =======================================
void startServer () {
  if (started || !isNetSet ())
    return;
  Serial.println ("Start server...");
  webServer.stop ();
  WiFi.softAP (getSSID ().c_str (), password);
  delay (3000);
  webServer.on ("/admin", handleNotYetImplemented);
  webServer.on ("/pseudo", handlePseudo);
  webServer.on ("/players", handleNotYetImplemented);
  webServer.on ("/state", handleNotYetImplemented);
  webServer.on ("/role", handleNotYetImplemented);
  webServer.on ("/poll", handleNotYetImplemented);
  webServer.onNotFound ([] () {
    webServer.send_P (404, "text/plain", "Not Found");
    delay (DELAY_SERVER_FLUSH);
    webServer.client ().stop ();
  });
  webServer.begin ();
  Serial.print ("Server started on ");
  Serial.println (WiFi.softAPIP ());
  started = true;
}

//  =======================================
void handleRequest () {
  if (!started)
    return;
  webServer.handleClient ();
  delay (100);
}

//  =======================================
static void updateStatus () {
  static lv_obj_t *statusBar (NULL);
  String msg (getLocalMac ()+" Server started on "+getSSID ()+" ("+
	      String (WiFi.softAPgetStationNum ())+")");
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
serverLoop () {
  startServer ();
  updateStatus ();
  handleRequest ();
}

//  =======================================
