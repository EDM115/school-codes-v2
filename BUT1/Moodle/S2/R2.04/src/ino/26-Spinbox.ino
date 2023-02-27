/*
  https://docs.lvgl.io/master/widgets/spinbox.html
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
  lv_example_spinbox_1 ();
}

void local_loop (void) {
}


static lv_obj_t *spinbox;

static void lv_spinbox_increment_event_cb (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  if (code == LV_EVENT_SHORT_CLICKED || code  == LV_EVENT_LONG_PRESSED_REPEAT) {
    lv_spinbox_increment (spinbox);
  }
}

static void lv_spinbox_decrement_event_cb (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  if (code == LV_EVENT_SHORT_CLICKED || code == LV_EVENT_LONG_PRESSED_REPEAT) {
    lv_spinbox_decrement (spinbox);
  }
}


void lv_example_spinbox_1 (void) {
  spinbox = lv_spinbox_create (lv_scr_act ());
  lv_spinbox_set_range (spinbox, -1000, 25000);
  lv_spinbox_set_digit_format (spinbox, 5, 2);
  lv_spinbox_step_prev (spinbox);
  lv_obj_set_width (spinbox, 100);
  lv_obj_center (spinbox);

  lv_coord_t h = lv_obj_get_height (spinbox);

  lv_obj_t *btn = lv_btn_create (lv_scr_act ());
  lv_obj_set_size (btn, h, h);
  lv_obj_align_to (btn, spinbox, LV_ALIGN_OUT_RIGHT_MID, 5, 0);
  lv_obj_set_style_bg_img_src (btn, LV_SYMBOL_PLUS, 0);
  lv_obj_add_event_cb (btn, lv_spinbox_increment_event_cb, LV_EVENT_ALL,  NULL);

  btn = lv_btn_create (lv_scr_act ());
  lv_obj_set_size (btn, h, h);
  lv_obj_align_to (btn, spinbox, LV_ALIGN_OUT_LEFT_MID, -5, 0);
  lv_obj_set_style_bg_img_src (btn, LV_SYMBOL_MINUS, 0);
  lv_obj_add_event_cb (btn, lv_spinbox_decrement_event_cb, LV_EVENT_ALL, NULL);
}
