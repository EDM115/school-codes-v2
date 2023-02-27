/*
  https://docs.lvgl.io/master/widgets/imgbtn.html
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
  lv_example_imgbtn_1 ();
}

void local_loop (void) {
}


void lv_example_imgbtn_1 (void) {
  LV_IMG_DECLARE (imgbtn_left);
  LV_IMG_DECLARE (imgbtn_right);
  LV_IMG_DECLARE (imgbtn_mid);

  /*Create a transition animation on width transformation and recolor.*/
  static lv_style_prop_t tr_prop[] = {LV_STYLE_TRANSFORM_WIDTH, LV_STYLE_IMG_RECOLOR_OPA, (lv_style_prop_t) 0};
  static lv_style_transition_dsc_t tr;
  lv_style_transition_dsc_init (&tr, tr_prop, lv_anim_path_linear, 200, 0, NULL);

  static lv_style_t style_def;
  lv_style_init (&style_def);
  lv_style_set_text_color (&style_def, lv_color_white ());
  lv_style_set_transition (&style_def, &tr);

  /*Darken the button when pressed and make it wider*/
  static lv_style_t style_pr;
  lv_style_init (&style_pr);
  lv_style_set_img_recolor_opa (&style_pr, LV_OPA_30);
  lv_style_set_img_recolor (&style_pr, lv_color_black ());
  lv_style_set_transform_width (&style_pr, 20);

  /*Create an image button*/
  lv_obj_t *imgbtn1 = lv_imgbtn_create (lv_scr_act ());
  lv_imgbtn_set_src (imgbtn1, LV_IMGBTN_STATE_RELEASED, &imgbtn_left, &imgbtn_mid, &imgbtn_right);
  lv_obj_add_style (imgbtn1, &style_def, 0);
  lv_obj_add_style (imgbtn1, &style_pr, LV_STATE_PRESSED);

  lv_obj_align (imgbtn1, LV_ALIGN_CENTER, 0, 0);

  /*Create a label on the image button*/
  lv_obj_t *label = lv_label_create (imgbtn1);
  lv_label_set_text (label, "Button");
  lv_obj_align (label, LV_ALIGN_CENTER, 0, -4);
}
