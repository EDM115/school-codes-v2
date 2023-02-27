/*
  https://docs.lvgl.io/master/widgets/meter.html
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
  lv_example_meter_1 ();
  // lv_example_meter_2 ();
  // lv_example_meter_3 ();
  // lv_example_meter_4 ();
}

void local_loop (void) {
}


static lv_obj_t *meter1;

static void set_value1 (void *indic, int32_t v) {
  lv_meter_set_indicator_value (meter1, (lv_meter_indicator_t *) indic, v);
}

/**
 * A simple meter
 */
void lv_example_meter_1 (void) {
  meter1 = lv_meter_create (lv_scr_act ());
  lv_obj_center (meter1);
  lv_obj_set_size (meter1, 200, 200);

  /*Add a scale first*/
  lv_meter_scale_t *scale = lv_meter_add_scale (meter1);
  lv_meter_set_scale_ticks (meter1, scale, 41, 2, 10, lv_palette_main (LV_PALETTE_GREY));
  lv_meter_set_scale_major_ticks (meter1, scale, 8, 4, 15, lv_color_black (), 10);

  lv_meter_indicator_t *indic;

  /*Add a blue arc to the start*/
  indic = lv_meter_add_arc (meter1, scale, 3, lv_palette_main (LV_PALETTE_BLUE), 0);
  lv_meter_set_indicator_start_value (meter1, indic, 0);
  lv_meter_set_indicator_end_value (meter1, indic, 20);

  /*Make the tick lines blue at the start of the scale*/
  indic = lv_meter_add_scale_lines (meter1, scale, lv_palette_main (LV_PALETTE_BLUE), lv_palette_main (LV_PALETTE_BLUE),
				    false, 0);
  lv_meter_set_indicator_start_value (meter1, indic, 0);
  lv_meter_set_indicator_end_value (meter1, indic, 20);

  /*Add a red arc to the end*/
  indic = lv_meter_add_arc (meter1, scale, 3, lv_palette_main (LV_PALETTE_RED), 0);
  lv_meter_set_indicator_start_value (meter1, indic, 80);
  lv_meter_set_indicator_end_value (meter1, indic, 100);

  /*Make the tick lines red at the end of the scale*/
  indic = lv_meter_add_scale_lines (meter1, scale, lv_palette_main (LV_PALETTE_RED), lv_palette_main (LV_PALETTE_RED), false,
				    0);
  lv_meter_set_indicator_start_value (meter1, indic, 80);
  lv_meter_set_indicator_end_value (meter1, indic, 100);

  /*Add a needle line indicator*/
  indic = lv_meter_add_needle_line (meter1, scale, 4, lv_palette_main (LV_PALETTE_GREY), -10);

  /*Create an animation to set the value*/
  lv_anim_t a;
  lv_anim_init (&a);
  lv_anim_set_exec_cb (&a, set_value1);
  lv_anim_set_var (&a, indic);
  lv_anim_set_values (&a, 0, 100);
  lv_anim_set_time (&a, 2000);
  lv_anim_set_repeat_delay (&a, 100);
  lv_anim_set_playback_time (&a, 500);
  lv_anim_set_playback_delay (&a, 100);
  lv_anim_set_repeat_count (&a, LV_ANIM_REPEAT_INFINITE);
  lv_anim_start (&a);
}

static lv_obj_t *meter2;

static void set_value2 (void *indic, int32_t v) {
  lv_meter_set_indicator_end_value (meter2, (lv_meter_indicator_t *) indic, v);
}


/**
 * A meter with multiple arcs
 */
void lv_example_meter_2 (void) {
  meter2 = lv_meter_create (lv_scr_act ());
  lv_obj_center (meter2);
  lv_obj_set_size (meter2, 220, 220);

  /*Remove the circle from the middle*/
  lv_obj_remove_style (meter2, NULL, LV_PART_INDICATOR);

  /*Add a scale first*/
  lv_meter_scale_t *scale = lv_meter_add_scale (meter2);
  lv_meter_set_scale_ticks (meter2, scale, 11, 2, 10, lv_palette_main (LV_PALETTE_GREY));
  lv_meter_set_scale_major_ticks (meter2, scale, 1, 2, 30, lv_color_hex3 (0xeee), 15);
  lv_meter_set_scale_range (meter2, scale, 0, 100, 270, 90);

  /*Add a three arc indicator*/
  lv_meter_indicator_t *indic1 = lv_meter_add_arc (meter2, scale, 10, lv_palette_main (LV_PALETTE_RED), 0);
  lv_meter_indicator_t *indic2 = lv_meter_add_arc (meter2, scale, 10, lv_palette_main (LV_PALETTE_GREEN), -10);
  lv_meter_indicator_t *indic3 = lv_meter_add_arc (meter2, scale, 10, lv_palette_main (LV_PALETTE_BLUE), -20);

  /*Create an animation to set the value*/
  lv_anim_t a;
  lv_anim_init (&a);
  lv_anim_set_exec_cb (&a, set_value2);
  lv_anim_set_values (&a, 0, 100);
  lv_anim_set_repeat_delay (&a, 100);
  lv_anim_set_playback_delay (&a, 100);
  lv_anim_set_repeat_count (&a, LV_ANIM_REPEAT_INFINITE);

  lv_anim_set_time (&a, 2000);
  lv_anim_set_playback_time (&a, 500);
  lv_anim_set_var (&a, indic1);
  lv_anim_start (&a);

  lv_anim_set_time (&a, 1000);
  lv_anim_set_playback_time (&a, 1000);
  lv_anim_set_var (&a, indic2);
  lv_anim_start (&a);

  lv_anim_set_time (&a, 1000);
  lv_anim_set_playback_time (&a, 2000);
  lv_anim_set_var (&a, indic3);
  lv_anim_start (&a);
}

static lv_obj_t *meter3;

static void set_value3 (void *indic, int32_t v) {
  lv_meter_set_indicator_end_value (meter3, (lv_meter_indicator_t *) indic, v);
}

static void tick_label_event (lv_event_t *e) {
  lv_obj_draw_part_dsc_t * draw_part_dsc = lv_event_get_draw_part_dsc (e);

  /*Be sure it's drawing meter related parts*/
  if (draw_part_dsc->class_p != &lv_meter_class) return;

  /*Be sure it's drawing the ticks*/
  if (draw_part_dsc->type != LV_METER_DRAW_PART_TICK) return;

  /*Be sure it's a major ticks*/
  if (draw_part_dsc->id % 5) return;

  /*The order of numbers on the clock is tricky: 12, 1, 2, 3...*/
  if (draw_part_dsc->id == 0)
    strncpy (draw_part_dsc->text, "12", 4);
  else
    lv_snprintf (draw_part_dsc->text, 4, "%d", draw_part_dsc->id / 5);
}

/**
 * A clock from a meter
 */
void lv_example_meter_3 (void) {
  meter3 = lv_meter_create (lv_scr_act ());
  lv_obj_set_size (meter3, 220, 220);
  lv_obj_center (meter3);

  /*Create a scale for the minutes*/
  /*61 ticks in a 360 degrees range (the last and the first line overlaps)*/
  lv_meter_scale_t *scale = lv_meter_add_scale (meter3);
  lv_meter_set_scale_ticks (meter3, scale, 60, 1, 10, lv_palette_main (LV_PALETTE_GREY));
  lv_meter_set_scale_major_ticks (meter3, scale, 5, 2, 20, lv_color_black (), 10);
  lv_meter_set_scale_range (meter3, scale, 0, 59, 354, 270);

  LV_IMG_DECLARE (img_hand);

  /*Add a the hands from images*/
  lv_meter_indicator_t *indic_min = lv_meter_add_needle_img (meter3, scale, &img_hand, 5, 5);
  lv_meter_indicator_t *indic_hour = lv_meter_add_needle_img (meter3, scale, &img_hand, 5, 5);

  lv_obj_add_event_cb (meter3, tick_label_event, LV_EVENT_DRAW_PART_BEGIN, NULL);

  /*Create an animation to set the value*/
  lv_anim_t a;
  lv_anim_init (&a);
  lv_anim_set_exec_cb (&a, set_value3);
  lv_anim_set_values (&a, 0, 59);
  lv_anim_set_repeat_count (&a, LV_ANIM_REPEAT_INFINITE);
  lv_anim_set_time (&a, 5000);     /*2 sec for 1 turn of the minute hand (1 hour)*/
  lv_anim_set_var (&a, indic_min);
  lv_anim_start (&a);

  lv_anim_set_var (&a, indic_hour);
  lv_anim_set_time (&a, 240000);    /*24 sec for 1 turn of the hour hand*/
  lv_anim_set_values (&a, 0, 59);
  lv_anim_start (&a);
}

/**
 * Create a pie chart
 */
void lv_example_meter_4 (void) {
  lv_obj_t *meter = lv_meter_create (lv_scr_act ());

  /*Remove the background and the circle from the middle*/
  lv_obj_remove_style (meter, NULL, LV_PART_MAIN);
  lv_obj_remove_style (meter, NULL, LV_PART_INDICATOR);

  lv_obj_set_size (meter, 200, 200);
  lv_obj_center (meter);

  /*Add a scale first with no ticks.*/
  lv_meter_scale_t *scale = lv_meter_add_scale (meter);
  lv_meter_set_scale_ticks (meter, scale, 0, 0, 0, lv_color_black ());
  lv_meter_set_scale_range (meter, scale, 0, 100, 360, 0);

  /*Add a three arc indicator*/
  lv_coord_t indic_w = 100;
  lv_meter_indicator_t *indic1 = lv_meter_add_arc (meter, scale, indic_w, lv_palette_main (LV_PALETTE_ORANGE), 0);
  lv_meter_set_indicator_start_value (meter, indic1, 0);
  lv_meter_set_indicator_end_value (meter, indic1, 40);

  lv_meter_indicator_t *indic2 = lv_meter_add_arc (meter, scale, indic_w, lv_palette_main (LV_PALETTE_YELLOW), 0);
  lv_meter_set_indicator_start_value (meter, indic2, 40);  /*Start from the previous*/
  lv_meter_set_indicator_end_value (meter, indic2, 80);

  lv_meter_indicator_t *indic3 = lv_meter_add_arc (meter, scale, indic_w, lv_palette_main (LV_PALETTE_DEEP_ORANGE), 0);
  lv_meter_set_indicator_start_value (meter, indic3, 80);  /*Start from the previous*/
  lv_meter_set_indicator_end_value (meter, indic3, 100);
}
