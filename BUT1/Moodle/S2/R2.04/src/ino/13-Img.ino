/*
  https://docs.lvgl.io/master/widgets/img.html
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
  lv_example_img_1 ();
  //lv_example_img_2 ();
  //lv_example_img_3 ();
  //lv_example_img_4 ();
}

void local_loop (void) {
}


void lv_example_img_1 (void) {
  LV_IMG_DECLARE (img_cogwheel_argb);
  lv_obj_t *img1 = lv_img_create (lv_scr_act ());
  lv_img_set_src (img1, &img_cogwheel_argb);
  lv_obj_align (img1, LV_ALIGN_CENTER, 0, -20);
  lv_obj_set_size (img1, 200, 200);

  lv_obj_t *img2 = lv_img_create (lv_scr_act ());
  lv_img_set_src (img2, LV_SYMBOL_OK "Accept");
  lv_obj_align_to (img2, img1, LV_ALIGN_OUT_BOTTOM_MID, 0, 20);
}

static lv_obj_t *create_slider (lv_color_t color);
static void slider_event_cb (lv_event_t *e);

static lv_obj_t *red_slider, *green_slider, *blue_slider, *intense_slider;
static lv_obj_t *img1;

/**
 * Demonstrate runtime image re-coloring
 */
void lv_example_img_2 (void) {
  /*Create 4 sliders to adjust RGB color and re-color intensity*/
  red_slider = create_slider (lv_palette_main (LV_PALETTE_RED));
  green_slider = create_slider (lv_palette_main (LV_PALETTE_GREEN));
  blue_slider = create_slider (lv_palette_main (LV_PALETTE_BLUE));
  intense_slider = create_slider (lv_palette_main (LV_PALETTE_GREY));

  lv_slider_set_value (red_slider, LV_OPA_20, LV_ANIM_OFF);
  lv_slider_set_value (green_slider, LV_OPA_90, LV_ANIM_OFF);
  lv_slider_set_value (blue_slider, LV_OPA_60, LV_ANIM_OFF);
  lv_slider_set_value (intense_slider, LV_OPA_50, LV_ANIM_OFF);

  lv_obj_align (red_slider, LV_ALIGN_LEFT_MID, 25, 0);
  lv_obj_align_to (green_slider, red_slider, LV_ALIGN_OUT_RIGHT_MID, 25, 0);
  lv_obj_align_to (blue_slider, green_slider, LV_ALIGN_OUT_RIGHT_MID, 25, 0);
  lv_obj_align_to (intense_slider, blue_slider, LV_ALIGN_OUT_RIGHT_MID, 25, 0);

  /*Now create the actual image*/
  LV_IMG_DECLARE (img_cogwheel_argb)
    img1 = lv_img_create (lv_scr_act ());
  lv_img_set_src (img1, &img_cogwheel_argb);
  lv_obj_align (img1, LV_ALIGN_RIGHT_MID, -20, 0);

  lv_event_send (intense_slider, LV_EVENT_VALUE_CHANGED, NULL);
}

static void slider_event_cb (lv_event_t *e) {
  LV_UNUSED (e);

  /*Recolor the image based on the sliders' values*/
  lv_color_t color  = lv_color_make (lv_slider_get_value (red_slider), lv_slider_get_value (green_slider),
				     lv_slider_get_value (blue_slider));
  lv_opa_t intense = lv_slider_get_value (intense_slider);
  lv_obj_set_style_img_recolor_opa (img1, intense, 0);
  lv_obj_set_style_img_recolor (img1, color, 0);
}

static lv_obj_t *create_slider (lv_color_t color) {
  lv_obj_t *slider = lv_slider_create (lv_scr_act ());
  lv_slider_set_range (slider, 0, 255);
  lv_obj_set_size (slider, 10, 200);
  lv_obj_set_style_bg_color (slider, color, LV_PART_KNOB);
  lv_obj_set_style_bg_color (slider, lv_color_darken (color, LV_OPA_40), LV_PART_INDICATOR);
  lv_obj_add_event_cb (slider, slider_event_cb, LV_EVENT_VALUE_CHANGED, NULL);
  return slider;
}

static void set_angle (void *img, int32_t v) {
  lv_img_set_angle ((lv_obj_t *) img, v);
}

static void set_zoom (void *img, int32_t v) {
  lv_img_set_zoom ((lv_obj_t *) img, v);
}


/**
 * Show transformations (zoom and rotation) using a pivot point.
 */
void lv_example_img_3 (void) {
  LV_IMG_DECLARE (img_cogwheel_argb);

  /*Now create the actual image*/
  lv_obj_t *img = lv_img_create (lv_scr_act ());
  lv_img_set_src (img, &img_cogwheel_argb);
  lv_obj_align (img, LV_ALIGN_CENTER, 50, 50);
  lv_img_set_pivot (img, 0, 0);    /*Rotate around the top left corner*/

  lv_anim_t a;
  lv_anim_init (&a);
  lv_anim_set_var (&a, img);
  lv_anim_set_exec_cb (&a, set_angle);
  lv_anim_set_values (&a, 0, 3600);
  lv_anim_set_time (&a, 5000);
  lv_anim_set_repeat_count (&a, LV_ANIM_REPEAT_INFINITE);
  lv_anim_start (&a);

  lv_anim_set_exec_cb (&a, set_zoom);
  lv_anim_set_values (&a, 128, 256);
  lv_anim_set_playback_time (&a, 3000);
  lv_anim_start (&a);
}

static void ofs_y_anim (void *img, int32_t v) {
  lv_img_set_offset_y ((lv_obj_t *) img, v);
}

/**
 * Image styling and offset
 */
void lv_example_img_4 (void) {
  LV_IMG_DECLARE (img_skew_strip);

  static lv_style_t style;
  lv_style_init (&style);
  lv_style_set_bg_color (&style, lv_palette_main (LV_PALETTE_YELLOW));
  lv_style_set_bg_opa (&style, LV_OPA_COVER);
  lv_style_set_img_recolor_opa (&style, LV_OPA_COVER);
  lv_style_set_img_recolor (&style, lv_color_black ());

  lv_obj_t *img = lv_img_create (lv_scr_act ());
  lv_obj_add_style (img, &style, 0);
  lv_img_set_src (img, &img_skew_strip);
  lv_obj_set_size (img, 150, 100);
  lv_obj_center (img);

  lv_anim_t a;
  lv_anim_init (&a);
  lv_anim_set_var (&a, img);
  lv_anim_set_exec_cb (&a, ofs_y_anim);
  lv_anim_set_values (&a, 0, 100);
  lv_anim_set_time (&a, 3000);
  lv_anim_set_playback_time (&a, 500);
  lv_anim_set_repeat_count (&a, LV_ANIM_REPEAT_INFINITE);
  lv_anim_start (&a);
}
