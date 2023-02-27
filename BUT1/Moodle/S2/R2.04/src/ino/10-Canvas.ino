/*
  https://docs.lvgl.io/master/widgets/canvas.html
*/

// error: 'lv_font_montserrat_18' was not declared in this scope
// => in lv_conf.h set #define LV_FONT_MONTSERRAT_18 1 (line 358)

// 200x150 => overflow
#define CANVAS_WIDTH_1  100    // 200
#define CANVAS_HEIGHT_1  100   // 150

#define CANVAS_WIDTH  50
#define CANVAS_HEIGHT  50


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
  lv_example_canvas_1 ();
  // lv_example_canvas_2 ();
  // lv_example_canvas_3 ();
  // lv_example_canvas_4 ();
  // lv_example_canvas_5 ();
  // lv_example_canvas_6 ();
  // lv_example_canvas_7 ();
}

void local_loop (void) {
}


void lv_example_canvas_1 (void) {
  lv_draw_rect_dsc_t rect_dsc;
  lv_draw_rect_dsc_init (&rect_dsc);
  rect_dsc.radius = 10;
  rect_dsc.bg_opa = LV_OPA_COVER;
  rect_dsc.bg_grad.dir = LV_GRAD_DIR_HOR;
  rect_dsc.bg_grad.stops[0].color = lv_palette_main (LV_PALETTE_RED);
  rect_dsc.bg_grad.stops[1].color = lv_palette_main (LV_PALETTE_BLUE);
  rect_dsc.border_width = 2;
  rect_dsc.border_opa = LV_OPA_90;
  rect_dsc.border_color = lv_color_white ();
  rect_dsc.shadow_width = 5;
  rect_dsc.shadow_ofs_x = 5;
  rect_dsc.shadow_ofs_y = 5;

  lv_draw_label_dsc_t label_dsc;
  lv_draw_label_dsc_init (&label_dsc);
  label_dsc.color = lv_palette_main (LV_PALETTE_ORANGE);

  static uint8_t cbuf[LV_CANVAS_BUF_SIZE_TRUE_COLOR (CANVAS_WIDTH_1, CANVAS_HEIGHT_1)];

  lv_obj_t *canvas = lv_canvas_create (lv_scr_act ());
  lv_canvas_set_buffer (canvas, cbuf, CANVAS_WIDTH_1, CANVAS_HEIGHT_1, LV_IMG_CF_TRUE_COLOR);
  lv_obj_center (canvas);
  lv_canvas_fill_bg (canvas, lv_palette_lighten (LV_PALETTE_GREY, 3), LV_OPA_COVER);

  lv_canvas_draw_rect (canvas, 70, 60, 100, 70, &rect_dsc);

  lv_canvas_draw_text (canvas, 40, 20, 100, &label_dsc, "Some text on text canvas");

  /*Test the rotation. It requires another buffer where the original image is stored.
   *So copy the current image to buffer and rotate it to the canvas*/
  static uint8_t cbuf_tmp[LV_CANVAS_BUF_SIZE_TRUE_COLOR (CANVAS_WIDTH_1, CANVAS_HEIGHT_1)];
  memcpy (cbuf_tmp, cbuf, sizeof (cbuf_tmp));
  lv_img_dsc_t img;
  img.data = (const uint8_t *) cbuf_tmp;
  img.header.cf = LV_IMG_CF_TRUE_COLOR;
  img.header.w = CANVAS_WIDTH_1;
  img.header.h = CANVAS_HEIGHT_1;

  lv_canvas_fill_bg (canvas, lv_palette_lighten (LV_PALETTE_GREY, 3), LV_OPA_COVER);
  lv_canvas_transform (canvas, &img, 120, LV_IMG_ZOOM_NONE, 0, 0, CANVAS_WIDTH_1 / 2, CANVAS_HEIGHT_1 / 2, true);
}

/**
 * Create a transparent canvas with Chroma keying and indexed color format (palette).
 */
void lv_example_canvas_2 (void) {
  /*Create a button to better see the transparency*/
  lv_btn_create (lv_scr_act ());

  /*Create a buffer for the canvas*/
  static uint8_t cbuf[LV_CANVAS_BUF_SIZE_INDEXED_1BIT (CANVAS_WIDTH, CANVAS_HEIGHT)];

  /*Create a canvas and initialize its palette*/
  lv_obj_t *canvas = lv_canvas_create (lv_scr_act ());
  lv_canvas_set_buffer (canvas, cbuf, CANVAS_WIDTH, CANVAS_HEIGHT, LV_IMG_CF_INDEXED_1BIT);
  lv_canvas_set_palette (canvas, 0, LV_COLOR_CHROMA_KEY);
  lv_canvas_set_palette (canvas, 1, lv_palette_main (LV_PALETTE_RED));

  /*Create colors with the indices of the palette*/
  lv_color_t c0;
  lv_color_t c1;

  c0.full = 0;
  c1.full = 1;

  /*Red background (There is no dedicated alpha channel in indexed images so LV_OPA_COVER is ignored)*/
  lv_canvas_fill_bg (canvas, c1, LV_OPA_COVER);

  /*Create hole on the canvas*/
  uint32_t x;
  uint32_t y;
  for (y = 10; y < 30; y++) {
    for (x = 5; x < 20; x++) {
      lv_canvas_set_px_color (canvas, x, y, c0);
    }
  }
}

/**
 * Draw a rectangle to the canvas
 */
void lv_example_canvas_3 (void) {
  /*Create a buffer for the canvas*/
  static uint8_t cbuf[LV_CANVAS_BUF_SIZE_TRUE_COLOR (CANVAS_WIDTH, CANVAS_HEIGHT)];

  /*Create a canvas and initialize its palette*/
  lv_obj_t *canvas = lv_canvas_create (lv_scr_act ());
  lv_canvas_set_buffer (canvas, cbuf, CANVAS_WIDTH, CANVAS_HEIGHT, LV_IMG_CF_TRUE_COLOR);
  lv_canvas_fill_bg (canvas, lv_color_hex3 (0xccc), LV_OPA_COVER);
  lv_obj_center (canvas);

  lv_draw_rect_dsc_t dsc;
  lv_draw_rect_dsc_init (&dsc);
  dsc.bg_color = lv_palette_main (LV_PALETTE_RED);
  dsc.border_color = lv_palette_main (LV_PALETTE_BLUE);
  dsc.border_width = 3;
  dsc.outline_color = lv_palette_main (LV_PALETTE_GREEN);
  dsc.outline_width = 2;
  dsc.outline_pad = 2;
  dsc.outline_opa = LV_OPA_50;
  dsc.radius = 5;
  dsc.border_width = 3;
  lv_canvas_draw_rect (canvas, 10, 10, 30, 20, &dsc);
}

/**
 * Draw a text to the canvas
 */
void lv_example_canvas_4 (void) {
  /*Create a buffer for the canvas*/
  static uint8_t cbuf[LV_CANVAS_BUF_SIZE_TRUE_COLOR (CANVAS_WIDTH, CANVAS_HEIGHT)];

  /*Create a canvas and initialize its palette*/
  lv_obj_t *canvas = lv_canvas_create (lv_scr_act ());
  lv_canvas_set_buffer (canvas, cbuf, CANVAS_WIDTH, CANVAS_HEIGHT, LV_IMG_CF_TRUE_COLOR);
  lv_canvas_fill_bg (canvas, lv_color_hex3 (0xccc), LV_OPA_COVER);
  lv_obj_center (canvas);

  lv_draw_label_dsc_t dsc;
  lv_draw_label_dsc_init (&dsc);
  dsc.color = lv_palette_main (LV_PALETTE_RED);
  dsc.font = &lv_font_montserrat_18;
  dsc.decor = LV_TEXT_DECOR_UNDERLINE;

  lv_canvas_draw_text (canvas, 10, 10, 30, &dsc, "Hello");
}

/**
 * Draw an arc to the canvas
 */
void lv_example_canvas_5 (void) {
  /*Create a buffer for the canvas*/
  static uint8_t cbuf[LV_CANVAS_BUF_SIZE_TRUE_COLOR (CANVAS_WIDTH, CANVAS_HEIGHT)];

  /*Create a canvas and initialize its palette*/
  lv_obj_t *canvas = lv_canvas_create (lv_scr_act ());
  lv_canvas_set_buffer (canvas, cbuf, CANVAS_WIDTH, CANVAS_HEIGHT, LV_IMG_CF_TRUE_COLOR);
  lv_canvas_fill_bg (canvas, lv_color_hex3 (0xccc), LV_OPA_COVER);
  lv_obj_center (canvas);

  lv_draw_arc_dsc_t dsc;
  lv_draw_arc_dsc_init (&dsc);
  dsc.color = lv_palette_main (LV_PALETTE_RED);
  dsc.width = 5;

  lv_canvas_draw_arc (canvas, 25, 25, 15, 0, 220, &dsc);
}

/**
 * Draw an image to the canvas
 */
void lv_example_canvas_6 (void) {
  /*Create a buffer for the canvas*/
  static uint8_t cbuf[LV_CANVAS_BUF_SIZE_TRUE_COLOR (CANVAS_WIDTH, CANVAS_HEIGHT)];

  /*Create a canvas and initialize its palette*/
  lv_obj_t *canvas = lv_canvas_create (lv_scr_act ());
  lv_canvas_set_buffer (canvas, cbuf, CANVAS_WIDTH, CANVAS_HEIGHT, LV_IMG_CF_TRUE_COLOR);
  lv_canvas_fill_bg (canvas, lv_color_hex3 (0xccc), LV_OPA_COVER);
  lv_obj_center (canvas);

  lv_draw_img_dsc_t dsc;
  lv_draw_img_dsc_init (&dsc);

  LV_IMG_DECLARE (img_star);
  lv_canvas_draw_img (canvas, 5, 5, &img_star, &dsc);
}

/**
 * Draw a line to the canvas
 */
void lv_example_canvas_7 (void) {
  /*Create a buffer for the canvas*/
  static uint8_t cbuf[LV_CANVAS_BUF_SIZE_TRUE_COLOR (CANVAS_WIDTH, CANVAS_HEIGHT)];

  /*Create a canvas and initialize its palette*/
  lv_obj_t *canvas = lv_canvas_create (lv_scr_act ());
  lv_canvas_set_buffer (canvas, cbuf, CANVAS_WIDTH, CANVAS_HEIGHT, LV_IMG_CF_TRUE_COLOR);
  lv_canvas_fill_bg (canvas, lv_color_hex3 (0xccc), LV_OPA_COVER);
  lv_obj_center (canvas);

  lv_draw_line_dsc_t dsc;
  lv_draw_line_dsc_init (&dsc);
  dsc.color = lv_palette_main (LV_PALETTE_RED);
  dsc.width = 4;
  dsc.round_end = 1;
  dsc.round_start = 1;

  lv_point_t p[] = {{15, 15}, {35, 10}, {10, 40}};
  lv_canvas_draw_line (canvas, p, 3, &dsc);
}
