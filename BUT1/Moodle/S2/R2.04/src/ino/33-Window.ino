/*
  https://docs.lvgl.io/master/widgets/win.html
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
  lv_example_win_1 ();
}

void local_loop (void) {
}


static void event_handler (lv_event_t *e) {
  lv_obj_t *obj = lv_event_get_target (e);
  LV_UNUSED (obj);
  LV_LOG_USER ("Button %d clicked", (int)lv_obj_get_index (obj));
}

void lv_example_win_1 (void) {
  lv_obj_t *win = lv_win_create (lv_scr_act (), 40);
  lv_obj_t *btn;
  btn = lv_win_add_btn (win, LV_SYMBOL_LEFT, 40);
  lv_obj_add_event_cb (btn, event_handler, LV_EVENT_CLICKED, NULL);

  lv_win_add_title (win, "A title");

  btn = lv_win_add_btn (win, LV_SYMBOL_RIGHT, 40);
  lv_obj_add_event_cb (btn, event_handler, LV_EVENT_CLICKED, NULL);

  btn = lv_win_add_btn (win, LV_SYMBOL_CLOSE, 60);
  lv_obj_add_event_cb (btn, event_handler, LV_EVENT_CLICKED, NULL);

  lv_obj_t *cont = lv_win_get_content (win);  /*Content can be added here*/
  lv_obj_t *label = lv_label_create (cont);
  lv_label_set_text
    (label,
     "This is\n"	"a pretty\n"	"long text\n"
     "to see how\n"	"the window\n"	"becomes\n"
     "scrollable.\n"	"\n"		"\n"
     "Some more\n"	"text to be\n"	"sure it\n"
     "overflows. :)");
}
