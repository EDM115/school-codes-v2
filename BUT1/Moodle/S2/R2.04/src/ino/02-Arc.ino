/*
  https://docs.lvgl.io/master/widgets/arc.html
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
void
display_flush (lv_disp_drv_t *disp, const lv_area_t *area, lv_color_t *color_p) {
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
  lv_example_arc_1 ();
  lv_example_arc_2 ();
}

void local_loop (void) {
}


static void
value_changed_event_cb (lv_event_t *e);

void lv_example_arc_1 (void) {
  lv_obj_t *label = lv_label_create (lv_scr_act ());

  /*Create an Arc*/
  lv_obj_t *arc = lv_arc_create (lv_scr_act ());
  lv_obj_set_size (arc, 150, 150);
  lv_arc_set_rotation (arc, 135);
  lv_arc_set_bg_angles (arc, 0, 270);
  lv_arc_set_value (arc, 10);
  lv_obj_set_pos(arc, 10, 20);
  //lv_obj_center (arc);
  lv_obj_add_event_cb (arc, value_changed_event_cb, LV_EVENT_VALUE_CHANGED, label);

  /*Manually update the label for the first time*/
  lv_event_send (arc, LV_EVENT_VALUE_CHANGED, NULL);
}

static void value_changed_event_cb (lv_event_t *e) {
  lv_obj_t *arc = lv_event_get_target (e);
  lv_obj_t *label = (lv_obj_t*) lv_event_get_user_data (e);

  lv_label_set_text_fmt (label, "%d%%", lv_arc_get_value (arc));

  /*Rotate the label to the current position of the arc*/
  lv_arc_rotate_obj_to_angle (arc, label, 25);
}

static void set_angle (void *obj, int32_t v) {
  lv_arc_set_value ((lv_obj_t *) obj, v);
}

/**
 * Create an arc which acts as a loader.
 */
void lv_example_arc_2 (void) {
  /*Create an Arc*/
  lv_obj_t *arc = lv_arc_create (lv_scr_act ());
  lv_arc_set_rotation (arc, 270);
  lv_arc_set_bg_angles (arc, 0, 360);
  lv_obj_remove_style (arc, NULL, LV_PART_KNOB);   /*Be sure the knob is not displayed*/
  lv_obj_clear_flag (arc, LV_OBJ_FLAG_CLICKABLE);  /*To not allow adjusting by click*/
  lv_obj_center (arc);

  lv_anim_t a;
  lv_anim_init (&a);
  lv_anim_set_var (&a, arc);
  lv_anim_set_exec_cb (&a, set_angle);
  lv_anim_set_time (&a, 1000);
  lv_anim_set_repeat_count (&a, LV_ANIM_REPEAT_INFINITE);    /*Just for the demo*/
  lv_anim_set_repeat_delay (&a, 500);
  lv_anim_set_values (&a, 0, 100);
  lv_anim_start (&a);
}

// ########################################
