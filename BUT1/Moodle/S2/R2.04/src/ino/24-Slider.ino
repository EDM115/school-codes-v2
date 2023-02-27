/*
  https://docs.lvgl.io/master/widgets/slider.html
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
  lv_example_slider_1 ();
  lv_example_slider_2 ();
  lv_example_slider_3 ();
}

void local_loop (void) {
}


static void slider_event_cb1 (lv_event_t *e);
static lv_obj_t *slider_label;

/**
 * A default slider with a label displaying the current value
 */
void lv_example_slider_1 (void) {
  /*Create a slider in the center of the display*/
  lv_obj_t *slider = lv_slider_create (lv_scr_act ());
  lv_obj_align (slider, LV_ALIGN_CENTER, 0, -60);
  lv_obj_add_event_cb (slider, slider_event_cb1, LV_EVENT_VALUE_CHANGED, NULL);

  /*Create a label below the slider*/
  slider_label = lv_label_create (lv_scr_act ());
  lv_label_set_text (slider_label, "0%");

  lv_obj_align_to (slider_label, slider, LV_ALIGN_OUT_BOTTOM_MID, 0, 10);
}

static void slider_event_cb1 (lv_event_t *e) {
  lv_obj_t *slider = lv_event_get_target (e);
  char buf[8];
  lv_snprintf (buf, sizeof (buf), "%d%%", (int)lv_slider_get_value (slider));
  lv_label_set_text (slider_label, buf);
  lv_obj_align_to (slider_label, slider, LV_ALIGN_OUT_BOTTOM_MID, 0, 10);
}

/**
 * Show how to style a slider.
 */
void lv_example_slider_2 (void) {
  /*Create a transition*/
  static const lv_style_prop_t props[] = {LV_STYLE_BG_COLOR, (lv_style_prop_t) 0};
  static lv_style_transition_dsc_t transition_dsc;
  lv_style_transition_dsc_init (&transition_dsc, props, lv_anim_path_linear, 300, 0, NULL);

  static lv_style_t style_main;
  static lv_style_t style_indicator;
  static lv_style_t style_knob;
  static lv_style_t style_pressed_color;
  lv_style_init (&style_main);
  lv_style_set_bg_opa (&style_main, LV_OPA_COVER);
  lv_style_set_bg_color (&style_main, lv_color_hex3 (0xbbb));
  lv_style_set_radius (&style_main, LV_RADIUS_CIRCLE);
  lv_style_set_pad_ver (&style_main, -2); /*Makes the indicator larger*/

  lv_style_init (&style_indicator);
  lv_style_set_bg_opa (&style_indicator, LV_OPA_COVER);
  lv_style_set_bg_color (&style_indicator, lv_palette_main (LV_PALETTE_CYAN));
  lv_style_set_radius (&style_indicator, LV_RADIUS_CIRCLE);
  lv_style_set_transition (&style_indicator, &transition_dsc);

  lv_style_init (&style_knob);
  lv_style_set_bg_opa (&style_knob, LV_OPA_COVER);
  lv_style_set_bg_color (&style_knob, lv_palette_main (LV_PALETTE_CYAN));
  lv_style_set_border_color (&style_knob, lv_palette_darken (LV_PALETTE_CYAN, 3));
  lv_style_set_border_width (&style_knob, 2);
  lv_style_set_radius (&style_knob, LV_RADIUS_CIRCLE);
  lv_style_set_pad_all (&style_knob, 6); /*Makes the knob larger*/
  lv_style_set_transition (&style_knob, &transition_dsc);

  lv_style_init (&style_pressed_color);
  lv_style_set_bg_color (&style_pressed_color, lv_palette_darken (LV_PALETTE_CYAN, 2));

  /*Create a slider and add the style*/
  lv_obj_t *slider = lv_slider_create (lv_scr_act ());
  lv_obj_remove_style_all (slider);        /*Remove the styles coming from the theme*/

  lv_obj_add_style (slider, &style_main, LV_PART_MAIN);
  lv_obj_add_style (slider, &style_indicator, LV_PART_INDICATOR);
  lv_obj_add_style (slider, &style_pressed_color, LV_PART_INDICATOR | LV_STATE_PRESSED);
  lv_obj_add_style (slider, &style_knob, LV_PART_KNOB);
  lv_obj_add_style (slider, &style_pressed_color, LV_PART_KNOB | LV_STATE_PRESSED);

  lv_obj_center (slider);
}

static void slider_event_cb3 (lv_event_t *e);

/**
 * Show the current value when the slider is pressed by extending the drawer
 *
 */
void lv_example_slider_3 (void) {
  /*Create a slider in the center of the display*/
  lv_obj_t *slider;
  slider = lv_slider_create (lv_scr_act ());
  lv_obj_align (slider, LV_ALIGN_CENTER, 0, 60);

  lv_slider_set_mode (slider, LV_SLIDER_MODE_RANGE);
  lv_slider_set_value (slider, 70, LV_ANIM_OFF);
  lv_slider_set_left_value (slider, 20, LV_ANIM_OFF);

  lv_obj_add_event_cb (slider, slider_event_cb3, LV_EVENT_ALL, NULL);
  lv_obj_refresh_ext_draw_size (slider);
}

static void slider_event_cb3 (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *obj = lv_event_get_target (e);

  /*Provide some extra space for the value*/
  if (code == LV_EVENT_REFR_EXT_DRAW_SIZE) {
    lv_event_set_ext_draw_size (e, 50);
  }
  else if (code == LV_EVENT_DRAW_PART_END) {
    lv_obj_draw_part_dsc_t *dsc = lv_event_get_draw_part_dsc (e);
    if (dsc->part == LV_PART_INDICATOR) {
      char buf[16];
      lv_snprintf (buf, sizeof (buf), "%d - %d", (int)lv_slider_get_left_value (obj), (int)lv_slider_get_value (obj));

      lv_point_t label_size;
      lv_txt_get_size (&label_size, buf, LV_FONT_DEFAULT, 0, 0, LV_COORD_MAX, 0);
      lv_area_t label_area;
      label_area.x1 = dsc->draw_area->x1 + lv_area_get_width (dsc->draw_area) / 2 - label_size.x / 2;
      label_area.x2 = label_area.x1 + label_size.x;
      label_area.y2 = dsc->draw_area->y1 - 10;
      label_area.y1 = label_area.y2 - label_size.y;

      lv_draw_label_dsc_t label_draw_dsc;
      lv_draw_label_dsc_init (&label_draw_dsc);
      label_draw_dsc.color = lv_color_hex3 (0x888);
      lv_draw_label (dsc->draw_ctx, &label_draw_dsc, &label_area, buf, NULL);
    }
  }
}
