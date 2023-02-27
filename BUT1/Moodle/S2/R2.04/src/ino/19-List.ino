/*
  https://docs.lvgl.io/master/widgets/list.html
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
  // lv_example_list_1 ();
  lv_example_list_2 ();
}

void local_loop (void) {
}


static lv_obj_t *listA;

static void event_handler1 (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *obj = lv_event_get_target (e);
  LV_UNUSED (obj);
  if (code == LV_EVENT_CLICKED) {
    LV_LOG_USER ("Clicked: %s", lv_list_get_btn_text (listA, obj));
  }
}

void lv_example_list_1 (void) {
  /*Create a list*/
  listA = lv_list_create (lv_scr_act ());
  lv_obj_set_size (listA, 180, 220);
  lv_obj_center (listA);

  /*Add buttons to the list*/
  lv_obj_t *btn;

  lv_list_add_text (listA, "File");
  btn = lv_list_add_btn (listA, LV_SYMBOL_FILE, "New");
  lv_obj_add_event_cb (btn, event_handler1, LV_EVENT_CLICKED, NULL);
  btn = lv_list_add_btn (listA, LV_SYMBOL_DIRECTORY, "Open");
  lv_obj_add_event_cb (btn, event_handler1, LV_EVENT_CLICKED, NULL);
  btn = lv_list_add_btn (listA, LV_SYMBOL_SAVE, "Save");
  lv_obj_add_event_cb (btn, event_handler1, LV_EVENT_CLICKED, NULL);
  btn = lv_list_add_btn (listA, LV_SYMBOL_CLOSE, "Delete");
  lv_obj_add_event_cb (btn, event_handler1, LV_EVENT_CLICKED, NULL);
  btn = lv_list_add_btn (listA, LV_SYMBOL_EDIT, "Edit");
  lv_obj_add_event_cb (btn, event_handler1, LV_EVENT_CLICKED, NULL);

  lv_list_add_text (listA, "Connectivity");
  btn = lv_list_add_btn (listA, LV_SYMBOL_BLUETOOTH, "Bluetooth");
  lv_obj_add_event_cb (btn, event_handler1, LV_EVENT_CLICKED, NULL);
  btn = lv_list_add_btn (listA, LV_SYMBOL_GPS, "Navigation");
  lv_obj_add_event_cb (btn, event_handler1, LV_EVENT_CLICKED, NULL);
  btn = lv_list_add_btn (listA, LV_SYMBOL_USB, "USB");
  lv_obj_add_event_cb (btn, event_handler1, LV_EVENT_CLICKED, NULL);
  btn = lv_list_add_btn (listA, LV_SYMBOL_BATTERY_FULL, "Battery");
  lv_obj_add_event_cb (btn, event_handler1, LV_EVENT_CLICKED, NULL);

  lv_list_add_text (listA, "Exit");
  btn = lv_list_add_btn (listA, LV_SYMBOL_OK, "Apply");
  lv_obj_add_event_cb (btn, event_handler1, LV_EVENT_CLICKED, NULL);
  btn = lv_list_add_btn (listA, LV_SYMBOL_CLOSE, "Close");
  lv_obj_add_event_cb (btn, event_handler1, LV_EVENT_CLICKED, NULL);
}


#include <stdlib.h>

static lv_obj_t *list1;
static lv_obj_t *list2;

static lv_obj_t *currentButton = NULL;

static void event_handler2 (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *obj = lv_event_get_target (e);
  if (code == LV_EVENT_CLICKED) {
    LV_LOG_USER ("Clicked: %s", lv_list_get_btn_text (list1, obj));

    if (currentButton == obj) {
      currentButton = NULL;
    }
    else {
      currentButton = obj;
    }
    lv_obj_t *parent = lv_obj_get_parent (obj);
    uint32_t i;
    for (i = 0; i < lv_obj_get_child_cnt (parent); i++) {
      lv_obj_t *child = lv_obj_get_child (parent, i);
      if (child == currentButton) {
	lv_obj_add_state (child, LV_STATE_CHECKED);
      }
      else {
	lv_obj_clear_state (child, LV_STATE_CHECKED);
      }
    }
  }
}

static void event_handler_top (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  if (code == LV_EVENT_CLICKED) {
    if (currentButton == NULL) return;
    lv_obj_move_background (currentButton);
    lv_obj_scroll_to_view (currentButton, LV_ANIM_ON);
  }
}

static void event_handler_up (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  if ( (code == LV_EVENT_CLICKED) || (code == LV_EVENT_LONG_PRESSED_REPEAT)) {
    if (currentButton == NULL) return;
    uint32_t index = lv_obj_get_index (currentButton);
    if (index <= 0) return;
    lv_obj_move_to_index (currentButton, index - 1);
    lv_obj_scroll_to_view (currentButton, LV_ANIM_ON);
  }
}

static void event_handler_center (lv_event_t *e) {
  const lv_event_code_t code = lv_event_get_code (e);
  if ( (code == LV_EVENT_CLICKED) || (code == LV_EVENT_LONG_PRESSED_REPEAT)) {
    if (currentButton == NULL) return;

    lv_obj_t *parent = lv_obj_get_parent (currentButton);
    const uint32_t pos = lv_obj_get_child_cnt (parent) / 2;

    lv_obj_move_to_index (currentButton, pos);

    lv_obj_scroll_to_view (currentButton, LV_ANIM_ON);
  }
}

static void event_handler_dn (lv_event_t *e) {
  const lv_event_code_t code = lv_event_get_code (e);
  if ( (code == LV_EVENT_CLICKED) || (code == LV_EVENT_LONG_PRESSED_REPEAT)) {
    if (currentButton == NULL) return;
    const uint32_t index = lv_obj_get_index (currentButton);

    lv_obj_move_to_index (currentButton, index + 1);
    lv_obj_scroll_to_view (currentButton, LV_ANIM_ON);
  }
}

static void event_handler_bottom (lv_event_t *e) {
  const lv_event_code_t code = lv_event_get_code (e);
  if (code == LV_EVENT_CLICKED) {
    if (currentButton == NULL) return;
    lv_obj_move_foreground (currentButton);
    lv_obj_scroll_to_view (currentButton, LV_ANIM_ON);
  }
}

static void event_handler_swap (lv_event_t *e) {
  const lv_event_code_t code = lv_event_get_code (e);
  // lv_obj_t *obj = lv_event_get_target (e);
  if ( (code == LV_EVENT_CLICKED) || (code == LV_EVENT_LONG_PRESSED_REPEAT)) {
    uint32_t cnt = lv_obj_get_child_cnt (list1);
    for (int i = 0; i < 100; i++)
      if (cnt > 1) {
	lv_obj_t *obj = lv_obj_get_child (list1, rand () % cnt);
	lv_obj_move_to_index (obj, rand () % cnt);
	if (currentButton != NULL) {
	  lv_obj_scroll_to_view (currentButton, LV_ANIM_ON);
	}
      }
  }
}

void lv_example_list_2 (void) {
  /*Create a list*/
  list1 = lv_list_create (lv_scr_act ());
  lv_obj_set_size (list1, lv_pct (60), lv_pct (100));
  lv_obj_set_style_pad_row (list1, 5, 0);

  /*Add buttons to the list*/
  lv_obj_t *btn;
  int i;
  for (i = 0; i < 15; i++) {
    btn = lv_btn_create (list1);
    lv_obj_set_width (btn, lv_pct (50));
    lv_obj_add_event_cb (btn, event_handler2, LV_EVENT_CLICKED, NULL);

    lv_obj_t *lab = lv_label_create (btn);
    lv_label_set_text_fmt (lab, "Item %d", i);
  }

  /*Select the first button by default*/
  currentButton = lv_obj_get_child (list1, 0);
  lv_obj_add_state (currentButton, LV_STATE_CHECKED);

  /*Create a second list with up and down buttons*/
  list2 = lv_list_create (lv_scr_act ());
  lv_obj_set_size (list2, lv_pct (40), lv_pct (100));
  lv_obj_align (list2, LV_ALIGN_TOP_RIGHT, 0, 0);
  lv_obj_set_flex_flow (list2, LV_FLEX_FLOW_COLUMN);

  btn = lv_list_add_btn (list2, NULL, "Top");
  lv_obj_add_event_cb (btn, event_handler_top, LV_EVENT_ALL, NULL);
  lv_group_remove_obj (btn);

  btn = lv_list_add_btn (list2, LV_SYMBOL_UP, "Up");
  lv_obj_add_event_cb (btn, event_handler_up, LV_EVENT_ALL, NULL);
  lv_group_remove_obj (btn);

  btn = lv_list_add_btn (list2, LV_SYMBOL_LEFT, "Center");
  lv_obj_add_event_cb (btn, event_handler_center, LV_EVENT_ALL, NULL);
  lv_group_remove_obj (btn);

  btn = lv_list_add_btn (list2, LV_SYMBOL_DOWN, "Down");
  lv_obj_add_event_cb (btn, event_handler_dn, LV_EVENT_ALL, NULL);
  lv_group_remove_obj (btn);

  btn = lv_list_add_btn (list2, NULL, "Bottom");
  lv_obj_add_event_cb (btn, event_handler_bottom, LV_EVENT_ALL, NULL);
  lv_group_remove_obj (btn);

  btn = lv_list_add_btn (list2, LV_SYMBOL_SHUFFLE, "Shuffle");
  lv_obj_add_event_cb (btn, event_handler_swap, LV_EVENT_ALL, NULL);
  lv_group_remove_obj (btn);
}
