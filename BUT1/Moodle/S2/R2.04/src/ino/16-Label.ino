/*
  https://docs.lvgl.io/master/widgets/label.html
*/

// error: 'lv_font_montserrat_16' was not declared in this scope
// => in lv_conf.h set #define LV_FONT_MONTSERRAT_16 1
// error: 'lv_font_dejavu_16_persian_hebrew' was not declared in this scope
// => in lv_conf.h set #define LV_FONT_DEJAVU_16_PERSIAN_HEBREW 1
// error: 'lv_font_simsun_16_cjk' was not declared in this scope
// => in lv_conf.h set #define LV_FONT_SIMSUN_16_CJK 1

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
  lv_example_label_1 ();
  // lv_example_label_2 ();
  // lv_example_label_3 ();
  // lv_example_label_4 ();
  // lv_example_label_5 ();
}

void local_loop (void) {
}


/**
 * Show line wrap, re-color, line align and text scrolling.
 */
void lv_example_label_1 (void) {
  lv_obj_t *label1 = lv_label_create (lv_scr_act ());
  lv_label_set_long_mode (label1, LV_LABEL_LONG_WRAP);     /*Break the long lines*/
  lv_label_set_recolor (label1, true);                      /*Enable re-coloring by commands in the text*/
  lv_label_set_text (label1, "#0000ff Re-color# #ff00ff words# #ff0000 of a# label, align the lines to the center "
		     "and wrap long text automatically.");
  lv_obj_set_width (label1, 150);  /*Set smaller width to make the lines wrap*/
  lv_obj_set_style_text_align (label1, LV_TEXT_ALIGN_CENTER, 0);
  lv_obj_align (label1, LV_ALIGN_CENTER, 0, -40);

  lv_obj_t *label2 = lv_label_create (lv_scr_act ());
  lv_label_set_long_mode (label2, LV_LABEL_LONG_SCROLL_CIRCULAR);     /*Circular scroll*/
  lv_obj_set_width (label2, 150);
  lv_label_set_text (label2, "It is a circularly scrolling text. ");
  lv_obj_align (label2, LV_ALIGN_CENTER, 0, 40);
}

/**
 * Create a fake text shadow
 */
void lv_example_label_2 (void) {
  /*Create a style for the shadow*/
  static lv_style_t style_shadow;
  lv_style_init (&style_shadow);
  lv_style_set_text_opa (&style_shadow, LV_OPA_30);
  lv_style_set_text_color (&style_shadow, lv_color_black ());

  /*Create a label for the shadow first (it's in the background)*/
  lv_obj_t *shadow_label = lv_label_create (lv_scr_act ());
  lv_obj_add_style (shadow_label, &style_shadow, 0);

  /*Create the main label*/
  lv_obj_t *main_label = lv_label_create (lv_scr_act ());
  lv_label_set_text (main_label, "A simple method to create\n"
		     "shadows on a text.\n"
		     "It even works with\n\n"
		     "newlines     and spaces.");

  /*Set the same text for the shadow label*/
  lv_label_set_text (shadow_label, lv_label_get_text (main_label));

  /*Position the main label*/
  lv_obj_align (main_label, LV_ALIGN_CENTER, 0, 0);

  /*Shift the second label down and to the right by 2 pixel*/
  lv_obj_align_to (shadow_label, main_label, LV_ALIGN_TOP_LEFT, 2, 2);
}

/**
 * Show mixed LTR, RTL and Chinese label
 */
void lv_example_label_3 (void) {
  lv_obj_t *ltr_label = lv_label_create (lv_scr_act ());
  lv_label_set_text (ltr_label, "In modern terminology, a microcontroller is similar to a system on a chip (SoC).");
  lv_obj_set_style_text_font (ltr_label, &lv_font_montserrat_16, 0);
  lv_obj_set_width (ltr_label, 310);
  lv_obj_align (ltr_label, LV_ALIGN_TOP_LEFT, 5, 5);

  lv_obj_t *rtl_label = lv_label_create (lv_scr_act ());
  lv_label_set_text (rtl_label,
		     "מעבד, או בשמו המלא יחידת עיבוד מרכזית (באנגלית: CPU - Central Processing Unit).");
  lv_obj_set_style_base_dir (rtl_label, LV_BASE_DIR_RTL, 0);
  lv_obj_set_style_text_font (rtl_label, &lv_font_dejavu_16_persian_hebrew, 0);
  lv_obj_set_width (rtl_label, 310);
  lv_obj_align (rtl_label, LV_ALIGN_LEFT_MID, 5, 0);

  lv_obj_t *cz_label = lv_label_create (lv_scr_act ());
  lv_label_set_text (cz_label,
		     "嵌入式系统（Embedded System），\n是一种嵌入机械或电气系统内部、具有专一功能和实时计算性能的计算机系统。");
  lv_obj_set_style_text_font (cz_label, &lv_font_simsun_16_cjk, 0);
  lv_obj_set_width (cz_label, 310);
  lv_obj_align (cz_label, LV_ALIGN_BOTTOM_LEFT, 5, -5);
}

#define MASK_WIDTH 100
#define MASK_HEIGHT 45

static void add_mask_event_cb (lv_event_t *e) {
  static lv_draw_mask_map_param_t m;
  static int16_t mask_id;

  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *obj = lv_event_get_target (e);
  lv_opa_t *mask_map = (lv_opa_t *) lv_event_get_user_data (e);
  if (code == LV_EVENT_COVER_CHECK) {
    lv_event_set_cover_res (e, LV_COVER_RES_MASKED);
  }
  else if (code == LV_EVENT_DRAW_MAIN_BEGIN) {
    lv_draw_mask_map_init (&m, &obj->coords, mask_map);
    mask_id = lv_draw_mask_add (&m, NULL);

  }
  else if (code == LV_EVENT_DRAW_MAIN_END) {
    lv_draw_mask_free_param (&m);
    lv_draw_mask_remove_id (mask_id);
  }
}

/**
 * Draw label with gradient color
 */
void lv_example_label_4 (void) {
  /* Create the mask of a text by drawing it to a canvas*/
  static lv_opa_t mask_map[MASK_WIDTH * MASK_HEIGHT];

  /*Create a "8 bit alpha" canvas and clear it*/
  lv_obj_t *canvas = lv_canvas_create (lv_scr_act ());
  lv_canvas_set_buffer (canvas, mask_map, MASK_WIDTH, MASK_HEIGHT, LV_IMG_CF_ALPHA_8BIT);
  lv_canvas_fill_bg (canvas, lv_color_black (), LV_OPA_TRANSP);

  /*Draw a label to the canvas. The result "image" will be used as mask*/
  lv_draw_label_dsc_t label_dsc;
  lv_draw_label_dsc_init (&label_dsc);
  label_dsc.color = lv_color_white ();
  label_dsc.align = LV_TEXT_ALIGN_CENTER;
  lv_canvas_draw_text (canvas, 5, 5, MASK_WIDTH, &label_dsc, "Text with gradient");

  /*The mask is reads the canvas is not required anymore*/
  lv_obj_del (canvas);

  /* Create an object from where the text will be masked out.
   * Now it's a rectangle with a gradient but it could be an image too*/
  lv_obj_t *grad = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (grad, MASK_WIDTH, MASK_HEIGHT);
  lv_obj_center (grad);
  lv_obj_set_style_bg_color (grad, lv_color_hex (0xff0000), 0);
  lv_obj_set_style_bg_grad_color (grad, lv_color_hex (0x0000ff), 0);
  lv_obj_set_style_bg_grad_dir (grad, LV_GRAD_DIR_HOR, 0);
  lv_obj_add_event_cb (grad, add_mask_event_cb, LV_EVENT_ALL, mask_map);
}

/**
 * Show customizing the circular scrolling animation of a label with `LV_LABEL_LONG_SCROLL_CIRCULAR`
 * long mode.
 */
void lv_example_label_5 (void) {
  static lv_anim_t animation_template;
  static lv_style_t label_style;

  lv_anim_init (&animation_template);
  lv_anim_set_delay (&animation_template, 1000);           /*Wait 1 second to start the first scroll*/
  lv_anim_set_repeat_delay (&animation_template,
			    3000);    /*Repeat the scroll 3 seconds after the label scrolls back to the initial position*/

  /*Initialize the label style with the animation template*/
  lv_style_init (&label_style);
  lv_style_set_anim (&label_style, &animation_template);

  lv_obj_t *label1 = lv_label_create (lv_scr_act ());
  lv_label_set_long_mode (label1, LV_LABEL_LONG_SCROLL_CIRCULAR);      /*Circular scroll*/
  lv_obj_set_width (label1, 150);
  lv_label_set_text (label1, "It is a circularly scrolling text. ");
  lv_obj_align (label1, LV_ALIGN_CENTER, 0, 40);
  lv_obj_add_style (label1, &label_style, LV_STATE_DEFAULT);           /*Add the style to the label*/
}
