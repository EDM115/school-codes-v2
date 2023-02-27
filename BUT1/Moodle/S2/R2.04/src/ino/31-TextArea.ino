/*
  https://docs.lvgl.io/master/widgets/textarea.html
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
  // lv_example_textarea_1 ();
  lv_example_textarea_2 ();
  // lv_example_textarea_3 ();
}

void local_loop (void) {
}


static void textarea_event_handler (lv_event_t *e) {
  lv_obj_t *ta = lv_event_get_target (e);
  LV_UNUSED (ta);
  LV_LOG_USER ("Enter was pressed. The current text is: %s", lv_textarea_get_text (ta));
}

static void btnm_event_handler (lv_event_t *e) {
  lv_obj_t *obj = lv_event_get_target (e);
  lv_obj_t *ta = (lv_obj_t *) lv_event_get_user_data (e);
  const char *txt = lv_btnmatrix_get_btn_text (obj, lv_btnmatrix_get_selected_btn (obj));

  if (strcmp (txt, LV_SYMBOL_BACKSPACE) == 0) lv_textarea_del_char (ta);
  else if (strcmp (txt, LV_SYMBOL_NEW_LINE) == 0) lv_event_send (ta, LV_EVENT_READY, NULL);
  else lv_textarea_add_text (ta, txt);

}

void lv_example_textarea_1 (void) {
  lv_obj_t *ta = lv_textarea_create (lv_scr_act ());
  lv_textarea_set_one_line (ta, true);
  lv_obj_align (ta, LV_ALIGN_TOP_MID, 0, 10);
  lv_obj_add_event_cb (ta, textarea_event_handler, LV_EVENT_READY, ta);
  lv_obj_add_state (ta, LV_STATE_FOCUSED); /*To be sure the cursor is visible*/

  static const char *btnm_map[] = {"1", "2", "3", "\n",
    "4", "5", "6", "\n",
    "7", "8", "9", "\n",
    LV_SYMBOL_BACKSPACE, "0", LV_SYMBOL_NEW_LINE, ""
  };

  lv_obj_t *btnm = lv_btnmatrix_create (lv_scr_act ());
  lv_obj_set_size (btnm, 200, 150);
  lv_obj_align (btnm, LV_ALIGN_BOTTOM_MID, 0, -10);
  lv_obj_add_event_cb (btnm, btnm_event_handler, LV_EVENT_VALUE_CHANGED, ta);
  lv_obj_clear_flag (btnm, LV_OBJ_FLAG_CLICK_FOCUSABLE); /*To keep the text area focused on button clicks*/
  lv_btnmatrix_set_map (btnm, btnm_map);
}

static void ta_event_cb1 (lv_event_t *e);

static lv_obj_t *kb1;

void lv_example_textarea_2 (void) {
  /*Create the password box*/
  lv_obj_t *pwd_ta = lv_textarea_create (lv_scr_act ());
  lv_textarea_set_text (pwd_ta, "");
  lv_textarea_set_password_mode (pwd_ta, true);
  lv_textarea_set_one_line (pwd_ta, true);
  lv_obj_set_width (pwd_ta, lv_pct (40));
  lv_obj_set_pos (pwd_ta, 5, 20);
  lv_obj_add_event_cb (pwd_ta, ta_event_cb1, LV_EVENT_ALL, NULL);

  /*Create a label and position it above the text box*/
  lv_obj_t *pwd_label = lv_label_create (lv_scr_act ());
  lv_label_set_text (pwd_label, "Password:");
  lv_obj_align_to (pwd_label, pwd_ta, LV_ALIGN_OUT_TOP_LEFT, 0, 0);

  /*Create the one-line mode text area*/
  lv_obj_t *text_ta = lv_textarea_create (lv_scr_act ());
  lv_textarea_set_one_line (text_ta, true);
  lv_textarea_set_password_mode (text_ta, false);
  lv_obj_set_width (text_ta, lv_pct (40));
  lv_obj_add_event_cb (text_ta, ta_event_cb1, LV_EVENT_ALL, NULL);
  lv_obj_align (text_ta, LV_ALIGN_TOP_RIGHT, -5, 20);


  /*Create a label and position it above the text box*/
  lv_obj_t *oneline_label = lv_label_create (lv_scr_act ());
  lv_label_set_text (oneline_label, "Text:");
  lv_obj_align_to (oneline_label, text_ta, LV_ALIGN_OUT_TOP_LEFT, 0, 0);

  /*Create a keyboard*/
  kb1 = lv_keyboard_create (lv_scr_act ());
  lv_obj_set_size (kb1,  LV_HOR_RES, LV_VER_RES / 2);

  lv_keyboard_set_textarea (kb1, pwd_ta); /*Focus it on one of the text areas to start*/
}

static void ta_event_cb1 (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *ta = lv_event_get_target (e);
  if (code == LV_EVENT_CLICKED || code == LV_EVENT_FOCUSED) {
    /*Focus on the clicked text area*/
    if (kb1 != NULL) lv_keyboard_set_textarea (kb1, ta);
  }

  else if (code == LV_EVENT_READY) {
    LV_LOG_USER ("Ready, current text: %s", lv_textarea_get_text (ta));
  }
}

static void ta_event_cb2 (lv_event_t *e);

static lv_obj_t *kb2;

/**
 * Automatically format text like a clock. E.g. "12:34"
 * Add the ':' automatically.
 */
void lv_example_textarea_3 (void) {
  /*Create the text area*/
  lv_obj_t *ta = lv_textarea_create (lv_scr_act ());
  lv_obj_add_event_cb (ta, ta_event_cb2, LV_EVENT_VALUE_CHANGED, NULL);
  lv_textarea_set_accepted_chars (ta, "0123456789:");
  lv_textarea_set_max_length (ta, 5);
  lv_textarea_set_one_line (ta, true);
  lv_textarea_set_text (ta, "");

  /*Create a keyboard*/
  kb2 = lv_keyboard_create (lv_scr_act ());
  lv_obj_set_size (kb2,  LV_HOR_RES, LV_VER_RES / 2);
  lv_keyboard_set_mode (kb2, LV_KEYBOARD_MODE_NUMBER);
  lv_keyboard_set_textarea (kb2, ta);
}

static void ta_event_cb2 (lv_event_t *e) {
  lv_obj_t *ta = lv_event_get_target (e);
  const char *txt = lv_textarea_get_text (ta);
  if (txt[0] >= '0' && txt[0] <= '9' &&
      txt[1] >= '0' && txt[1] <= '9' &&
      txt[2] != ':') {
    lv_textarea_set_cursor_pos (ta, 2);
    lv_textarea_add_char (ta, ':');
  }
}
