/*
  https://docs.lvgl.io/master/widgets/calendar.html
*/

// replace LOCAL_ by LV_ in prod
#define LOCAL_USE_CALENDAR_HEADER_DROPDOWN 1
#define LOCAL_USE_CALENDAR_HEADER_ARROW 0

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
  lv_example_calendar_1 ();
}

void local_loop (void) {
}


static void event_handler (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *obj = lv_event_get_current_target (e);

  if (code == LV_EVENT_VALUE_CHANGED) {
    lv_calendar_date_t date;
    if (lv_calendar_get_pressed_date (obj, &date)) {
      LV_LOG_USER ("Clicked date: %02d.%02d.%d", date.day, date.month, date.year);
    }
  }
}

void lv_example_calendar_1 (void) {
  lv_obj_t  *calendar = lv_calendar_create (lv_scr_act ());
  lv_obj_set_size (calendar, 185, 185);
  lv_obj_align (calendar, LV_ALIGN_CENTER, 0, 27);
  lv_obj_add_event_cb (calendar, event_handler, LV_EVENT_ALL, NULL);

  lv_calendar_set_today_date (calendar, 2021, 02, 23);
  lv_calendar_set_showed_date (calendar, 2021, 02);

  /*Highlight a few days*/
  static lv_calendar_date_t highlighted_days[3];       /*Only its pointer will be saved so should be static*/
  highlighted_days[0].year = 2021;
  highlighted_days[0].month = 02;
  highlighted_days[0].day = 6;

  highlighted_days[1].year = 2021;
  highlighted_days[1].month = 02;
  highlighted_days[1].day = 11;

  highlighted_days[2].year = 2022;
  highlighted_days[2].month = 02;
  highlighted_days[2].day = 22;

  lv_calendar_set_highlighted_dates (calendar, highlighted_days, 3);

#if LOCAL_USE_CALENDAR_HEADER_DROPDOWN
  lv_calendar_header_dropdown_create (calendar);
#elif LOCAL_USE_CALENDAR_HEADER_ARROW
 lv_calendar_header_arrow_create (calendar);
#endif
  lv_calendar_set_showed_date (calendar, 2021, 10);
}
