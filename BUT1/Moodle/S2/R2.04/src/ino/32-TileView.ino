/*
  https://docs.lvgl.io/master/widgets/tileview.html
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
  lv_example_tileview_1 ();
}

void local_loop (void) {
}


/**
 * Create a 2x2 tile view and allow scrolling only in an "L" shape.
 * Demonstrate scroll chaining with a long list that
 * scrolls the tile view when it can't be scrolled further.
 */
void lv_example_tileview_1 (void) {
  lv_obj_t *tv = lv_tileview_create (lv_scr_act ());

  /*Tile1: just a label*/
  lv_obj_t *tile1 = lv_tileview_add_tile (tv, 0, 0, LV_DIR_BOTTOM);
  lv_obj_t *label = lv_label_create (tile1);
  lv_label_set_text (label, "Scroll down");
  lv_obj_center (label);


  /*Tile2: a button*/
  lv_obj_t *tile2 = lv_tileview_add_tile (tv, 0, 1, LV_DIR_TOP | LV_DIR_RIGHT);

  lv_obj_t *btn = lv_btn_create (tile2);

  label = lv_label_create (btn);
  lv_label_set_text (label, "Scroll up or right");

  lv_obj_set_size (btn, LV_SIZE_CONTENT, LV_SIZE_CONTENT);
  lv_obj_center (btn);

  /*Tile3: a list*/
  lv_obj_t *tile3 = lv_tileview_add_tile (tv, 1, 1, LV_DIR_LEFT);
  lv_obj_t *list = lv_list_create (tile3);
  lv_obj_set_size (list, LV_PCT (100), LV_PCT (100));

  lv_list_add_btn (list, NULL, "One");
  lv_list_add_btn (list, NULL, "Two");
  lv_list_add_btn (list, NULL, "Three");
  lv_list_add_btn (list, NULL, "Four");
  lv_list_add_btn (list, NULL, "Five");
  lv_list_add_btn (list, NULL, "Six");
  lv_list_add_btn (list, NULL, "Seven");
  lv_list_add_btn (list, NULL, "Eight");
  lv_list_add_btn (list, NULL, "Nine");
  lv_list_add_btn (list, NULL, "Ten");
}
