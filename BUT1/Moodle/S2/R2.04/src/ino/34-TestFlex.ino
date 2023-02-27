/*
  https://docs.lvgl.io/master/layouts/flex.html
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
  lv_example_flex_1 ();
  // lv_example_flex_2 ();
  // lv_example_flex_3 ();
  // lv_example_flex_4 ();
  // lv_example_flex_5 ();
  // lv_example_flex_6 ();
}

void local_loop (void) {
}


/**
 * A simple row and a column layout with flexbox
 */
void lv_example_flex_1 (void) {
  /*Create a container with ROW flex direction*/
  lv_obj_t *cont_row = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (cont_row, 300, 75);
  lv_obj_align (cont_row, LV_ALIGN_TOP_MID, 0, 5);
  lv_obj_set_flex_flow (cont_row, LV_FLEX_FLOW_ROW);

  /*Create a container with COLUMN flex direction*/
  lv_obj_t *cont_col = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (cont_col, 200, 150);
  lv_obj_align_to (cont_col, cont_row, LV_ALIGN_OUT_BOTTOM_MID, 0, 5);
  lv_obj_set_flex_flow (cont_col, LV_FLEX_FLOW_COLUMN);

  uint32_t i;
  for (i = 0; i < 10; i++) {
    lv_obj_t *obj;
    lv_obj_t *label;

    /*Add items to the row*/
    obj = lv_btn_create (cont_row);
    lv_obj_set_size (obj, 100, LV_PCT (100));

    label = lv_label_create (obj);
    lv_label_set_text_fmt (label, "Item: %"LV_PRIu32"", i);
    lv_obj_center (label);

    /*Add items to the column*/
    obj = lv_btn_create (cont_col);
    lv_obj_set_size (obj, LV_PCT (100), LV_SIZE_CONTENT);

    label = lv_label_create (obj);
    lv_label_set_text_fmt (label, "Item: %"LV_PRIu32, i);
    lv_obj_center (label);
  }
}

/**
 * Arrange items in rows with wrap and place the items to get even space around them.
 */
void lv_example_flex_2 (void) {
  static lv_style_t style;
  lv_style_init (&style);
  lv_style_set_flex_flow (&style, LV_FLEX_FLOW_ROW_WRAP);
  lv_style_set_flex_main_place (&style, LV_FLEX_ALIGN_SPACE_EVENLY);
  lv_style_set_layout (&style, LV_LAYOUT_FLEX);

  lv_obj_t *cont = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (cont, 300, 220);
  lv_obj_center (cont);
  lv_obj_add_style (cont, &style, 0);

  uint32_t i;
  for (i = 0; i < 8; i++) {
    lv_obj_t *obj = lv_obj_create (cont);
    lv_obj_set_size (obj, 70, LV_SIZE_CONTENT);
    lv_obj_add_flag (obj, LV_OBJ_FLAG_CHECKABLE);

    lv_obj_t *label = lv_label_create (obj);
    lv_label_set_text_fmt (label, "%"LV_PRIu32, i);
    lv_obj_center (label);
  }
}

/**
 * Demonstrate flex grow.
 */
void lv_example_flex_3 (void) {
  lv_obj_t *cont = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (cont, 300, 220);
  lv_obj_center (cont);
  lv_obj_set_flex_flow (cont, LV_FLEX_FLOW_ROW);

  lv_obj_t *obj;
  obj = lv_obj_create (cont);
  lv_obj_set_size (obj, 40, 40);           /*Fix size*/

  obj = lv_obj_create (cont);
  lv_obj_set_height (obj, 40);
  lv_obj_set_flex_grow (obj, 1);           /*1 portion from the free space*/

  obj = lv_obj_create (cont);
  lv_obj_set_height (obj, 40);
  lv_obj_set_flex_grow (obj, 2);           /*2 portion from the free space*/

  obj = lv_obj_create (cont);
  lv_obj_set_size (obj, 40, 40);           /*Fix size. It is flushed to the right by the "grow" items*/
}


/**
 * Reverse the order of flex items
 */
void lv_example_flex_4 (void) {

  lv_obj_t *cont = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (cont, 300, 220);
  lv_obj_center (cont);
  lv_obj_set_flex_flow (cont, LV_FLEX_FLOW_COLUMN_REVERSE);

  uint32_t i;
  for (i = 0; i < 6; i++) {
    lv_obj_t *obj = lv_obj_create (cont);
    lv_obj_set_size (obj, 100, 50);

    lv_obj_t *label = lv_label_create (obj);
    lv_label_set_text_fmt (label, "Item: %"LV_PRIu32, i);
    lv_obj_center (label);
  }
}

static void row_gap_anim (void *obj, int32_t v) {
  lv_obj_set_style_pad_row ((_lv_obj_t *) obj, v, 0);
}

static void column_gap_anim (void *obj, int32_t v) {
  lv_obj_set_style_pad_column ((_lv_obj_t *) obj, v, 0);
}

/**
 * Demonstrate the effect of column and row gap style properties
 */
void lv_example_flex_5 (void) {
  lv_obj_t *cont = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (cont, 300, 220);
  lv_obj_center (cont);
  lv_obj_set_flex_flow (cont, LV_FLEX_FLOW_ROW_WRAP);

  uint32_t i;
  for (i = 0; i < 9; i++) {
    lv_obj_t *obj = lv_obj_create (cont);
    lv_obj_set_size (obj, 70, LV_SIZE_CONTENT);

    lv_obj_t *label = lv_label_create (obj);
    lv_label_set_text_fmt (label, "%"LV_PRIu32, i);
    lv_obj_center (label);
  }

  lv_anim_t a;
  lv_anim_init (&a);
  lv_anim_set_var (&a, cont);
  lv_anim_set_values (&a, 0, 10);
  lv_anim_set_repeat_count (&a, LV_ANIM_REPEAT_INFINITE);

  lv_anim_set_exec_cb (&a, row_gap_anim);
  lv_anim_set_time (&a, 500);
  lv_anim_set_playback_time (&a, 500);
  lv_anim_start (&a);

  lv_anim_set_exec_cb (&a, column_gap_anim);
  lv_anim_set_time (&a, 3000);
  lv_anim_set_playback_time (&a, 3000);
  lv_anim_start (&a);
}

/**
 * RTL base direction changes order of the items.
 * Also demonstrate how horizontal scrolling works with RTL.
 */
void lv_example_flex_6 (void) {
  lv_obj_t *cont = lv_obj_create (lv_scr_act ());
  lv_obj_set_style_base_dir (cont, LV_BASE_DIR_RTL, 0);
  lv_obj_set_size (cont, 300, 220);
  lv_obj_center (cont);
  lv_obj_set_flex_flow (cont, LV_FLEX_FLOW_ROW_WRAP);

  uint32_t i;
  for (i = 0; i < 20; i++) {
    lv_obj_t *obj = lv_obj_create (cont);
    lv_obj_set_size (obj, 70, LV_SIZE_CONTENT);

    lv_obj_t *label = lv_label_create (obj);
    lv_label_set_text_fmt (label, "%"LV_PRIu32, i);
    lv_obj_center (label);
  }
}
