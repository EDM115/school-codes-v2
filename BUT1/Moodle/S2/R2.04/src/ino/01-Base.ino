/*
  https://docs.lvgl.io/master/widgets/obj.html
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
  lcd.pushColors ((uint16_t *) &color_p->full, w * h, true);
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
  lv_example_obj_1 ();
  lv_example_obj_2 ();
}

void local_loop (void) {
}

void lv_example_obj_1 (void) {
  lv_obj_t *obj1;
  obj1 = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (obj1, 100, 50);
  lv_obj_align (obj1, LV_ALIGN_CENTER, -60, -30);

  static lv_style_t style_shadow;
  lv_style_init (&style_shadow);
  lv_style_set_shadow_width (&style_shadow, 10);
  lv_style_set_shadow_spread (&style_shadow, 5);
  lv_style_set_shadow_color (&style_shadow, lv_palette_main (LV_PALETTE_BLUE));

  lv_obj_t *obj2;
  obj2 = lv_obj_create (lv_scr_act ());
  lv_obj_add_style (obj2, &style_shadow, 0);
  lv_obj_align (obj2, LV_ALIGN_CENTER, 60, 30);
}

/**
 * Make an object dragable.
 */
void lv_example_obj_2 (void) {
    lv_obj_t *obj;
    obj = lv_obj_create (lv_scr_act ());
    lv_obj_set_size (obj, 150, 100);
    lv_obj_add_event_cb (obj, drag_event_handler, LV_EVENT_PRESSING, NULL);

    lv_obj_t *label = lv_label_create (obj);
    lv_label_set_text (label, "Drag me");
    lv_obj_center (label);
}

static void drag_event_handler (lv_event_t *e) {
    lv_obj_t *obj = lv_event_get_target (e);

    lv_indev_t *indev = lv_indev_get_act ();
    if (indev == NULL)
      return;

    lv_point_t vect;
    lv_indev_get_vect (indev, &vect);

    lv_coord_t x = lv_obj_get_x (obj) + vect.x;
    lv_coord_t y = lv_obj_get_y (obj) + vect.y;
    lv_obj_set_pos (obj, x, y);
}

// ########################################
