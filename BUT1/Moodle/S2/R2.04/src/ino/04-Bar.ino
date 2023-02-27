/*
  https://docs.lvgl.io/master/widgets/bar.html
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
  lv_example_bar_1 ();
  lv_example_bar_2 ();
  lv_example_bar_3 ();
  lv_example_bar_4 ();
  lv_example_bar_5 ();
  lv_example_bar_6 ();
}

void local_loop (void) {
}

void lv_example_bar_1 (void) {
  lv_obj_t *bar1 = lv_bar_create (lv_scr_act ());
  lv_obj_set_size (bar1, 200, 20);
  lv_obj_set_pos (bar1, 10, 30);
  lv_bar_set_value (bar1, 70, LV_ANIM_OFF);
}

/**
 * Example of styling the bar
 */
void lv_example_bar_2 (void) {
  static lv_style_t style_bg;
  static lv_style_t style_indic;

  lv_style_init (&style_bg);
  lv_style_set_border_color (&style_bg, lv_palette_main (LV_PALETTE_BLUE));
  lv_style_set_border_width (&style_bg, 2);
  lv_style_set_pad_all (&style_bg, 6); /*To make the indicator smaller*/
  lv_style_set_radius (&style_bg, 6);
  lv_style_set_anim_time (&style_bg, 1000);

  lv_style_init (&style_indic);
  lv_style_set_bg_opa (&style_indic, LV_OPA_COVER);
  lv_style_set_bg_color (&style_indic, lv_palette_main (LV_PALETTE_BLUE));
  lv_style_set_radius (&style_indic, 3);

  lv_obj_t *bar = lv_bar_create (lv_scr_act ());
  lv_obj_remove_style_all (bar);  /*To have a clean start*/
  lv_obj_add_style (bar, &style_bg, 0);
  lv_obj_add_style (bar, &style_indic, LV_PART_INDICATOR);

  lv_obj_set_size (bar, 200, 20);
  lv_obj_set_pos (bar, 10, 60);
  lv_bar_set_value (bar, 100, LV_ANIM_ON);
}

static void set_temp (void *bar, int32_t temp) {
  lv_bar_set_value ((lv_obj_t *) bar, temp, LV_ANIM_ON);
}

/**
 * A temperature meter example
 */
void lv_example_bar_3 (void) {
  static lv_style_t style_indic;

  lv_style_init (&style_indic);
  lv_style_set_bg_opa (&style_indic, LV_OPA_COVER);
  lv_style_set_bg_color (&style_indic, lv_palette_main (LV_PALETTE_RED));
  lv_style_set_bg_grad_color (&style_indic, lv_palette_main (LV_PALETTE_BLUE));
  lv_style_set_bg_grad_dir (&style_indic, LV_GRAD_DIR_VER);

  lv_obj_t *bar = lv_bar_create (lv_scr_act ());
  lv_obj_add_style (bar, &style_indic, LV_PART_INDICATOR);
  lv_obj_set_size (bar, 20, 200);
  lv_obj_set_pos (bar, 400, 10);
  lv_bar_set_range (bar, -20, 40);

  lv_anim_t a;
  lv_anim_init (&a);
  lv_anim_set_exec_cb (&a, set_temp);
  lv_anim_set_time (&a, 3000);
  lv_anim_set_playback_time (&a, 3000);
  lv_anim_set_var (&a, bar);
  lv_anim_set_values (&a, -20, 40);
  lv_anim_set_repeat_count (&a, LV_ANIM_REPEAT_INFINITE);
  lv_anim_start (&a);
}

/**
 * Bar with stripe pattern and ranged value
 */
void lv_example_bar_4 (void) {
  LV_IMG_DECLARE (img_skew_strip);
  static lv_style_t style_indic;

  lv_style_init (&style_indic);
  lv_style_set_bg_img_src (&style_indic, &img_skew_strip);
  lv_style_set_bg_img_tiled (&style_indic, true);
  lv_style_set_bg_img_opa (&style_indic, LV_OPA_30);

  lv_obj_t *bar = lv_bar_create (lv_scr_act ());
  lv_obj_add_style (bar, &style_indic, LV_PART_INDICATOR);

  lv_obj_set_size (bar, 260, 20);
  lv_obj_set_pos (bar, 10, 90);
  lv_bar_set_mode (bar, LV_BAR_MODE_RANGE);
  lv_bar_set_value (bar, 90, LV_ANIM_OFF);
  lv_bar_set_start_value (bar, 20, LV_ANIM_OFF);
}

/**
 * Bar with LTR and RTL base direction
 */
void lv_example_bar_5 (void) {
  lv_obj_t *label;


  lv_obj_t *bar_ltr = lv_bar_create (lv_scr_act ());
  lv_obj_set_size (bar_ltr, 200, 20);
  lv_bar_set_value (bar_ltr, 70, LV_ANIM_OFF);
  lv_obj_align (bar_ltr, LV_ALIGN_CENTER, 0, 0);

  label = lv_label_create (lv_scr_act ());
  lv_label_set_text (label, "Left to Right base direction");
  lv_obj_align_to (label, bar_ltr, LV_ALIGN_OUT_TOP_MID, 0, -5);

  lv_obj_t *bar_rtl = lv_bar_create (lv_scr_act ());
  lv_obj_set_style_base_dir (bar_rtl, LV_BASE_DIR_RTL, 0);
  lv_obj_set_size (bar_rtl, 200, 20);
  lv_bar_set_value (bar_rtl, 70, LV_ANIM_OFF);
  lv_obj_align (bar_rtl, LV_ALIGN_CENTER, 0, 60);

  label = lv_label_create (lv_scr_act ());
  lv_label_set_text (label, "Right to Left base direction");
  lv_obj_align_to (label, bar_rtl, LV_ALIGN_OUT_TOP_MID, 0, -5);
}

static void set_value (void *bar, int32_t v) {
  lv_bar_set_value ((lv_obj_t *) bar, v, LV_ANIM_OFF);
}

static void event_cb (lv_event_t *e) {
  lv_obj_draw_part_dsc_t *dsc = lv_event_get_draw_part_dsc (e);
  if (dsc->part != LV_PART_INDICATOR) return;

  lv_obj_t *obj = lv_event_get_target (e);

  lv_draw_label_dsc_t label_dsc;
  lv_draw_label_dsc_init (&label_dsc);
  label_dsc.font = LV_FONT_DEFAULT;

  char buf[8];
  lv_snprintf (buf, sizeof (buf), "%d", (int)lv_bar_get_value (obj));

  lv_point_t txt_size;
  lv_txt_get_size (&txt_size, buf, label_dsc.font, label_dsc.letter_space, label_dsc.line_space, LV_COORD_MAX,
		   label_dsc.flag);

  lv_area_t txt_area;
  /*If the indicator is long enough put the text inside on the right*/
  if (lv_area_get_width (dsc->draw_area) > txt_size.x + 20) {
    txt_area.x2 = dsc->draw_area->x2 - 5;
    txt_area.x1 = txt_area.x2 - txt_size.x + 1;
    label_dsc.color = lv_color_white ();
  }
  /*If the indicator is still short put the text out of it on the right*/
  else {
    txt_area.x1 = dsc->draw_area->x2 + 5;
    txt_area.x2 = txt_area.x1 + txt_size.x - 1;
    label_dsc.color = lv_color_black ();
  }

  txt_area.y1 = dsc->draw_area->y1 + (lv_area_get_height (dsc->draw_area) - txt_size.y) / 2;
  txt_area.y2 = txt_area.y1 + txt_size.y - 1;

  lv_draw_label (dsc->draw_ctx, &label_dsc, &txt_area, buf, NULL);
}

/**
 * Custom drawer on the bar to display the current value
 */
void lv_example_bar_6 (void) {
  lv_obj_t *bar = lv_bar_create (lv_scr_act ());
  lv_obj_add_event_cb (bar, event_cb, LV_EVENT_DRAW_PART_END, NULL);
  lv_obj_set_size (bar, 200, 20);
  lv_obj_set_pos (bar, 10, 280);

  lv_anim_t a;
  lv_anim_init (&a);
  lv_anim_set_var (&a, bar);
  lv_anim_set_values (&a, 0, 100);
  lv_anim_set_exec_cb (&a, set_value);
  lv_anim_set_time (&a, 2000);
  lv_anim_set_playback_time (&a, 2000);
  lv_anim_set_repeat_count (&a, LV_ANIM_REPEAT_INFINITE);
  lv_anim_start (&a);

}
