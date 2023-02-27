/*
  https://docs.lvgl.io/master/widgets/keyboard.html
*/

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
  lv_example_keyboard_1 ();
  //lv_example_keyboard_2 ();
}

void local_loop (void) {
}


static void ta_event_cb (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *ta = lv_event_get_target (e);
  lv_obj_t *kb = (lv_obj_t *) lv_event_get_user_data (e);
  if (code == LV_EVENT_FOCUSED) {
    lv_keyboard_set_textarea (kb, ta);
    lv_obj_clear_flag (kb, LV_OBJ_FLAG_HIDDEN);
  }

  if (code == LV_EVENT_DEFOCUSED) {
    lv_keyboard_set_textarea (kb, NULL);
    lv_obj_add_flag (kb, LV_OBJ_FLAG_HIDDEN);
  }
}

void lv_example_keyboard_1 (void) {
  /*Create a keyboard to use it with an of the text areas*/
  lv_obj_t *kb = lv_keyboard_create (lv_scr_act ());

  /*Create a text area. The keyboard will write here*/
  lv_obj_t *ta;
  ta = lv_textarea_create (lv_scr_act ());
  lv_obj_align (ta, LV_ALIGN_TOP_LEFT, 10, 10);
  lv_obj_add_event_cb (ta, ta_event_cb, LV_EVENT_ALL, kb);
  lv_textarea_set_placeholder_text (ta, "Hello");
  lv_obj_set_size (ta, 140, 80);

  ta = lv_textarea_create (lv_scr_act ());
  lv_obj_align (ta, LV_ALIGN_TOP_RIGHT, -10, 10);
  lv_obj_add_event_cb (ta, ta_event_cb, LV_EVENT_ALL, kb);
  lv_obj_set_size (ta, 140, 80);

  lv_keyboard_set_textarea (kb, ta);
}

void lv_example_keyboard_2 (void) {
  /*Create an AZERTY keyboard map*/
  static const char *kb_map[] = {
    "A", "Z", "E", "R", "T", "Y", "U", "I", "O", "P", LV_SYMBOL_BACKSPACE, "\n",
    "Q", "S", "D", "F", "G", "J", "K", "L", "M",  LV_SYMBOL_NEW_LINE, "\n",
    "W", "X", "C", "V", "B", "N", ",", ".", ":", "!", "?", "\n",
    LV_SYMBOL_CLOSE, " ",  " ", " ", LV_SYMBOL_OK, NULL
  };

  /*Set the relative width of the buttons and other controls*/
  static const lv_btnmatrix_ctrl_t kb_ctrl[] = {
    4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6,
    4, 4, 4, 4, 4, 4, 4, 4, 4, 6,
    4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
    2, LV_BTNMATRIX_CTRL_HIDDEN | 2, 6, LV_BTNMATRIX_CTRL_HIDDEN | 2, 2
  };

  /*Create a keyboard and add the new map as USER_1 mode*/
  lv_obj_t *kb = lv_keyboard_create (lv_scr_act ());

  lv_keyboard_set_map (kb, LV_KEYBOARD_MODE_USER_1, kb_map, kb_ctrl);
  lv_keyboard_set_mode (kb, LV_KEYBOARD_MODE_USER_1);

  /*Create a text area. The keyboard will write here*/
  lv_obj_t *ta;
  ta = lv_textarea_create (lv_scr_act ());
  lv_obj_align (ta, LV_ALIGN_TOP_MID, 0, 10);
  lv_obj_set_size (ta, lv_pct (90), 80);
  lv_obj_add_state (ta, LV_STATE_FOCUSED);

  lv_keyboard_set_textarea (kb, ta);
}
