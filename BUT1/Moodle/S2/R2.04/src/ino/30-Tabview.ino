/*
  https://docs.lvgl.io/master/widgets/tabview.html
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
  lv_example_tabview_1 ();
  // lv_example_tabview_2 ();
}

void local_loop (void) {
}


void lv_example_tabview_1 (void) {
  /*Create a Tab view object*/
  lv_obj_t *tabview;
  tabview = lv_tabview_create (lv_scr_act (), LV_DIR_TOP, 50);

  /*Add 3 tabs (the tabs are page (lv_page) and can be scrolled*/
  lv_obj_t *tab1 = lv_tabview_add_tab (tabview, "Tab 1");
  lv_obj_t *tab2 = lv_tabview_add_tab (tabview, "Tab 2");
  lv_obj_t *tab3 = lv_tabview_add_tab (tabview, "Tab 3");

  /*Add content to the tabs*/
  lv_obj_t *label = lv_label_create (tab1);
  lv_label_set_text
    (label,
     "This the first tab\n\n"	"If the content\n"	"of a tab\n"
     "becomes too\n"		"longer\n"		"than the\n"
     "container\n"		"then it\n"		"automatically\n"
     "becomes\n"		"scrollable.\n"		"\n"
     "\n"			"\n"			"Can you see it?");

  label = lv_label_create (tab2);
  lv_label_set_text (label, "Second tab");

  label = lv_label_create (tab3);
  lv_label_set_text (label, "Third tab");

  lv_obj_scroll_to_view_recursive (label, LV_ANIM_ON);

}

static void scroll_begin_event (lv_event_t *e) {
  /*Disable the scroll animations. Triggered when a tab button is clicked */
  if (lv_event_get_code (e) == LV_EVENT_SCROLL_BEGIN) {
    lv_anim_t *a = (lv_anim_t *) lv_event_get_param (e);
    if (a)  a->time = 0;
  }
}

void lv_example_tabview_2 (void) {
  /*Create a Tab view object*/
  lv_obj_t *tabview;
  tabview = lv_tabview_create (lv_scr_act (), LV_DIR_LEFT, 80);
  lv_obj_add_event_cb (lv_tabview_get_content (tabview), scroll_begin_event, LV_EVENT_SCROLL_BEGIN, NULL);

  lv_obj_set_style_bg_color (tabview, lv_palette_lighten (LV_PALETTE_RED, 2), 0);

  lv_obj_t *tab_btns = lv_tabview_get_tab_btns (tabview);
  lv_obj_set_style_bg_color (tab_btns, lv_palette_darken (LV_PALETTE_GREY, 3), 0);
  lv_obj_set_style_text_color (tab_btns, lv_palette_lighten (LV_PALETTE_GREY, 5), 0);
  lv_obj_set_style_border_side (tab_btns, LV_BORDER_SIDE_RIGHT, LV_PART_ITEMS | LV_STATE_CHECKED);


  /*Add 3 tabs (the tabs are page (lv_page) and can be scrolled*/
  lv_obj_t *tab1 = lv_tabview_add_tab (tabview, "Tab 1");
  lv_obj_t *tab2 = lv_tabview_add_tab (tabview, "Tab 2");
  lv_obj_t *tab3 = lv_tabview_add_tab (tabview, "Tab 3");
  lv_obj_t *tab4 = lv_tabview_add_tab (tabview, "Tab 4");
  lv_obj_t *tab5 = lv_tabview_add_tab (tabview, "Tab 5");

  lv_obj_set_style_bg_color (tab2, lv_palette_lighten (LV_PALETTE_AMBER, 3), 0);
  lv_obj_set_style_bg_opa (tab2, LV_OPA_COVER, 0);

  /*Add content to the tabs*/
  lv_obj_t *label = lv_label_create (tab1);
  lv_label_set_text (label, "First tab");

  label = lv_label_create (tab2);
  lv_label_set_text (label, "Second tab");

  label = lv_label_create (tab3);
  lv_label_set_text (label, "Third tab");

  label = lv_label_create (tab4);
  lv_label_set_text (label, "Forth tab");

  label = lv_label_create (tab5);
  lv_label_set_text (label, "Fifth tab");

  lv_obj_clear_flag (lv_tabview_get_content (tabview), LV_OBJ_FLAG_SCROLLABLE);
}
