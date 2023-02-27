/*
  https://docs.lvgl.io/master/widgets/btn.html
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
void display_flush  (lv_disp_drv_t *disp, const lv_area_t *area, lv_color_t *color_p) {
  uint32_t w =  (area->x2 - area->x1 + 1);
  uint32_t h =  (area->y2 - area->y1 + 1);

  lcd.startWrite  ();
  lcd.setAddrWindow  (area->x1, area->y1, w, h);
  lcd.pushColors  ( (uint16_t *) &color_p->full, w * h, true);
  lcd.endWrite  ();

  lv_disp_flush_ready  (disp);
}

/*** Touchpad callback to read the touchpad ***/
void touchpad_read  (lv_indev_drv_t *indev_driver, lv_indev_data_t *data) {
  uint16_t touchX, touchY;
  bool touched = lcd.getTouch  (&touchX, &touchY);

  if  (!touched)
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
void local_main  (void) {
  lv_example_btn_1  ();
  lv_example_btn_2  ();
  lv_example_btn_3  ();
}

void local_loop  (void) {
}


static void event_handler (lv_event_t *e) {
  lv_event_code_t code = lv_event_get_code (e);

  if (code == LV_EVENT_CLICKED) {
    LV_LOG_USER ("Clicked");
  }
  else if (code == LV_EVENT_VALUE_CHANGED) {
    LV_LOG_USER ("Toggled");
  }
}

void lv_example_btn_1 (void) {
  lv_obj_t *label;

  lv_obj_t *btn1 = lv_btn_create (lv_scr_act ());
  lv_obj_add_event_cb (btn1, event_handler, LV_EVENT_ALL, NULL);
  lv_obj_align (btn1, LV_ALIGN_LEFT_MID, 0, -40);

  label = lv_label_create (btn1);
  lv_label_set_text (label, "Button");
  lv_obj_center (label);

  lv_obj_t *btn2 = lv_btn_create (lv_scr_act ());
  lv_obj_add_event_cb (btn2, event_handler, LV_EVENT_ALL, NULL);
  lv_obj_align (btn2, LV_ALIGN_LEFT_MID, 0, 40);
  lv_obj_add_flag (btn2, LV_OBJ_FLAG_CHECKABLE);
  lv_obj_set_height (btn2, LV_SIZE_CONTENT);

  label = lv_label_create (btn2);
  lv_label_set_text (label, "Toggle");
  lv_obj_center (label);
}

/**
 * Style a button from scratch
 */
void lv_example_btn_2 (void) {
  /*Init the style for the default state*/
  static lv_style_t style;
  lv_style_init (&style);

  lv_style_set_radius (&style, 3);

  lv_style_set_bg_opa (&style, LV_OPA_100);
  lv_style_set_bg_color (&style, lv_palette_main (LV_PALETTE_BLUE));
  lv_style_set_bg_grad_color (&style, lv_palette_darken (LV_PALETTE_BLUE, 2));
  lv_style_set_bg_grad_dir (&style, LV_GRAD_DIR_VER);

  lv_style_set_border_opa (&style, LV_OPA_40);
  lv_style_set_border_width (&style, 2);
  lv_style_set_border_color (&style, lv_palette_main (LV_PALETTE_GREY));

  lv_style_set_shadow_width (&style, 8);
  lv_style_set_shadow_color (&style, lv_palette_main (LV_PALETTE_GREY));
  lv_style_set_shadow_ofs_y (&style, 8);

  lv_style_set_outline_opa (&style, LV_OPA_COVER);
  lv_style_set_outline_color (&style, lv_palette_main (LV_PALETTE_BLUE));

  lv_style_set_text_color (&style, lv_color_white ());
  lv_style_set_pad_all (&style, 10);

  /*Init the pressed style*/
  static lv_style_t style_pr;
  lv_style_init (&style_pr);

  /*Add a large outline when pressed*/
  lv_style_set_outline_width (&style_pr, 30);
  lv_style_set_outline_opa (&style_pr, LV_OPA_TRANSP);

  lv_style_set_translate_y (&style_pr, 5);
  lv_style_set_shadow_ofs_y (&style_pr, 3);
  lv_style_set_bg_color (&style_pr, lv_palette_darken (LV_PALETTE_BLUE, 2));
  lv_style_set_bg_grad_color (&style_pr, lv_palette_darken (LV_PALETTE_BLUE, 4));

  /*Add a transition to the outline*/
  static lv_style_transition_dsc_t trans;
  static lv_style_prop_t props[] = {LV_STYLE_OUTLINE_WIDTH, LV_STYLE_OUTLINE_OPA, (lv_style_prop_t) 0};
  lv_style_transition_dsc_init (&trans, props, lv_anim_path_linear, 300, 0, NULL);

  lv_style_set_transition (&style_pr, &trans);

  lv_obj_t *btn1 = lv_btn_create (lv_scr_act ());
  lv_obj_remove_style_all (btn1);                          /*Remove the style coming from the theme*/
  lv_obj_add_style (btn1, &style, 0);
  lv_obj_add_style (btn1, &style_pr, LV_STATE_PRESSED);
  lv_obj_set_size (btn1, LV_SIZE_CONTENT, LV_SIZE_CONTENT);
  lv_obj_center (btn1);

  lv_obj_t *label = lv_label_create (btn1);
  lv_label_set_text (label, "Button");
  lv_obj_center (label);
}

/**
 * Create a style transition on a button to act like a gum when clicked
 */
void lv_example_btn_3 (void) {
  /*Properties to transition*/
  static lv_style_prop_t props[] = {
    LV_STYLE_TRANSFORM_WIDTH, LV_STYLE_TRANSFORM_HEIGHT, LV_STYLE_TEXT_LETTER_SPACE, (lv_style_prop_t)0
  };

  /*Transition descriptor when going back to the default state.
   *Add some delay to be sure the press transition is visible even if the press was very short*/
  static lv_style_transition_dsc_t transition_dsc_def;
  lv_style_transition_dsc_init (&transition_dsc_def, props, lv_anim_path_overshoot, 250, 100, NULL);

  /*Transition descriptor when going to pressed state.
   *No delay, go to presses state immediately*/
  static lv_style_transition_dsc_t transition_dsc_pr;
  lv_style_transition_dsc_init (&transition_dsc_pr, props, lv_anim_path_ease_in_out, 250, 0, NULL);

  /*Add only the new transition to he default state*/
  static lv_style_t style_def;
  lv_style_init (&style_def);
  lv_style_set_transition (&style_def, &transition_dsc_def);

  /*Add the transition and some transformation to the presses state.*/
  static lv_style_t style_pr;
  lv_style_init (&style_pr);
  lv_style_set_transform_width (&style_pr, 10);
  lv_style_set_transform_height (&style_pr, -10);
  lv_style_set_text_letter_space (&style_pr, 10);
  lv_style_set_transition (&style_pr, &transition_dsc_pr);

  lv_obj_t *btn1 = lv_btn_create (lv_scr_act ());
  lv_obj_align (btn1, LV_ALIGN_CENTER, 0, -80);
  lv_obj_add_style (btn1, &style_pr, LV_STATE_PRESSED);
  lv_obj_add_style (btn1, &style_def, 0);

  lv_obj_t *label = lv_label_create (btn1);
  lv_label_set_text (label, "Gum");
}
