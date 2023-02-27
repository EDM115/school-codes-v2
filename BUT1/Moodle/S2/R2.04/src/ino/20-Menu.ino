/*
  https://docs.lvgl.io/master/widgets/menu.html
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
  lv_example_menu_1 ();
  // lv_example_menu_2 ();
  // lv_example_menu_3 ();
  // lv_example_menu_4 ();
  // lv_example_menu_5 ();
}

void local_loop (void) {
}


void lv_example_menu_1 (void) {
  /*Create a menu object*/
  lv_obj_t *menu = lv_menu_create (lv_scr_act ());
  lv_obj_set_size (menu, lv_disp_get_hor_res (NULL), lv_disp_get_ver_res (NULL));
  lv_obj_center (menu);

  lv_obj_t *cont;
  lv_obj_t *label;

  /*Create a sub page*/
  lv_obj_t *sub_page = lv_menu_page_create (menu, NULL);

  cont = lv_menu_cont_create (sub_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Hello, I am hiding here");

  /*Create a main page*/
  lv_obj_t *main_page = lv_menu_page_create (menu, NULL);

  cont = lv_menu_cont_create (main_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Item 1");

  cont = lv_menu_cont_create (main_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Item 2");

  cont = lv_menu_cont_create (main_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Item 3 (Click me!)");
  lv_menu_set_load_page_event (menu, cont, sub_page);

  lv_menu_set_page (menu, main_page);
}

static void back_event_handler2 (lv_event_t *e) {
  lv_obj_t *obj = lv_event_get_target (e);
  lv_obj_t *menu = (lv_obj_t *) lv_event_get_user_data (e);

  if (lv_menu_back_btn_is_root (menu, obj)) {
    lv_obj_t *mbox1 = lv_msgbox_create (NULL, "Hello", "Root back btn click.", NULL, true);
    lv_obj_center (mbox1);
  }
}

void lv_example_menu_2 (void) {
  lv_obj_t *menu = lv_menu_create (lv_scr_act ());
  lv_menu_set_mode_root_back_btn (menu, LV_MENU_ROOT_BACK_BTN_ENABLED);
  lv_obj_add_event_cb (menu, back_event_handler2, LV_EVENT_CLICKED, menu);
  lv_obj_set_size (menu, lv_disp_get_hor_res (NULL), lv_disp_get_ver_res (NULL));
  lv_obj_center (menu);

  lv_obj_t *cont;
  lv_obj_t *label;

  /*Create a sub page*/
  lv_obj_t *sub_page = lv_menu_page_create (menu, NULL);

  cont = lv_menu_cont_create (sub_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Hello, I am hiding here");

  /*Create a main page*/
  lv_obj_t *main_page = lv_menu_page_create (menu, NULL);

  cont = lv_menu_cont_create (main_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Item 1");

  cont = lv_menu_cont_create (main_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Item 2");

  cont = lv_menu_cont_create (main_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Item 3 (Click me!)");
  lv_menu_set_load_page_event (menu, cont, sub_page);

  lv_menu_set_page (menu, main_page);
}

void lv_example_menu_3 (void) {
  /*Create a menu object*/
  lv_obj_t *menu = lv_menu_create (lv_scr_act ());
  lv_obj_set_size (menu, lv_disp_get_hor_res (NULL), lv_disp_get_ver_res (NULL));
  lv_obj_center (menu);

  /*Modify the header*/
  lv_obj_t *back_btn = lv_menu_get_main_header_back_btn (menu);
  lv_obj_t *back_btn_label = lv_label_create (back_btn);
  lv_label_set_text (back_btn_label, "Back");

  lv_obj_t *cont;
  lv_obj_t *label;

  /*Create sub pages*/
  lv_obj_t *sub_1_page = lv_menu_page_create (menu, "Page 1");

  cont = lv_menu_cont_create (sub_1_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Hello, I am hiding here");

  lv_obj_t *sub_2_page = lv_menu_page_create (menu, "Page 2");

  cont = lv_menu_cont_create (sub_2_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Hello, I am hiding here");

  lv_obj_t *sub_3_page = lv_menu_page_create (menu, "Page 3");

  cont = lv_menu_cont_create (sub_3_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Hello, I am hiding here");

  /*Create a main page*/
  lv_obj_t *main_page = lv_menu_page_create (menu, NULL);

  cont = lv_menu_cont_create (main_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Item 1 (Click me!)");
  lv_menu_set_load_page_event (menu, cont, sub_1_page);

  cont = lv_menu_cont_create (main_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Item 2 (Click me!)");
  lv_menu_set_load_page_event (menu, cont, sub_2_page);

  cont = lv_menu_cont_create (main_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Item 3 (Click me!)");
  lv_menu_set_load_page_event (menu, cont, sub_3_page);

  lv_menu_set_page (menu, main_page);
}

static uint32_t btn_cnt = 1;
static lv_obj_t *main_page;
static lv_obj_t *menu;

static void float_btn_event_cb (lv_event_t *e) {
  LV_UNUSED (e);

  btn_cnt++;

  lv_obj_t *cont;
  lv_obj_t *label;

  lv_obj_t *sub_page = lv_menu_page_create (menu, NULL);

  cont = lv_menu_cont_create (sub_page);
  label = lv_label_create (cont);
  lv_label_set_text_fmt (label, "Hello, I am hiding inside %"LV_PRIu32"", btn_cnt);

  cont = lv_menu_cont_create (main_page);
  label = lv_label_create (cont);
  lv_label_set_text_fmt (label, "Item %"LV_PRIu32"", btn_cnt);
  lv_menu_set_load_page_event (menu, cont, sub_page);

  lv_obj_scroll_to_view_recursive (cont, LV_ANIM_ON);
}

void lv_example_menu_4 (void) {
  /*Create a menu object*/
  menu = lv_menu_create (lv_scr_act ());
  lv_obj_set_size (menu, lv_disp_get_hor_res (NULL), lv_disp_get_ver_res (NULL));
  lv_obj_center (menu);

  lv_obj_t *cont;
  lv_obj_t *label;

  /*Create a sub page*/
  lv_obj_t *sub_page = lv_menu_page_create (menu, NULL);

  cont = lv_menu_cont_create (sub_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Hello, I am hiding inside the first item");

  /*Create a main page*/
  main_page = lv_menu_page_create (menu, NULL);

  cont = lv_menu_cont_create (main_page);
  label = lv_label_create (cont);
  lv_label_set_text (label, "Item 1");
  lv_menu_set_load_page_event (menu, cont, sub_page);

  lv_menu_set_page (menu, main_page);

  /*Create floating btn*/
  lv_obj_t *float_btn = lv_btn_create (lv_scr_act ());
  lv_obj_set_size (float_btn, 50, 50);
  lv_obj_add_flag (float_btn, LV_OBJ_FLAG_FLOATING);
  lv_obj_align (float_btn, LV_ALIGN_BOTTOM_RIGHT, -10, -10);
  lv_obj_add_event_cb (float_btn, float_btn_event_cb, LV_EVENT_CLICKED, menu);
  lv_obj_set_style_radius (float_btn, LV_RADIUS_CIRCLE, 0);
  lv_obj_set_style_bg_img_src (float_btn, LV_SYMBOL_PLUS, 0);
  lv_obj_set_style_text_font (float_btn, lv_theme_get_font_large (float_btn), 0);
}

enum {
  LV_MENU_ITEM_BUILDER_VARIANT_1,
  LV_MENU_ITEM_BUILDER_VARIANT_2
};
typedef uint8_t lv_menu_builder_variant_t;

static void back_event_handler5 (lv_event_t *e);
static void switch_handler (lv_event_t *e);
lv_obj_t *root_page;
static lv_obj_t *create_text (lv_obj_t *parent, const char *icon, const char *txt,
			      lv_menu_builder_variant_t builder_variant);
static lv_obj_t *create_slider (lv_obj_t *parent,
				const char *icon, const char *txt, int32_t min, int32_t max, int32_t val);
static lv_obj_t *create_switch (lv_obj_t *parent,
				const char *icon, const char *txt, bool chk);

void lv_example_menu_5 (void) {
  lv_obj_t *menu = lv_menu_create (lv_scr_act ());

  lv_color_t bg_color = lv_obj_get_style_bg_color (menu, 0);
  if (lv_color_brightness (bg_color) > 127) {
    lv_obj_set_style_bg_color (menu, lv_color_darken (lv_obj_get_style_bg_color (menu, 0), 10), 0);
  }
  else {
    lv_obj_set_style_bg_color (menu, lv_color_darken (lv_obj_get_style_bg_color (menu, 0), 50), 0);
  }
  lv_menu_set_mode_root_back_btn (menu, LV_MENU_ROOT_BACK_BTN_ENABLED);
  lv_obj_add_event_cb (menu, back_event_handler5, LV_EVENT_CLICKED, menu);
  lv_obj_set_size (menu, lv_disp_get_hor_res (NULL), lv_disp_get_ver_res (NULL));
  lv_obj_center (menu);

  lv_obj_t *cont;
  lv_obj_t *section;

  /*Create sub pages*/
  lv_obj_t *sub_mechanics_page = lv_menu_page_create (menu, NULL);
  lv_obj_set_style_pad_hor (sub_mechanics_page, lv_obj_get_style_pad_left (lv_menu_get_main_header (menu), 0), 0);
  lv_menu_separator_create (sub_mechanics_page);
  section = lv_menu_section_create (sub_mechanics_page);
  create_slider (section, LV_SYMBOL_SETTINGS, "Velocity", 0, 150, 120);
  create_slider (section, LV_SYMBOL_SETTINGS, "Acceleration", 0, 150, 50);
  create_slider (section, LV_SYMBOL_SETTINGS, "Weight limit", 0, 150, 80);

  lv_obj_t *sub_sound_page = lv_menu_page_create (menu, NULL);
  lv_obj_set_style_pad_hor (sub_sound_page, lv_obj_get_style_pad_left (lv_menu_get_main_header (menu), 0), 0);
  lv_menu_separator_create (sub_sound_page);
  section = lv_menu_section_create (sub_sound_page);
  create_switch (section, LV_SYMBOL_AUDIO, "Sound", false);

  lv_obj_t *sub_display_page = lv_menu_page_create (menu, NULL);
  lv_obj_set_style_pad_hor (sub_display_page, lv_obj_get_style_pad_left (lv_menu_get_main_header (menu), 0), 0);
  lv_menu_separator_create (sub_display_page);
  section = lv_menu_section_create (sub_display_page);
  create_slider (section, LV_SYMBOL_SETTINGS, "Brightness", 0, 150, 100);

  lv_obj_t *sub_software_info_page = lv_menu_page_create (menu, NULL);
  lv_obj_set_style_pad_hor (sub_software_info_page, lv_obj_get_style_pad_left (lv_menu_get_main_header (menu), 0), 0);
  section = lv_menu_section_create (sub_software_info_page);
  create_text (section, NULL, "Version 1.0", LV_MENU_ITEM_BUILDER_VARIANT_1);

  lv_obj_t *sub_legal_info_page = lv_menu_page_create (menu, NULL);
  lv_obj_set_style_pad_hor (sub_legal_info_page, lv_obj_get_style_pad_left (lv_menu_get_main_header (menu), 0), 0);
  section = lv_menu_section_create (sub_legal_info_page);
  for (uint32_t i = 0; i < 15; i++) {
    create_text (section, NULL,
		 "This is a long long long long long long long long long text, if it is long enough it may scroll.",
		 LV_MENU_ITEM_BUILDER_VARIANT_1);
  }

  lv_obj_t *sub_about_page = lv_menu_page_create (menu, NULL);
  lv_obj_set_style_pad_hor (sub_about_page, lv_obj_get_style_pad_left (lv_menu_get_main_header (menu), 0), 0);
  lv_menu_separator_create (sub_about_page);
  section = lv_menu_section_create (sub_about_page);
  cont = create_text (section, NULL, "Software information", LV_MENU_ITEM_BUILDER_VARIANT_1);
  lv_menu_set_load_page_event (menu, cont, sub_software_info_page);
  cont = create_text (section, NULL, "Legal information", LV_MENU_ITEM_BUILDER_VARIANT_1);
  lv_menu_set_load_page_event (menu, cont, sub_legal_info_page);

  lv_obj_t *sub_menu_mode_page = lv_menu_page_create (menu, NULL);
  lv_obj_set_style_pad_hor (sub_menu_mode_page, lv_obj_get_style_pad_left (lv_menu_get_main_header (menu), 0), 0);
  lv_menu_separator_create (sub_menu_mode_page);
  section = lv_menu_section_create (sub_menu_mode_page);
  cont = create_switch (section, LV_SYMBOL_AUDIO, "Sidebar enable", true);
  lv_obj_add_event_cb (lv_obj_get_child (cont, 2), switch_handler, LV_EVENT_VALUE_CHANGED, menu);

  /*Create a root page*/
  root_page = lv_menu_page_create (menu, "Settings");
  lv_obj_set_style_pad_hor (root_page, lv_obj_get_style_pad_left (lv_menu_get_main_header (menu), 0), 0);
  section = lv_menu_section_create (root_page);
  cont = create_text (section, LV_SYMBOL_SETTINGS, "Mechanics", LV_MENU_ITEM_BUILDER_VARIANT_1);
  lv_menu_set_load_page_event (menu, cont, sub_mechanics_page);
  cont = create_text (section, LV_SYMBOL_AUDIO, "Sound", LV_MENU_ITEM_BUILDER_VARIANT_1);
  lv_menu_set_load_page_event (menu, cont, sub_sound_page);
  cont = create_text (section, LV_SYMBOL_SETTINGS, "Display", LV_MENU_ITEM_BUILDER_VARIANT_1);
  lv_menu_set_load_page_event (menu, cont, sub_display_page);

  create_text (root_page, NULL, "Others", LV_MENU_ITEM_BUILDER_VARIANT_1);
  section = lv_menu_section_create (root_page);
  cont = create_text (section, NULL, "About", LV_MENU_ITEM_BUILDER_VARIANT_1);
  lv_menu_set_load_page_event (menu, cont, sub_about_page);
  cont = create_text (section, LV_SYMBOL_SETTINGS, "Menu mode", LV_MENU_ITEM_BUILDER_VARIANT_1);
  lv_menu_set_load_page_event (menu, cont, sub_menu_mode_page);

  lv_menu_set_sidebar_page (menu, root_page);

  lv_event_send (lv_obj_get_child (lv_obj_get_child (lv_menu_get_cur_sidebar_page (menu), 0), 0), LV_EVENT_CLICKED, NULL);
}

static void back_event_handler5 (lv_event_t *e) {
  lv_obj_t *obj = lv_event_get_target (e);
  lv_obj_t *menu = (lv_obj_t *) lv_event_get_user_data (e);

  if (lv_menu_back_btn_is_root (menu, obj)) {
    lv_obj_t *mbox1 = lv_msgbox_create (NULL, "Hello", "Root back btn click.", NULL, true);
    lv_obj_center (mbox1);
  }
}

static void switch_handler (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);
  lv_obj_t *menu = (lv_obj_t *) lv_event_get_user_data (e);
  lv_obj_t *obj = lv_event_get_target (e);
  if (code == LV_EVENT_VALUE_CHANGED) {
    if (lv_obj_has_state (obj, LV_STATE_CHECKED)) {
      lv_menu_set_page (menu, NULL);
      lv_menu_set_sidebar_page (menu, root_page);
      lv_event_send (lv_obj_get_child (lv_obj_get_child (lv_menu_get_cur_sidebar_page (menu), 0), 0), LV_EVENT_CLICKED, NULL);
    }
    else {
      lv_menu_set_sidebar_page (menu, NULL);
      lv_menu_clear_history (menu); /* Clear history because we will be showing the root page later */
      lv_menu_set_page (menu, root_page);
    }
  }
}

static lv_obj_t *create_text (lv_obj_t *parent, const char *icon, const char *txt,
			      lv_menu_builder_variant_t builder_variant) {
  lv_obj_t *obj = lv_menu_cont_create (parent);

  lv_obj_t *img = NULL;
  lv_obj_t *label = NULL;

  if (icon) {
    img = lv_img_create (obj);
    lv_img_set_src (img, icon);
  }

  if (txt) {
    label = lv_label_create (obj);
    lv_label_set_text (label, txt);
    lv_label_set_long_mode (label, LV_LABEL_LONG_SCROLL_CIRCULAR);
    lv_obj_set_flex_grow (label, 1);
  }

  if (builder_variant == LV_MENU_ITEM_BUILDER_VARIANT_2 && icon && txt) {
    lv_obj_add_flag (img, LV_OBJ_FLAG_FLEX_IN_NEW_TRACK);
    lv_obj_swap (img, label);
  }

  return obj;
}

static lv_obj_t *create_slider (lv_obj_t *parent, const char *icon, const char *txt, int32_t min, int32_t max,
				int32_t val) {
  lv_obj_t *obj = create_text (parent, icon, txt, LV_MENU_ITEM_BUILDER_VARIANT_2);

  lv_obj_t *slider = lv_slider_create (obj);
  lv_obj_set_flex_grow (slider, 1);
  lv_slider_set_range (slider, min, max);
  lv_slider_set_value (slider, val, LV_ANIM_OFF);

  if (icon == NULL) {
    lv_obj_add_flag (slider, LV_OBJ_FLAG_FLEX_IN_NEW_TRACK);
  }

  return obj;
}

static lv_obj_t *create_switch (lv_obj_t *parent, const char *icon, const char *txt, bool chk) {
  lv_obj_t *obj = create_text (parent, icon, txt, LV_MENU_ITEM_BUILDER_VARIANT_1);

  lv_obj_t *sw = lv_switch_create (obj);
  lv_obj_add_state (sw, chk ? LV_STATE_CHECKED : 0);

  return obj;
}
