/****
Tramontana for Processing
Tramontana is a tool for interactive spaces. It communicates with iOS devices. You can download the app here: https://itunes.apple.com/us/app/tramontana/id1121069555?mt=8
made by Pierluigi Dalla Rosa

A_HelloWorld
With this sketch you can test the connection to your device. 
One mouse click corresponds to a haptic vibration.

Don't forget to change the ipAddress below (192.168.1.17) to match the one visible on your device when you open the app.
***/

///IMPORT TRAMONTANA
import tramontana.library.*;

///Tramontana needs websockets that can be found at:
///https://github.com/alexandrainst/processing_websockets
import websockets.*;

/* Create an instance of Tramonana */
Tramontana t;

void setup(){
 size(480,240);
 /* Start the connection with Tramontana iOS/AppleTV/Android */
 /* Look on your device for the ipAddress, it shows on the starting panel when you open the app */
 t = new Tramontana(this,"192.168.1.17");
}
void draw(){
  background(255);
  fill(128);
  text("Hello Tramontana!",width/2-(textWidth("Hello Tramontana!")/2),height/2);
  text("Click your mouse to activate the haptic engine on your phone!",width/2-(textWidth("Click your mouse to activate the haptic engine on your phone!")/2),height/2+30);
}
void mousePressed(){
  /* This will make the haptic engine of your iDevice vibrate */
  t.makeVibrate();
}
