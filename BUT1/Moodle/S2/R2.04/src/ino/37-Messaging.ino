/*
  https://docs.lvgl.io/master/others/msg.html
*/

// error: 'LV_EVENT_MSG_RECEIVED' was not declared in this scope
// => in lv_conf.h set #define LV_USE_MSG 1

// ########################################
#define LGFX_WT32_SC01 
#define LGFX_AUTODETECT // Autodetect board
#define LGFX_USE_V1     // set to use new version of library
#include <LovyanGFX.hpp> // main library
static LGFX lcd; // declare display variable
#include <lvgl.h>
#include "lv_conf.h"

/*** Setup screen resolution for LVGL ***/
static const uint16_t screenWidth = 480;
static const uint16_t screenHeight = 320;
static lv_disp_draw_buf_t draw_buf;
static lv_color_t buf[screenWidth * 10];

/*** Function declaration ***/
void display_flush (lv_disp_drv_t *disp, const lv_area_t *area, lv_color_t *color_p);
void touchpad_read (lv_indev_drv_t *indev_driver, lv_indev_data_t *data);
void local_main ();
void local_loop ();

// ########################################
void setup (void) {
  lcd.init (); // Initialize LovyanGFX
  lv_init ();  // Initialize lvgl
  // Setting display to landscape
  if (lcd.width () < lcd.height ())
    lcd.setRotation (lcd.getRotation () ^ 1);

  /* LVGL : Setting up buffer to use for display */
  lv_disp_draw_buf_init (&draw_buf, buf, NULL, screenWidth * 10);

  /*** LVGL : Setup & Initialize the display device driver ***/
  static lv_disp_drv_t disp_drv;
  lv_disp_drv_init (&disp_drv);
  disp_drv.hor_res = screenWidth;
  disp_drv.ver_res = screenHeight;
  disp_drv.flush_cb = display_flush;
  disp_drv.draw_buf = &draw_buf;
  lv_disp_drv_register (&disp_drv);

  /*** LVGL : Setup & Initialize the input device driver ***/
  static lv_indev_drv_t indev_drv;
  lv_indev_drv_init (&indev_drv);
  indev_drv.type = LV_INDEV_TYPE_POINTER;
  indev_drv.read_cb = touchpad_read;
  lv_indev_drv_register (&indev_drv);

  local_main ();
}

void loop () {
  lv_timer_handler (); /* let the GUI do its work */
  delay (5);
  local_loop ();
}


/*** Display callback to flush the buffer to screen ***/
void display_flush (lv_disp_drv_t *disp, const lv_area_t *area, lv_color_t *color_p) {
  uint32_t w = (area->x2 - area->x1 + 1);
  uint32_t h = (area->y2 - area->y1 + 1);

  lcd.startWrite ();
  lcd.setAddrWindow (area->x1, area->y1, w, h);
  lcd.pushColors ( (uint16_t *) &color_p->full, w * h, true);
  lcd.endWrite ();

  lv_disp_flush_ready (disp);
}

/*** Touchpad callback to read the touchpad ***/
void touchpad_read (lv_indev_drv_t *indev_driver, lv_indev_data_t *data) {
  uint16_t touchX, touchY;
  bool touched = lcd.getTouch (&touchX, &touchY);

  if (!touched)
    data->state = LV_INDEV_STATE_REL;
  else {
    data->state = LV_INDEV_STATE_PR;

    /*Set the coordinates*/
    data->point.x = touchX;
    data->point.y = touchY;
  }
}

// ########################################
// Demo
void local_main (void) {
  lv_example_msg_1 ();
  // lv_example_msg_2 ();
  // lv_example_msg_3 ();
}

void local_loop (void) {
}


/*Define a message ID*/
#define MSG_NEW_TEMPERATURE     1

static void slider_event_cb1 (lv_event_t *e);
static void label_event_cb1 (lv_event_t *e);

/**
 * A slider sends a message on value change and a label display's that value
 */
void lv_example_msg_1 (void) {
  /*Create a slider in the center of the display*/
  lv_obj_t *slider = lv_slider_create (lv_scr_act ());
  lv_obj_center (slider);
  lv_obj_add_event_cb (slider, slider_event_cb1, LV_EVENT_VALUE_CHANGED, NULL);

  /*Create a label below the slider*/
  lv_obj_t *label = lv_label_create (lv_scr_act ());
  lv_obj_add_event_cb (label, label_event_cb1, LV_EVENT_MSG_RECEIVED, NULL);
  lv_label_set_text (label, "0%");
  lv_obj_align (label, LV_ALIGN_CENTER, 0, 30);

  /*Subscribe the label to a message. Also use the user_data to set a format string here.*/
  lv_msg_subscribe_obj (MSG_NEW_TEMPERATURE, label, (void *) "%d °C");
}

static void slider_event_cb1 (lv_event_t *e) {
  /*Notify all subscribers (only the label now) that the slider value has been changed*/
  lv_obj_t *slider = lv_event_get_target (e);
  int32_t v = lv_slider_get_value (slider);
  lv_msg_send (MSG_NEW_TEMPERATURE, &v);
}

static void label_event_cb1 (lv_event_t *e) {
  lv_obj_t *label = lv_event_get_target (e);
  lv_msg_t *m = lv_event_get_msg (e);

  const char *fmt = (const char *) lv_msg_get_user_data (m);
  const int32_t *v = (const int32_t *) lv_msg_get_payload (m);

  lv_label_set_text_fmt (label, fmt, *v);
}

/*Define a message ID*/
#define MSG_LOGIN_ATTEMPT   1
#define MSG_LOG_OUT         2
#define MSG_LOGIN_ERROR     3
#define MSG_LOGIN_OK        4

static void auth_manager (lv_msg_t *m);
static void textarea_event_cb (lv_event_t *e);
static void log_out_event_cb (lv_event_t *e);
static void start_engine_msg_event_cb (lv_event_t *e);
static void info_label_msg_event_cb (lv_event_t *e);

/**
 * Simple PIN login screen.
 * No global variables are used, all state changes are communicated via messages.
 */
void lv_example_msg_2 (void) {
  lv_msg_subscribe (MSG_LOGIN_ATTEMPT, (lv_msg_subscribe_cb_t) auth_manager, (void *) "hello");

  /*Create a slider in the center of the display*/
  lv_obj_t *ta = lv_textarea_create (lv_scr_act ());
  lv_obj_set_pos (ta, 10, 10);
  lv_obj_set_width (ta, 200);
  lv_textarea_set_one_line (ta, true);
  lv_textarea_set_password_mode (ta, true);
  lv_textarea_set_placeholder_text (ta, "The password is: hello");
  lv_obj_add_event_cb (ta, textarea_event_cb, LV_EVENT_ALL, NULL);
  lv_msg_subscribe_obj (MSG_LOGIN_ERROR, ta, NULL);
  lv_msg_subscribe_obj (MSG_LOGIN_OK, ta, NULL);
  lv_msg_subscribe_obj (MSG_LOG_OUT, ta, NULL);

  lv_obj_t *kb = lv_keyboard_create (lv_scr_act ());
  lv_keyboard_set_textarea (kb, ta);

  lv_obj_t *btn;
  lv_obj_t *label;

  /*Create a log out button which will be active only when logged in*/
  btn = lv_btn_create (lv_scr_act ());
  lv_obj_set_pos (btn, 240, 10);
  lv_obj_add_event_cb (btn, log_out_event_cb, LV_EVENT_ALL, NULL);
  lv_msg_subscribe_obj (MSG_LOGIN_OK, btn, NULL);
  lv_msg_subscribe_obj (MSG_LOG_OUT, btn, NULL);

  label = lv_label_create (btn);
  lv_label_set_text (label, "LOG OUT");

  /*Create a label to show info*/
  label = lv_label_create (lv_scr_act ());
  lv_label_set_text (label, "");
  lv_obj_add_event_cb (label, info_label_msg_event_cb, LV_EVENT_MSG_RECEIVED, NULL);
  lv_obj_set_pos (label, 10, 60);
  lv_msg_subscribe_obj (MSG_LOGIN_ERROR, label, NULL);
  lv_msg_subscribe_obj (MSG_LOGIN_OK, label, NULL);
  lv_msg_subscribe_obj (MSG_LOG_OUT, label, NULL);

  /*Create button which will be active only when logged in*/
  btn = lv_btn_create (lv_scr_act ());
  lv_obj_set_pos (btn, 10, 80);
  lv_obj_add_event_cb (btn, start_engine_msg_event_cb, LV_EVENT_MSG_RECEIVED, NULL);
  lv_obj_add_flag (btn, LV_OBJ_FLAG_CHECKABLE);
  lv_msg_subscribe_obj (MSG_LOGIN_OK, btn, NULL);
  lv_msg_subscribe_obj (MSG_LOG_OUT, btn, NULL);

  label = lv_label_create (btn);
  lv_label_set_text (label, "START ENGINE");

  lv_msg_send (MSG_LOG_OUT, NULL);
}

static void auth_manager (lv_msg_t *m) {
  const char *pin_act = (const char *) lv_msg_get_payload (m);
  const char *pin_expexted = (const char *) lv_msg_get_user_data (m);
  if (strcmp (pin_act, pin_expexted) == 0)
    lv_msg_send (MSG_LOGIN_OK, NULL);
  else
    lv_msg_send (MSG_LOGIN_ERROR, "Incorrect PIN");
}

static void textarea_event_cb (lv_event_t *e) {
  lv_obj_t *ta = lv_event_get_target (e);
  lv_event_code_t code = lv_event_get_code (e);
  if (code == LV_EVENT_READY) {
    lv_msg_send (MSG_LOGIN_ATTEMPT, lv_textarea_get_text (ta));
  }
  else if (code == LV_EVENT_MSG_RECEIVED) {
    lv_msg_t *m = lv_event_get_msg (e);
    switch (lv_msg_get_id (m)) {
    case MSG_LOGIN_ERROR:
      /*If there was an error, clean the text area*/
      if (strlen ((const char *) lv_msg_get_payload (m)))
	lv_textarea_set_text (ta, "");
      break;
    case MSG_LOGIN_OK:
      lv_obj_add_state (ta, LV_STATE_DISABLED);
      lv_obj_clear_state (ta, LV_STATE_FOCUSED | LV_STATE_FOCUS_KEY);
      break;
    case MSG_LOG_OUT:
      lv_textarea_set_text (ta, "");
      lv_obj_clear_state (ta, LV_STATE_DISABLED);
      break;
    }
  }
}

static void log_out_event_cb (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  if (code == LV_EVENT_CLICKED) {
    lv_msg_send (MSG_LOG_OUT, NULL);
  }
  else if (code == LV_EVENT_MSG_RECEIVED) {
    lv_msg_t *m = lv_event_get_msg (e);
    lv_obj_t *btn = lv_event_get_target (e);
    switch (lv_msg_get_id (m)) {
    case MSG_LOGIN_OK:
      lv_obj_clear_state (btn, LV_STATE_DISABLED);
      break;
    case MSG_LOG_OUT:
      lv_obj_add_state (btn, LV_STATE_DISABLED);
      break;
    }
  }
}

static void start_engine_msg_event_cb (lv_event_t *e) {
  lv_msg_t *m = lv_event_get_msg (e);
  lv_obj_t *btn = lv_event_get_target (e);
  switch (lv_msg_get_id (m)) {
  case MSG_LOGIN_OK:
    lv_obj_clear_state (btn, LV_STATE_DISABLED);
    break;
  case MSG_LOG_OUT:
    lv_obj_add_state (btn, LV_STATE_DISABLED);
    break;
  }
}

static void info_label_msg_event_cb (lv_event_t *e) {
  lv_obj_t *label = lv_event_get_target (e);
  lv_msg_t *m = lv_event_get_msg (e);
  switch (lv_msg_get_id (m)) {
  case MSG_LOGIN_ERROR:
    lv_label_set_text (label, (const char*) lv_msg_get_payload (m));
    lv_obj_set_style_text_color (label, lv_palette_main (LV_PALETTE_RED), 0);
    break;
  case MSG_LOGIN_OK:
    lv_label_set_text (label, "Login successful");
    lv_obj_set_style_text_color (label, lv_palette_main (LV_PALETTE_GREEN), 0);
    break;
  case MSG_LOG_OUT:
    lv_label_set_text (label, "Logged out");
    lv_obj_set_style_text_color (label, lv_palette_main (LV_PALETTE_GREY), 0);
    break;
  default:
    break;
  }
}

static int32_t limit_value (int32_t v);
static void btn_event_cb (lv_event_t *e);
static void label_event_cb2 (lv_event_t *e);
static void slider_event_cb2 (lv_event_t *e);

static int32_t power_value;

typedef lv_uintptr_t lv_msg_id_t;

void lv_msg_update_value (int32_t *v) {
  // TODO
}

/**
 * Show how an increment button, a decrement button, as slider can set a value
 * and a label display it.
 * The current value (i.e. the system's state) is stored only in one static variable in a function
 * and no global variables are required.
 */
void lv_example_msg_3 (void) {

  lv_obj_t *panel = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (panel, 250, LV_SIZE_CONTENT);
  lv_obj_center (panel);
  lv_obj_set_flex_flow (panel, LV_FLEX_FLOW_ROW);
  lv_obj_set_flex_align (panel, LV_FLEX_ALIGN_SPACE_BETWEEN, LV_FLEX_ALIGN_CENTER, LV_FLEX_ALIGN_START);

  lv_obj_t *btn;
  lv_obj_t *label;

  /*Up button*/
  btn = lv_btn_create (panel);
  lv_obj_set_flex_grow (btn, 1);
  lv_obj_add_event_cb (btn, btn_event_cb, LV_EVENT_ALL, NULL);
  label = lv_label_create (btn);
  lv_label_set_text (label, LV_SYMBOL_LEFT);
  lv_obj_center (label);

  /*Current value*/
  label = lv_label_create (panel);
  lv_obj_set_flex_grow (label, 2);
  lv_obj_set_style_text_align (label, LV_TEXT_ALIGN_CENTER, 0);
  lv_label_set_text (label, "?");
  lv_msg_subscribe_obj ( (lv_msg_id_t) &power_value, label, NULL);
  lv_obj_add_event_cb (label, label_event_cb2, LV_EVENT_MSG_RECEIVED, NULL);

  /*Down button*/
  btn = lv_btn_create (panel);
  lv_obj_set_flex_grow (btn, 1);
  lv_obj_add_event_cb (btn, btn_event_cb, LV_EVENT_ALL, NULL);
  label = lv_label_create (btn);
  lv_label_set_text (label, LV_SYMBOL_RIGHT);
  lv_obj_center (label);

  /*Slider*/
  lv_obj_t *slider = lv_slider_create (panel);
  lv_obj_set_flex_grow (slider, 1);
  lv_obj_add_flag (slider, LV_OBJ_FLAG_FLEX_IN_NEW_TRACK);
  lv_msg_subscribe_obj ( (lv_msg_id_t)&power_value, slider, NULL);
  lv_obj_add_event_cb (slider, slider_event_cb2, LV_EVENT_ALL, NULL);

  power_value = 30;
  lv_msg_update_value (&power_value);
}

static int32_t limit_value (int32_t v) {
  return LV_CLAMP (30, v, 80);
}


static void btn_event_cb (lv_event_t *e) {
  lv_obj_t *btn = lv_event_get_target (e);
  lv_event_code_t code = lv_event_get_code (e);
  if (code == LV_EVENT_CLICKED || code == LV_EVENT_LONG_PRESSED_REPEAT) {
    if (lv_obj_get_index (btn) == 0) {    /*First object is the dec. button*/
      power_value = limit_value (power_value - 1);
      lv_msg_update_value (&power_value);
    }
    else {
      power_value = limit_value (power_value + 1);
      lv_msg_update_value (&power_value);
    }
  }
}

static void label_event_cb2 (lv_event_t *e) {
  lv_obj_t *label = lv_event_get_target (e);
  lv_event_code_t code = lv_event_get_code (e);
  if (code == LV_EVENT_MSG_RECEIVED) {
    lv_msg_t *m = lv_event_get_msg (e);
    const int32_t *v = (const int32_t *) lv_msg_get_payload (m);
    lv_label_set_text_fmt (label, "%"LV_PRId32" %%", *v);
  }
}

static void slider_event_cb2 (lv_event_t *e) {
  lv_obj_t *slider = lv_event_get_target (e);
  lv_event_code_t code = lv_event_get_code (e);
  if (code == LV_EVENT_VALUE_CHANGED) {
    power_value = limit_value (lv_slider_get_value (slider));
    lv_msg_update_value (&power_value);
  }
  else if (code == LV_EVENT_MSG_RECEIVED) {
    lv_msg_t *m = lv_event_get_msg (e);
    const int32_t *v = (const int32_t *) lv_msg_get_payload (m);
    lv_slider_set_value (slider, *v, LV_ANIM_OFF);
  }
}
