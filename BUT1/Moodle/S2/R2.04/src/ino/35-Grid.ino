/*
  https://docs.lvgl.io/master/layouts/grid.html
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
  lv_example_grid_1 ();
  lv_example_grid_2 ();
  lv_example_grid_3 ();
  lv_example_grid_4 ();
  lv_example_grid_5 ();
  lv_example_grid_6 ();
}

void local_loop (void) {
}


/**
 * A simple grid
 */
void lv_example_grid_1 (void) {
  static lv_coord_t col_dsc[] = {70, 70, 70, LV_GRID_TEMPLATE_LAST};
  static lv_coord_t row_dsc[] = {50, 50, 50, LV_GRID_TEMPLATE_LAST};

  /*Create a container with grid*/
  lv_obj_t *cont = lv_obj_create (lv_scr_act ());
  lv_obj_set_style_grid_column_dsc_array (cont, col_dsc, 0);
  lv_obj_set_style_grid_row_dsc_array (cont, row_dsc, 0);
  lv_obj_set_size (cont, 300, 220);
  lv_obj_center (cont);
  lv_obj_set_layout (cont, LV_LAYOUT_GRID);

  lv_obj_t *label;
  lv_obj_t *obj;

  uint32_t i;
  for (i = 0; i < 9; i++) {
    uint8_t col = i % 3;
    uint8_t row = i / 3;

    obj = lv_btn_create (cont);
    /*Stretch the cell horizontally and vertically too
     *Set span to 1 to make the cell 1 column/row sized*/
    lv_obj_set_grid_cell (obj, LV_GRID_ALIGN_STRETCH, col, 1,
			  LV_GRID_ALIGN_STRETCH, row, 1);

    label = lv_label_create (obj);
    lv_label_set_text_fmt (label, "c%d, r%d", col, row);
    lv_obj_center (label);
  }
}
/**
 * Demonstrate cell placement and span
 */
void lv_example_grid_2 (void) {
  static lv_coord_t col_dsc[] = {70, 70, 70, LV_GRID_TEMPLATE_LAST};
  static lv_coord_t row_dsc[] = {50, 50, 50, LV_GRID_TEMPLATE_LAST};

  /*Create a container with grid*/
  lv_obj_t *cont = lv_obj_create (lv_scr_act ());
  lv_obj_set_grid_dsc_array (cont, col_dsc, row_dsc);
  lv_obj_set_size (cont, 300, 220);
  lv_obj_center (cont);

  lv_obj_t *label;
  lv_obj_t *obj;

  /*Cell to 0;0 and align to to the start (left/top) horizontally and vertically too*/
  obj = lv_obj_create (cont);
  lv_obj_set_size (obj, LV_SIZE_CONTENT, LV_SIZE_CONTENT);
  lv_obj_set_grid_cell (obj, LV_GRID_ALIGN_START, 0, 1,
			LV_GRID_ALIGN_START, 0, 1);
  label = lv_label_create (obj);
  lv_label_set_text (label, "c0, r0");

  /*Cell to 1;0 and align to to the start (left) horizontally and center vertically too*/
  obj = lv_obj_create (cont);
  lv_obj_set_size (obj, LV_SIZE_CONTENT, LV_SIZE_CONTENT);
  lv_obj_set_grid_cell (obj, LV_GRID_ALIGN_START, 1, 1,
			LV_GRID_ALIGN_CENTER, 0, 1);
  label = lv_label_create (obj);
  lv_label_set_text (label, "c1, r0");

  /*Cell to 2;0 and align to to the start (left) horizontally and end (bottom) vertically too*/
  obj = lv_obj_create (cont);
  lv_obj_set_size (obj, LV_SIZE_CONTENT, LV_SIZE_CONTENT);
  lv_obj_set_grid_cell (obj, LV_GRID_ALIGN_START, 2, 1,
			LV_GRID_ALIGN_END, 0, 1);
  label = lv_label_create (obj);
  lv_label_set_text (label, "c2, r0");

  /*Cell to 1;1 but 2 column wide (span = 2).Set width and height to stretched.*/
  obj = lv_obj_create (cont);
  lv_obj_set_size (obj, LV_SIZE_CONTENT, LV_SIZE_CONTENT);
  lv_obj_set_grid_cell (obj, LV_GRID_ALIGN_STRETCH, 1, 2,
			LV_GRID_ALIGN_STRETCH, 1, 1);
  label = lv_label_create (obj);
  lv_label_set_text (label, "c1-2, r1");

  /*Cell to 0;1 but 2 rows tall (span = 2).Set width and height to stretched.*/
  obj = lv_obj_create (cont);
  lv_obj_set_size (obj, LV_SIZE_CONTENT, LV_SIZE_CONTENT);
  lv_obj_set_grid_cell (obj, LV_GRID_ALIGN_STRETCH, 0, 1,
			LV_GRID_ALIGN_STRETCH, 1, 2);
  label = lv_label_create (obj);
  lv_label_set_text (label, "c0\nr1-2");
}

/**
 * Demonstrate grid's "free unit"
 */
void lv_example_grid_3 (void) {
  /*Column 1: fix width 60 px
   *Column 2: 1 unit from the remaining free space
   *Column 3: 2 unit from the remaining free space*/
  static lv_coord_t col_dsc[] = {60, LV_GRID_FR (1), LV_GRID_FR (2), LV_GRID_TEMPLATE_LAST};

  /*Row 1: fix width 50 px
   *Row 2: 1 unit from the remaining free space
   *Row 3: fix width 50 px*/
  static lv_coord_t row_dsc[] = {50, LV_GRID_FR (1), 50, LV_GRID_TEMPLATE_LAST};

  /*Create a container with grid*/
  lv_obj_t *cont = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (cont, 300, 220);
  lv_obj_center (cont);
  lv_obj_set_grid_dsc_array (cont, col_dsc, row_dsc);

  lv_obj_t *label;
  lv_obj_t *obj;
  uint32_t i;
  for (i = 0; i < 9; i++) {
    uint8_t col = i % 3;
    uint8_t row = i / 3;

    obj = lv_obj_create (cont);
    /*Stretch the cell horizontally and vertically too
     *Set span to 1 to make the cell 1 column/row sized*/
    lv_obj_set_grid_cell (obj, LV_GRID_ALIGN_STRETCH, col, 1,
			  LV_GRID_ALIGN_STRETCH, row, 1);

    label = lv_label_create (obj);
    lv_label_set_text_fmt (label, "%d,%d", col, row);
    lv_obj_center (label);
  }
}

/**
 * Demonstrate track placement
 */
void lv_example_grid_4 (void) {
  static lv_coord_t col_dsc[] = {60, 60, 60, LV_GRID_TEMPLATE_LAST};
  static lv_coord_t row_dsc[] = {45, 45, 45, LV_GRID_TEMPLATE_LAST};


  /*Add space between the columns and move the rows to the bottom (end)*/

  /*Create a container with grid*/
  lv_obj_t *cont = lv_obj_create (lv_scr_act ());
  lv_obj_set_grid_align (cont, LV_GRID_ALIGN_SPACE_BETWEEN, LV_GRID_ALIGN_END);
  lv_obj_set_grid_dsc_array (cont, col_dsc, row_dsc);
  lv_obj_set_size (cont, 300, 220);
  lv_obj_center (cont);

  lv_obj_t *label;
  lv_obj_t *obj;
  uint32_t i;
  for (i = 0; i < 9; i++) {
    uint8_t col = i % 3;
    uint8_t row = i / 3;

    obj = lv_obj_create (cont);
    /*Stretch the cell horizontally and vertically too
     *Set span to 1 to make the cell 1 column/row sized*/
    lv_obj_set_grid_cell (obj, LV_GRID_ALIGN_STRETCH, col, 1,
			  LV_GRID_ALIGN_STRETCH, row, 1);

    label = lv_label_create (obj);
    lv_label_set_text_fmt (label, "%d,%d", col, row);
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
 * Demonstrate column and row gap
 */
void lv_example_grid_5 (void) {
  /*60x60 cells*/
  static lv_coord_t col_dsc[] = {60, 60, 60, LV_GRID_TEMPLATE_LAST};
  static lv_coord_t row_dsc[] = {45, 45, 45, LV_GRID_TEMPLATE_LAST};

  /*Create a container with grid*/
  lv_obj_t *cont = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (cont, 300, 220);
  lv_obj_center (cont);
  lv_obj_set_grid_dsc_array (cont, col_dsc, row_dsc);

  lv_obj_t *label;
  lv_obj_t *obj;
  uint32_t i;
  for (i = 0; i < 9; i++) {
    uint8_t col = i % 3;
    uint8_t row = i / 3;

    obj = lv_obj_create (cont);
    lv_obj_set_grid_cell (obj, LV_GRID_ALIGN_STRETCH, col, 1,
			  LV_GRID_ALIGN_STRETCH, row, 1);
    label = lv_label_create (obj);
    lv_label_set_text_fmt (label, "%d,%d", col, row);
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
 * Demonstrate RTL direction on grid
 */
void lv_example_grid_6 (void) {
  static lv_coord_t col_dsc[] = {60, 60, 60, LV_GRID_TEMPLATE_LAST};
  static lv_coord_t row_dsc[] = {45, 45, 45, LV_GRID_TEMPLATE_LAST};

  /*Create a container with grid*/
  lv_obj_t *cont = lv_obj_create (lv_scr_act ());
  lv_obj_set_size (cont, 300, 220);
  lv_obj_center (cont);
  lv_obj_set_style_base_dir (cont, LV_BASE_DIR_RTL, 0);
  lv_obj_set_grid_dsc_array (cont, col_dsc, row_dsc);

  lv_obj_t *label;
  lv_obj_t *obj;
  uint32_t i;
  for (i = 0; i < 9; i++) {
    uint8_t col = i % 3;
    uint8_t row = i / 3;

    obj = lv_obj_create (cont);
    /*Stretch the cell horizontally and vertically too
     *Set span to 1 to make the cell 1 column/row sized*/
    lv_obj_set_grid_cell (obj, LV_GRID_ALIGN_STRETCH, col, 1,
			  LV_GRID_ALIGN_STRETCH, row, 1);

    label = lv_label_create (obj);
    lv_label_set_text_fmt (label, "%d,%d", col, row);
    lv_obj_center (label);
  }
}
