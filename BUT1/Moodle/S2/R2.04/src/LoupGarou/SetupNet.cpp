#include <Arduino.h>
#include <WiFi.h>

#include "SetupNet.h"

#define LGFX_WT32_SC01 
#define LGFX_AUTODETECT
#define LGFX_USE_V1
#include <LovyanGFX.hpp>
#include <lvgl.h>
#include "lv_conf.h"

//  =======================================
static char *ssidNames [] = {
  "R204-LG 01",
  "R204-LG 02",
  "R204-LG 03",
  "R204-LG 04",
  "R204-LG 05",
  "R204-LG 06",
  "R204-LG 07",
  "R204-LG 08",
  "R204-LG 09",
  "R204-LG 10",
  "R204-LG 11",
  "R204-LG 12",
  nullptr
};

static char *labelsMode [] = {
  "Client",
  "Server"
};

//  =======================================
const char *password ("MotDePass");
const char *webHost ("192.168.4.1");
const int webPort (80);

static bool netSet (false);
static bool serverMode (false);
static String ssid;
static String localMac;

bool
isNetSet () {
  return netSet;
}

bool
isServer () {
  return serverMode;
}

String
getSSID () {
  return ssid;
}

String getLocalMac () {
  return localMac;
}

//  =======================================
String mac2string (const byte mac [6]) {
  String result;
  for (int i (0); ; ++i) {
    char tmp [3];
    sprintf (tmp, "%02X", mac[i]);
    result += String (tmp);
    if (i == 5)
      break;
    result += ":";
  }
  result.toUpperCase ();
  return result;
}

String getMac () {
  byte mac[6];
  WiFi.macAddress (mac);
  return mac2string (mac);
}

void updateMac () {
  localMac = getMac ();
}

//  =======================================
static lv_style_t styleRadio;
static lv_style_t styleRadioChk;
static uint32_t selectedMode (serverMode);

static void callBackSetMode (lv_event_t *e) {
  uint32_t *active_id = (uint32_t *) lv_event_get_user_data (e);
  lv_obj_t *cont = lv_event_get_current_target (e);
  lv_obj_t *act_cb = lv_event_get_target (e);
  lv_obj_t *old_cb = lv_obj_get_child (cont, *active_id);

  if (act_cb == cont)
    return;
  serverMode = act_cb == lv_obj_get_child (cont, 1);
  lv_obj_clear_state (old_cb, LV_STATE_CHECKED);
  lv_obj_add_state (act_cb, LV_STATE_CHECKED);
  *active_id = lv_obj_get_index (act_cb);
}

static void radiobutton_create (lv_obj_t *parent, const char *txt) {
  lv_obj_t *obj = lv_checkbox_create (parent);
  lv_checkbox_set_text (obj, txt);
  lv_obj_add_flag (obj, LV_OBJ_FLAG_EVENT_BUBBLE);
  lv_obj_add_style (obj, &styleRadio, LV_PART_INDICATOR);
  lv_obj_add_style (obj, &styleRadioChk, LV_PART_INDICATOR | LV_STATE_CHECKED);
}


//  =======================================
static lv_obj_t *currentButton = NULL;
void callBackSetSSID (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  if (code != LV_EVENT_CLICKED)
    return;
  lv_obj_t *btn = lv_event_get_target (e);
  currentButton = currentButton == btn ? NULL : btn;
  lv_obj_t *label = lv_obj_get_child (btn, NULL);
  ssid = lv_label_get_text (label);
  lv_obj_t *parent = lv_obj_get_parent (btn);
  for (uint32_t i (0); i < lv_obj_get_child_cnt (parent); ++i) {
    lv_obj_t *child = lv_obj_get_child (parent, i);
    if (child == currentButton)
      lv_obj_add_state (child, LV_STATE_CHECKED);
    else
      lv_obj_clear_state (child, LV_STATE_CHECKED);
  }
}

//  =======================================
void callBackValidateNet (lv_event_t *e) {
  if (ssid.isEmpty ())
    return;
  displayFormNet (false);
  netSet = true;
}

//  =======================================
static lv_obj_t *formNet (nullptr);

void displayFormNet (bool show) {
  if (!show) {
    if (formNet)
      lv_obj_del (formNet);
    formNet = nullptr;
    return;
  }
  lv_style_init (&styleRadio);
  lv_style_set_radius (&styleRadio, LV_RADIUS_CIRCLE);
  lv_style_init (&styleRadioChk);
  lv_style_set_bg_img_src (&styleRadioChk, NULL);

  formNet = lv_obj_create (lv_scr_act ());

  lv_obj_t *listMode (lv_obj_create (formNet));
  lv_obj_set_flex_flow (listMode, LV_FLEX_FLOW_COLUMN);
  lv_obj_add_event_cb (listMode, callBackSetMode, LV_EVENT_CLICKED, &selectedMode);
  for (int i (0); i < 2; ++i)
    radiobutton_create (listMode, labelsMode [i]);
  lv_obj_add_state (lv_obj_get_child (listMode, selectedMode), LV_STATE_CHECKED);
  lv_obj_set_size (listMode, 150, 90);
  lv_obj_align (listMode, LV_ALIGN_TOP_LEFT, 0, 0);

  lv_obj_t *listSSID (lv_list_create (formNet));
  for (int i (0); ssidNames [i]; ++i) {
    lv_obj_t *btn = lv_btn_create (listSSID);
    lv_obj_add_event_cb (btn, callBackSetSSID, LV_EVENT_CLICKED, NULL);
    lv_obj_t *lab = lv_label_create (btn);
    lv_label_set_text_fmt (lab, ssidNames [i]);
  }
  lv_obj_set_size (listSSID, 150, 150);
  lv_obj_align (listSSID, LV_ALIGN_TOP_RIGHT, 0, 0);

  lv_obj_t *ok = lv_btn_create (formNet);
  lv_obj_t *label = lv_label_create (ok);
  lv_label_set_text (label, "OK");
  lv_obj_add_event_cb (ok, callBackValidateNet, LV_EVENT_CLICKED, NULL);
  lv_obj_align (ok, LV_ALIGN_BOTTOM_LEFT, 0, 0);

  lv_obj_set_size (formNet, 350, 200);
  lv_obj_center (formNet);
}

//  =======================================
