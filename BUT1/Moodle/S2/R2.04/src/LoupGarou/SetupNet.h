#ifndef _SetupNet_h_
#define _SetupNet_h_

// ========================================
extern const char *password;
extern const char *webHost;
extern const int webPort;

bool isNetSet ();
bool isServer ();
String getSSID ();
String getLocalMac ();

void updateMac ();
String mac2string (const byte mac [6]);
void displayFormNet (bool show);

//  =======================================
#endif // _SetupNet_h_
