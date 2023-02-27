/*
  https://docs.lvgl.io/master/widgets/span.html
*/

// in lv_conf.h set #define LV_FONT_MONTSERRAT_24 1
// in lv_conf.h set #define LV_FONT_MONTSERRAT_20 1

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
  lv_example_span_1 ();
}

void local_loop (void) {
}


/**
 * Create span.
 */
void lv_example_span_1 (void) {
  static lv_style_t style;
  lv_style_init (&style);
  lv_style_set_border_width (&style, 1);
  lv_style_set_border_color (&style, lv_palette_main (LV_PALETTE_ORANGE));
  lv_style_set_pad_all (&style, 2);

  lv_obj_t *spans = lv_spangroup_create (lv_scr_act ());
  lv_obj_set_width (spans, 300);
  lv_obj_set_height (spans, 300);
  lv_obj_center (spans);
  lv_obj_add_style (spans, &style, 0);

  lv_spangroup_set_align (spans, LV_TEXT_ALIGN_LEFT);
  lv_spangroup_set_overflow (spans, LV_SPAN_OVERFLOW_CLIP);
  lv_spangroup_set_indent (spans, 20);
  lv_spangroup_set_mode (spans, LV_SPAN_MODE_BREAK);

  lv_span_t *span = lv_spangroup_new_span (spans);
  lv_span_set_text (span, "China is a beautiful country.");
  lv_style_set_text_color (&span->style, lv_palette_main (LV_PALETTE_RED));
  lv_style_set_text_decor (&span->style, LV_TEXT_DECOR_UNDERLINE);
  lv_style_set_text_opa (&span->style, LV_OPA_50);

  span = lv_spangroup_new_span (spans);
  lv_span_set_text_static (span, "good good study, day day up.");
#if LV_FONT_MONTSERRAT_24
  lv_style_set_text_font (&span->style,  &lv_font_montserrat_24);
#endif
  lv_style_set_text_color (&span->style, lv_palette_main (LV_PALETTE_GREEN));

  span = lv_spangroup_new_span (spans);
  lv_span_set_text_static (span, "LVGL is an open-source graphics library.");
  lv_style_set_text_color (&span->style, lv_palette_main (LV_PALETTE_BLUE));

  span = lv_spangroup_new_span (spans);
  lv_span_set_text_static (span, "the boy no name.");
  lv_style_set_text_color (&span->style, lv_palette_main (LV_PALETTE_GREEN));
#if LV_FONT_MONTSERRAT_20
  lv_style_set_text_font (&span->style, &lv_font_montserrat_20);
#endif
  lv_style_set_text_decor (&span->style, LV_TEXT_DECOR_UNDERLINE);

  span = lv_spangroup_new_span (spans);
  lv_span_set_text (span, "I have a dream that hope to come true.");
  lv_style_set_text_decor (&span->style, LV_TEXT_DECOR_STRIKETHROUGH);

  lv_spangroup_refr_mode (spans);
}
