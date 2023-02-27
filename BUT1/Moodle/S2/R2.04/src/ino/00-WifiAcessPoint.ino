
// Set these to your desired credentials.
const char *ssid = "R204-XXX";
const char *password = "secret";


/*
  https://github.com/espressif/arduino-esp32/blob/master/libraries/WiFi/examples/WiFiAccessPoint/WiFiAccessPoint.ino
  https://www.arduino.cc/reference/en/libraries/wifi/
  WiFiAccessPoint.ino creates a WiFi access point and provides a web server on it.

*/

/* Uncomment below line to write on screen */
#define LOG_ON_SCREEN 1

#if LOG_ON_SCREEN
#define LGFX_WT32_SC01 
#define LGFX_AUTODETECT // Autodetect board
#define LGFX_USE_V1     // set to use new version of library
#include <LovyanGFX.hpp> // main library
static LGFX lcd; // declare display variable
#endif

#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiAP.h>


int count (0);

WiFiServer server (80);

String IpAddress2String (const IPAddress& ipAddress) {
  return String(ipAddress[0]) + String(".") +\
  String(ipAddress[1]) + String(".") +\
  String(ipAddress[2]) + String(".") +\
  String(ipAddress[3])  ; 
}

void setup () {
  Serial.begin (115200);
  Serial.println ();
  Serial.println ("Configuring access point...");
#if LOG_ON_SCREEN
  lcd.init (); // Initialize LovyanGFX
#endif

  // You can remove the password parameter if you want the AP to be open.
  WiFi.softAP (ssid, password);
  IPAddress myIP = WiFi.softAPIP ();
  Serial.print ("AP IP address: ");
  Serial.println (myIP);
#if LOG_ON_SCREEN
  lcd.setCursor (380, 0);
  lcd.printf ("AP IP address: %s\n", IpAddress2String (myIP));
#endif

  server.begin ();

  Serial.println ("Server started");
#if LOG_ON_SCREEN
  lcd.printf ("Server started on 192.168.4.1\n");
#endif
}

void loop () {
  WiFiClient client = server.available ();   // listen for incoming clients

  if (client) {                              // if you get a client,
    Serial.println ("New Client.");          // print a message out the serial port
#if LOG_ON_SCREEN
    lcd.printf ("New Client.\n");
#endif
    String currentLine = "";                 // make a String to hold incoming data from the client
    while (client.connected ()) {            // loop while the client's connected
      if (client.available ()) {             // if there's bytes to read from the client,
        char c = client.read ();             // read a byte, then
        Serial.write (c);                    // print it out the serial monitor
        if (c == '\n') {                     // if the byte is a newline character

          // if the current line is blank, you got two newline characters in a row.
          // that's the end of the client HTTP request, so send a response:
          if (currentLine.length () == 0) {
            // HTTP headers always start with a response code (e.g. HTTP/1.1 200 OK)
            // and a content-type so the client knows what's coming, then a blank line:
            client.println ("HTTP/1.1 200 OK");
            client.println ("Content-type:text/html");
            client.println ();

            // the content of the HTTP response follows the header:
            client.print ("Click <a href=\"/up\">here</a> count up.<br>");
            client.print ("Click <a href=\"/down\">here</a> count down.<br>");
            client.print ("count : ");
	    client.print (count);
	    client.print ("<br>");

            // The HTTP response ends with another blank line:
            client.println ();
            // break out of the while loop:
            break;
          } else {    // if you got a newline, then clear currentLine:
            currentLine = "";
          }
        } else if (c != '\r') {  // if you got anything else but a carriage return character,
          currentLine += c;      // add it to the end of the currentLine
        }

        // Check to see if the client request was "GET /up" or "GET /down":
        if (currentLine.endsWith ("GET /up")) {
	  ++count;
        }
        if (currentLine.endsWith ("GET /down")) {
	  --count;
        }
      }
    }
    // close the connection:
    client.stop ();
    Serial.println ("Client Disconnected.");
#if LOG_ON_SCREEN
    lcd.printf ("Client Disconnected.\n");
#endif
  }
}
