/*
  https://docs.lvgl.io/master/widgets/checkbox.html
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
  //lv_example_checkbox_1 ();
  lv_example_checkbox_2 ();
}

void local_loop (void) {
}


static void event_handler (lv_event_t * e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *obj = lv_event_get_target (e);
  LV_UNUSED (obj);
  if (code == LV_EVENT_VALUE_CHANGED) {
    const char *txt = lv_checkbox_get_text (obj);
    const char *state = lv_obj_get_state (obj) & LV_STATE_CHECKED ? "Checked" : "Unchecked";
    LV_UNUSED (txt);
    LV_UNUSED (state);
    LV_LOG_USER ("%s: %s", txt, state);
  }
}

void lv_example_checkbox_1 (void) {
  lv_obj_set_flex_flow (lv_scr_act (), LV_FLEX_FLOW_COLUMN);
  lv_obj_set_flex_align (lv_scr_act (), LV_FLEX_ALIGN_CENTER, LV_FLEX_ALIGN_START, LV_FLEX_ALIGN_CENTER);

  lv_obj_t *cb;
  cb = lv_checkbox_create (lv_scr_act ());
  lv_checkbox_set_text (cb, "Apple");
  lv_obj_add_event_cb (cb, event_handler, LV_EVENT_ALL, NULL);

  cb = lv_checkbox_create (lv_scr_act ());
  lv_checkbox_set_text (cb, "Banana");
  lv_obj_add_state (cb, LV_STATE_CHECKED);
  lv_obj_add_event_cb (cb, event_handler, LV_EVENT_ALL, NULL);

  cb = lv_checkbox_create (lv_scr_act ());
  lv_checkbox_set_text (cb, "Lemon");
  lv_obj_add_state (cb, LV_STATE_DISABLED);
  lv_obj_add_event_cb (cb, event_handler, LV_EVENT_ALL, NULL);

  cb = lv_checkbox_create (lv_scr_act ());
  lv_obj_add_state (cb, LV_STATE_CHECKED | LV_STATE_DISABLED);
  lv_checkbox_set_text (cb, "Melon\nand a new line");
  lv_obj_add_event_cb (cb, event_handler, LV_EVENT_ALL, NULL);

  lv_obj_update_layout (cb);
}

static lv_style_t style_radio;
static lv_style_t style_radio_chk;
static uint32_t active_index_1 = 0;
static uint32_t active_index_2 = 0;

static void radio_event_handler (lv_event_t *e) {
  uint32_t *active_id = (uint32_t *) lv_event_get_user_data (e);
  lv_obj_t *cont = lv_event_get_current_target (e);
  lv_obj_t *act_cb = lv_event_get_target (e);
  lv_obj_t *old_cb = lv_obj_get_child (cont, *active_id);

  /*Do nothing if the container was clicked*/
  if (act_cb == cont) return;

  lv_obj_clear_state (old_cb, LV_STATE_CHECKED);   /*Uncheck the previous radio button*/
  lv_obj_add_state (act_cb, LV_STATE_CHECKED);     /*Uncheck the current radio button*/

  *active_id = lv_obj_get_index (act_cb);

  LV_LOG_USER ("Selected radio buttons: %d, %d", (int)active_index_1, (int)active_index_2);
}

static void radiobutton_create (lv_obj_t *parent, const char *txt) {
  lv_obj_t *obj = lv_checkbox_create (parent);
  lv_checkbox_set_text (obj, txt);
  lv_obj_add_flag (obj, LV_OBJ_FLAG_EVENT_BUBBLE);
  lv_obj_add_style (obj, &style_radio, LV_PART_INDICATOR);
  lv_obj_add_style (obj, &style_radio_chk, LV_PART_INDICATOR | LV_STATE_CHECKED);
}

/**
 * Checkboxes as radio buttons
 */
void lv_example_checkbox_2 (void) {
  /* The idea is to enable `LV_OBJ_FLAG_EVENT_BUBBLE` on checkboxes and process the
   * `LV_EVENT_CLICKED` on the container.
   * A variable is passed as event user data where the index of the active
   * radiobutton is saved */

  lv_style_init (&style_radio);
  lv_style_set_radius (&style_radio, LV_RADIUS_CIRCLE);

  lv_style_init (&style_radio_chk);
  lv_style_set_bg_img_src (&style_radio_chk, NULL);

  uint32_t i;
  char buf[32];

  lv_obj_t *cont1 = lv_obj_create (lv_scr_act ());
  lv_obj_set_flex_flow (cont1, LV_FLEX_FLOW_COLUMN);
  lv_obj_set_size (cont1, lv_pct (40), lv_pct (80));
  lv_obj_add_event_cb (cont1, radio_event_handler, LV_EVENT_CLICKED, &active_index_1);

  for (i = 0; i < 5; i++) {
    lv_snprintf (buf, sizeof (buf), "A %d", (int)i + 1);
    radiobutton_create (cont1, buf);

  }
  /*Make the first checkbox checked*/
  lv_obj_add_state (lv_obj_get_child (cont1, 0), LV_STATE_CHECKED);

  lv_obj_t *cont2 = lv_obj_create (lv_scr_act ());
  lv_obj_set_flex_flow (cont2, LV_FLEX_FLOW_COLUMN);
  lv_obj_set_size (cont2, lv_pct (40), lv_pct (80));
  lv_obj_set_x (cont2, lv_pct (50));
  lv_obj_add_event_cb (cont2, radio_event_handler, LV_EVENT_CLICKED, &active_index_2);

  for (i = 0; i < 3; i++) {
    lv_snprintf (buf, sizeof (buf), "B %d", (int)i + 1);
    radiobutton_create (cont2, buf);
  }

  /*Make the first checkbox checked*/
  lv_obj_add_state (lv_obj_get_child (cont2, 0), LV_STATE_CHECKED);
}
