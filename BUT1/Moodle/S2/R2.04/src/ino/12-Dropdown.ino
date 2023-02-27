/*
  https://docs.lvgl.io/master/widgets/dropdown.html
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
  lv_example_dropdown_1 ();
  lv_example_dropdown_2 ();
  lv_example_dropdown_3 ();
}

void local_loop (void) {
}


static void event_handler (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *obj = lv_event_get_target (e);
  if (code == LV_EVENT_VALUE_CHANGED) {
    char buf[32];
    lv_dropdown_get_selected_str (obj, buf, sizeof (buf));
    LV_LOG_USER ("Option: %s", buf);
  }
}

void lv_example_dropdown_1 (void) {
  /*Create a normal drop down list*/
  lv_obj_t *dd = lv_dropdown_create (lv_scr_act ());
  lv_dropdown_set_options (dd, "Apple\n"
			   "Banana\n"
			   "Orange\n"
			   "Cherry\n"
			   "Grape\n"
			   "Raspberry\n"
			   "Melon\n"
			   "Orange\n"
			   "Lemon\n"
			   "Nuts");

  lv_obj_align (dd, LV_ALIGN_TOP_RIGHT, 0, 20);
  lv_obj_add_event_cb (dd, event_handler, LV_EVENT_ALL, NULL);
}

/**
 * Create a drop down, up, left and right menus
 */
void lv_example_dropdown_2 (void) {
  static const char *opts = "Apple\n"
    "Banana\n"
    "Orange\n"
    "Melon";

  lv_obj_t *dd;
  dd = lv_dropdown_create (lv_scr_act ());
  lv_dropdown_set_options_static (dd, opts);
  lv_obj_align (dd, LV_ALIGN_TOP_MID, 0, 10);

  dd = lv_dropdown_create (lv_scr_act ());
  lv_dropdown_set_options_static (dd, opts);
  lv_dropdown_set_dir (dd, LV_DIR_BOTTOM);
  lv_dropdown_set_symbol (dd, LV_SYMBOL_UP);
  lv_obj_align (dd, LV_ALIGN_BOTTOM_MID, 0, -10);

  dd = lv_dropdown_create (lv_scr_act ());
  lv_dropdown_set_options_static (dd, opts);
  lv_dropdown_set_dir (dd, LV_DIR_RIGHT);
  lv_dropdown_set_symbol (dd, LV_SYMBOL_RIGHT);
  lv_obj_align (dd, LV_ALIGN_LEFT_MID, 10, 0);

  dd = lv_dropdown_create (lv_scr_act ());
  lv_dropdown_set_options_static (dd, opts);
  lv_dropdown_set_dir (dd, LV_DIR_LEFT);
  lv_dropdown_set_symbol (dd, LV_SYMBOL_LEFT);
  lv_obj_align (dd, LV_ALIGN_RIGHT_MID, -10, 0);
}

static void event_cb (lv_event_t *e) {
  lv_obj_t *dropdown = lv_event_get_target (e);
  char buf[64];
  lv_dropdown_get_selected_str (dropdown, buf, sizeof (buf));
  LV_LOG_USER ("'%s' is selected", buf);
}

/**
 * Create a menu from a drop-down list and show some drop-down list features and styling
 */
void lv_example_dropdown_3 (void) {
  /*Create a drop down list*/
  lv_obj_t *dropdown = lv_dropdown_create (lv_scr_act ());
  lv_obj_align (dropdown, LV_ALIGN_TOP_LEFT, 10, 10);
  lv_dropdown_set_options (dropdown, "New project\n"
			   "New file\n"
			   "Save\n"
			   "Save as ...\n"
			   "Open project\n"
			   "Recent projects\n"
			   "Preferences\n"
			   "Exit");

  /*Set a fixed text to display on the button of the drop-down list*/
  lv_dropdown_set_text (dropdown, "Menu");

  /*Use a custom image as down icon and flip it when the list is opened*/
  LV_IMG_DECLARE (img_caret_down)
    lv_dropdown_set_symbol (dropdown, &img_caret_down);
  lv_obj_set_style_transform_angle (dropdown, 1800, LV_PART_INDICATOR | LV_STATE_CHECKED);

  /*In a menu we don't need to show the last clicked item*/
  lv_dropdown_set_selected_highlight (dropdown, false);

  lv_obj_add_event_cb (dropdown, event_cb, LV_EVENT_VALUE_CHANGED, NULL);
}
