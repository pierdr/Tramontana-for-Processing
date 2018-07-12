/****
Tramontana for Processing
Tramontana is a tool for interactive spaces. It communicates with iOS devices. You can download the app here: https://itunes.apple.com/us/app/tramontana/id1121069555?mt=8
made by Pierluigi Dalla Rosa

B_ChangeColor
With this sketch you can change the color of your device. 

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
  fill(255,128,128);
  noStroke();
  rect(0,0,width/2,height/2);
  
   fill(128,255,128);
  rect(width/2,0,width/2,height/2);
  
   fill(128,128,255);
  rect(0,height/2,width/2,height/2);
  
   fill(128,128,128);
    rect(width/2,height/2,width/2,height/2);
}

void mousePressed(){
 if(mouseX<width/2 && mouseY<height/2)
 {
   t.setColor(255,128,128,255);
 }
 else if(mouseX>=width/2 && mouseY<height/2)
 {
    t.setColor(128,255,128,255);
 }
 else if(mouseX<width/2 && mouseY>=height/2)
 {
    t.setColor(128,128,255,255);
 }
 else if(mouseX>width/2 && mouseY>=height/2)
 {
   t.setColor(128,128,128,255);
 }
 
  
}
