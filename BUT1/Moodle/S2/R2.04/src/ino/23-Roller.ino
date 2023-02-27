/*
  https://docs.lvgl.io/master/widgets/roller.html
*/

// error: 'lv_font_montserrat_22' was not declared in this scope
// => in lv_conf.h set #define LV_FONT_MONTSERRAT_22 1

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
  lv_example_roller_1 ();
  // lv_example_roller_2 ();
  // lv_example_roller_3 ();
}

void local_loop (void) {
}


static void event_handler1 (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *obj = lv_event_get_target (e);
  if (code == LV_EVENT_VALUE_CHANGED) {
    char buf[32];
    lv_roller_get_selected_str (obj, buf, sizeof (buf));
    LV_LOG_USER ("Selected month: %s\n", buf);
  }
}

/**
 * An infinite roller with the name of the months
 */
void lv_example_roller_1 (void) {
  lv_obj_t *roller1 = lv_roller_create (lv_scr_act ());
  lv_roller_set_options
    (roller1,
     "January\n"	"February\n"	"March\n"
     "April\n"		"May\n"		"June\n"
     "July\n"		"August\n"	"September\n"
     "October\n"	"November\n"	"December",
     LV_ROLLER_MODE_INFINITE);

  lv_roller_set_visible_row_count (roller1, 4);
  lv_obj_center (roller1);
  lv_obj_add_event_cb (roller1, event_handler1, LV_EVENT_ALL, NULL);
}

static void event_handler2 (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *obj = lv_event_get_target (e);
  if (code == LV_EVENT_VALUE_CHANGED) {
    char buf[32];
    lv_roller_get_selected_str (obj, buf, sizeof (buf));
    LV_LOG_USER ("Selected value: %s", buf);
  }
}

/**
 * Roller with various alignments and larger text in the selected area
 */
void lv_example_roller_2 (void) {
  /*A style to make the selected option larger*/
  static lv_style_t style_sel;
  lv_style_init (&style_sel);
  lv_style_set_text_font (&style_sel, &lv_font_montserrat_22);
  lv_style_set_bg_color (&style_sel, lv_color_hex3 (0xf88));
  lv_style_set_border_width (&style_sel, 2);
  lv_style_set_border_color (&style_sel, lv_color_hex3 (0xf00));

  const char *opts = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10";
  lv_obj_t *roller;

  /*A roller on the left with left aligned text, and custom width*/
  roller = lv_roller_create (lv_scr_act ());
  lv_roller_set_options (roller, opts, LV_ROLLER_MODE_NORMAL);
  lv_roller_set_visible_row_count (roller, 2);
  lv_obj_set_width (roller, 100);
  lv_obj_add_style (roller, &style_sel, LV_PART_SELECTED);
  lv_obj_set_style_text_align (roller, LV_TEXT_ALIGN_LEFT, 0);
  lv_obj_set_style_bg_color (roller, lv_color_hex3 (0x0f0), 0);
  lv_obj_set_style_bg_grad_color (roller, lv_color_hex3 (0xafa), 0);
  lv_obj_set_style_bg_grad_dir (roller, LV_GRAD_DIR_VER, 0);
  lv_obj_align (roller, LV_ALIGN_LEFT_MID, 10, 0);
  lv_obj_add_event_cb (roller, event_handler2, LV_EVENT_ALL, NULL);
  lv_roller_set_selected (roller, 2, LV_ANIM_OFF);

  /*A roller on the middle with center aligned text, and auto (default) width*/
  roller = lv_roller_create (lv_scr_act ());
  lv_roller_set_options (roller, opts, LV_ROLLER_MODE_NORMAL);
  lv_roller_set_visible_row_count (roller, 3);
  lv_obj_add_style (roller, &style_sel, LV_PART_SELECTED);
  lv_obj_align (roller, LV_ALIGN_CENTER, 0, 0);
  lv_obj_add_event_cb (roller, event_handler2, LV_EVENT_ALL, NULL);
  lv_roller_set_selected (roller, 5, LV_ANIM_OFF);

  /*A roller on the right with right aligned text, and custom width*/
  roller = lv_roller_create (lv_scr_act ());
  lv_roller_set_options (roller, opts, LV_ROLLER_MODE_NORMAL);
  lv_roller_set_visible_row_count (roller, 4);
  lv_obj_set_width (roller, 80);
  lv_obj_add_style (roller, &style_sel, LV_PART_SELECTED);
  lv_obj_set_style_text_align (roller, LV_TEXT_ALIGN_RIGHT, 0);
  lv_obj_align (roller, LV_ALIGN_RIGHT_MID, -10, 0);
  lv_obj_add_event_cb (roller, event_handler2, LV_EVENT_ALL, NULL);
  lv_roller_set_selected (roller, 8, LV_ANIM_OFF);
}

static void mask_event_cb (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *obj = lv_event_get_target (e);

  static int16_t mask_top_id = -1;
  static int16_t mask_bottom_id = -1;

  if (code == LV_EVENT_COVER_CHECK) {
    lv_event_set_cover_res (e, LV_COVER_RES_MASKED);

  }
  else if (code == LV_EVENT_DRAW_MAIN_BEGIN) {
    /* add mask */
    const lv_font_t *font = lv_obj_get_style_text_font (obj, LV_PART_MAIN);
    lv_coord_t line_space = lv_obj_get_style_text_line_space (obj, LV_PART_MAIN);
    lv_coord_t font_h = lv_font_get_line_height (font);

    lv_area_t roller_coords;
    lv_obj_get_coords (obj, &roller_coords);

    lv_area_t rect_area;
    rect_area.x1 = roller_coords.x1;
    rect_area.x2 = roller_coords.x2;
    rect_area.y1 = roller_coords.y1;
    rect_area.y2 = roller_coords.y1 + (lv_obj_get_height (obj) - font_h - line_space) / 2;

    lv_draw_mask_fade_param_t *fade_mask_top = (lv_draw_mask_fade_param_t *) malloc (sizeof (lv_draw_mask_fade_param_t));
    lv_draw_mask_fade_init (fade_mask_top, &rect_area, LV_OPA_TRANSP, rect_area.y1, LV_OPA_COVER, rect_area.y2);
    mask_top_id = lv_draw_mask_add (fade_mask_top, NULL);

    rect_area.y1 = rect_area.y2 + font_h + line_space - 1;
    rect_area.y2 = roller_coords.y2;

    lv_draw_mask_fade_param_t *fade_mask_bottom = (lv_draw_mask_fade_param_t *)malloc (sizeof (lv_draw_mask_fade_param_t));
    lv_draw_mask_fade_init (fade_mask_bottom, &rect_area, LV_OPA_COVER, rect_area.y1, LV_OPA_TRANSP, rect_area.y2);
    mask_bottom_id = lv_draw_mask_add (fade_mask_bottom, NULL);

  }
  else if (code == LV_EVENT_DRAW_POST_END) {
    lv_draw_mask_fade_param_t *fade_mask_top = (lv_draw_mask_fade_param_t *) lv_draw_mask_remove_id (mask_top_id);
    lv_draw_mask_fade_param_t *fade_mask_bottom = (lv_draw_mask_fade_param_t *) lv_draw_mask_remove_id (mask_bottom_id);
    lv_draw_mask_free_param (fade_mask_top);
    lv_draw_mask_free_param (fade_mask_bottom);
    free (fade_mask_top);
    free (fade_mask_bottom);
    mask_top_id = -1;
    mask_bottom_id = -1;
  }
}

/**
 * Add a fade mask to roller.
 */
void lv_example_roller_3 (void) {
  static lv_style_t style;
  lv_style_init (&style);
  lv_style_set_bg_color (&style, lv_color_black ());
  lv_style_set_text_color (&style, lv_color_white ());
  lv_style_set_border_width (&style, 0);
  lv_style_set_pad_all (&style, 0);
  lv_obj_add_style (lv_scr_act (), &style, 0);

  lv_obj_t *roller1 = lv_roller_create (lv_scr_act ());
  lv_obj_add_style (roller1, &style, 0);
  lv_obj_set_style_bg_opa (roller1, LV_OPA_TRANSP, LV_PART_SELECTED);

#if LV_FONT_MONTSERRAT_22
  lv_obj_set_style_text_font (roller1, &lv_font_montserrat_22, LV_PART_SELECTED);
#endif

  lv_roller_set_options
    (roller1,
     "January\n"	"February\n"	"March\n"
     "April\n"		"May\n"		"June\n"
     "July\n"		"August\n"	"September\n"
     "October\n"	"November\n"	"December",
     LV_ROLLER_MODE_NORMAL);

  lv_obj_center (roller1);
  lv_roller_set_visible_row_count (roller1, 3);
  lv_obj_add_event_cb (roller1, mask_event_cb, LV_EVENT_ALL, NULL);
}
