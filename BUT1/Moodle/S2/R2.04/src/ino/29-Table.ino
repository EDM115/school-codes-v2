/*
  https://docs.lvgl.io/master/widgets/switch.html
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
  lv_example_table_1 ();
  //lv_example_table_2 ();
}

void local_loop (void) {
}


static void draw_part_event_cb (lv_event_t *e) {
  lv_obj_t *obj = lv_event_get_target (e);
  lv_obj_draw_part_dsc_t *dsc = lv_event_get_draw_part_dsc (e);
  /*If the cells are drawn...*/
  if (dsc->part == LV_PART_ITEMS) {
    uint32_t row = dsc->id /  lv_table_get_col_cnt (obj);
    uint32_t col = dsc->id - row * lv_table_get_col_cnt (obj);

    /*Make the texts in the first cell center aligned*/
    if (row == 0) {
      dsc->label_dsc->align = LV_TEXT_ALIGN_CENTER;
      dsc->rect_dsc->bg_color = lv_color_mix (lv_palette_main (LV_PALETTE_BLUE), dsc->rect_dsc->bg_color, LV_OPA_20);
      dsc->rect_dsc->bg_opa = LV_OPA_COVER;
    }
    /*In the first column align the texts to the right*/
    else if (col == 0) {
      dsc->label_dsc->align = LV_TEXT_ALIGN_RIGHT;
    }

    /*MAke every 2nd row grayish*/
    if ( (row != 0 && row % 2) == 0) {
      dsc->rect_dsc->bg_color = lv_color_mix (lv_palette_main (LV_PALETTE_GREY), dsc->rect_dsc->bg_color, LV_OPA_10);
      dsc->rect_dsc->bg_opa = LV_OPA_COVER;
    }
  }
}


void lv_example_table_1 (void) {
  lv_obj_t *table = lv_table_create (lv_scr_act ());

  /*Fill the first column*/
  lv_table_set_cell_value (table, 0, 0, "Name");
  lv_table_set_cell_value (table, 1, 0, "Apple");
  lv_table_set_cell_value (table, 2, 0, "Banana");
  lv_table_set_cell_value (table, 3, 0, "Lemon");
  lv_table_set_cell_value (table, 4, 0, "Grape");
  lv_table_set_cell_value (table, 5, 0, "Melon");
  lv_table_set_cell_value (table, 6, 0, "Peach");
  lv_table_set_cell_value (table, 7, 0, "Nuts");

  /*Fill the second column*/
  lv_table_set_cell_value (table, 0, 1, "Price");
  lv_table_set_cell_value (table, 1, 1, "$7");
  lv_table_set_cell_value (table, 2, 1, "$4");
  lv_table_set_cell_value (table, 3, 1, "$6");
  lv_table_set_cell_value (table, 4, 1, "$2");
  lv_table_set_cell_value (table, 5, 1, "$5");
  lv_table_set_cell_value (table, 6, 1, "$1");
  lv_table_set_cell_value (table, 7, 1, "$9");

  /*Set a smaller height to the table. It'll make it scrollable*/
  lv_obj_set_height (table, 200);
  lv_obj_center (table);

  /*Add an event callback to to apply some custom drawing*/
  lv_obj_add_event_cb (table, draw_part_event_cb, LV_EVENT_DRAW_PART_BEGIN, NULL);
}

#define ITEM_CNT 200

static void draw_event_cb (lv_event_t *e) {
  lv_obj_t *obj = lv_event_get_target (e);
  lv_obj_draw_part_dsc_t *dsc = lv_event_get_draw_part_dsc (e);
  /*If the cells are drawn...*/
  if (dsc->part == LV_PART_ITEMS) {
    bool chk = lv_table_has_cell_ctrl (obj, dsc->id, 0, LV_TABLE_CELL_CTRL_CUSTOM_1);

    lv_draw_rect_dsc_t rect_dsc;
    lv_draw_rect_dsc_init (&rect_dsc);
    rect_dsc.bg_color = chk ? lv_theme_get_color_primary (obj) : lv_palette_lighten (LV_PALETTE_GREY, 2);
    rect_dsc.radius = LV_RADIUS_CIRCLE;

    lv_area_t sw_area;
    sw_area.x1 = dsc->draw_area->x2 - 50;
    sw_area.x2 = sw_area.x1 + 40;
    sw_area.y1 = dsc->draw_area->y1 + lv_area_get_height (dsc->draw_area) / 2 - 10;
    sw_area.y2 = sw_area.y1 + 20;
    lv_draw_rect (dsc->draw_ctx, &rect_dsc, &sw_area);

    rect_dsc.bg_color = lv_color_white ();
    if (chk) {
      sw_area.x2 -= 2;
      sw_area.x1 = sw_area.x2 - 16;
    }
    else {
      sw_area.x1 += 2;
      sw_area.x2 = sw_area.x1 + 16;
    }
    sw_area.y1 += 2;
    sw_area.y2 -= 2;
    lv_draw_rect (dsc->draw_ctx, &rect_dsc, &sw_area);
  }
}

static void change_event_cb (lv_event_t *e) {
  lv_obj_t *obj = lv_event_get_target (e);
  uint16_t col;
  uint16_t row;
  lv_table_get_selected_cell (obj, &row, &col);
  bool chk = lv_table_has_cell_ctrl (obj, row, 0, LV_TABLE_CELL_CTRL_CUSTOM_1);
  if (chk) lv_table_clear_cell_ctrl (obj, row, 0, LV_TABLE_CELL_CTRL_CUSTOM_1);
  else lv_table_add_cell_ctrl (obj, row, 0, LV_TABLE_CELL_CTRL_CUSTOM_1);
}


/**
 * A very light-weighted list created from table
 */
void lv_example_table_2 (void) {
  /*Measure memory usage*/
  lv_mem_monitor_t mon1;
  lv_mem_monitor (&mon1);

  uint32_t t = lv_tick_get ();

  lv_obj_t *table = lv_table_create (lv_scr_act ());

  /*Set a smaller height to the table. It'll make it scrollable*/
  lv_obj_set_size (table, LV_SIZE_CONTENT, 200);

  lv_table_set_col_width (table, 0, 150);
  lv_table_set_row_cnt (table, ITEM_CNT); /*Not required but avoids a lot of memory reallocation lv_table_set_set_value*/
  lv_table_set_col_cnt (table, 1);

  /*Don't make the cell pressed, we will draw something different in the event*/
  lv_obj_remove_style (table, NULL, LV_PART_ITEMS | LV_STATE_PRESSED);

  uint32_t i;
  for (i = 0; i < ITEM_CNT; i++) {
    lv_table_set_cell_value_fmt (table, i, 0, "Item %"LV_PRIu32, i + 1);
  }

  lv_obj_align (table, LV_ALIGN_CENTER, 0, -20);

  /*Add an event callback to to apply some custom drawing*/
  lv_obj_add_event_cb (table, draw_event_cb, LV_EVENT_DRAW_PART_END, NULL);
  lv_obj_add_event_cb (table, change_event_cb, LV_EVENT_VALUE_CHANGED, NULL);

  lv_mem_monitor_t mon2;
  lv_mem_monitor (&mon2);

  uint32_t mem_used = mon1.free_size - mon2.free_size;

  uint32_t elaps = lv_tick_elaps (t);

  lv_obj_t *label = lv_label_create (lv_scr_act ());
  lv_label_set_text_fmt (label, "%"LV_PRIu32" items were created in %"LV_PRIu32" ms\n"
			 "using %"LV_PRIu32" bytes of memory",
			 (uint32_t)ITEM_CNT, elaps, mem_used);

  lv_obj_align (label, LV_ALIGN_BOTTOM_MID, 0, -10);

}
