/****
Tramontana for Processing
Tramontana is a tool for interactive spaces. It communicates with iOS devices. You can download the app here: https://itunes.apple.com/us/app/tramontana/id1121069555?mt=8
made by Pierluigi Dalla Rosa

C_MultipleDevices
Change the color of multiple devices.

Don't forget to change the ipAddresss below (192.168.1.17, 192.168.1.11) to match the one visible on your devices when you open the app.
***/

///IMPORT TRAMONTANA
import tramontana.library.*;

///Tramontana needs websockets that can be found at:
///https://github.com/alexandrainst/processing_websockets
import websockets.*;

/* Create an instance of Tramonana for each of your devices*/
Tramontana t1;
Tramontana t2;

String txtOnScrn = "Press the mouse to change color of multiple tramontanas!";
int size = 150;
void setup(){
 size(480,240);
  /* Start the connection with Tramontana iOS/AppleTV/Android */
 /* Look on your device for the ipAddress, it shows on the starting panel when you open the app */
 t1 = new Tramontana(this,"192.168.1.11");
 /* Look on your other device for the ipAddress, it shows on the starting panel when you open the app */
 t2 = new Tramontana(this,"192.168.1.17");
}
void draw(){
  background(255);
  fill(128);
  text(txtOnScrn,width/2-(textWidth(txtOnScrn)/2),height-12);
  ellipse(width/2,height/2,size,size);
}
void mousePressed(){
  /* You are concurrently changing color of your devices */
   t1.setColor(0,0,0,0.5);
   t2.setColor(0,0,0,0.5);
   size = 135;
   
}
void mouseReleased()
{
  /* You are concurrently changing color of your devices */
   t1.setColor(random(0,1.0),random(0,1.0),random(0,1.0),0.5);
   t2.setColor(random(0,1.0),random(0,1.0),random(0,1.0),0.5);
   size = 150;
}
