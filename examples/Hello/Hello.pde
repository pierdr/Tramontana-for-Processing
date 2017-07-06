/****
Tramontana for Processing
made by Pierluigi Dalla Rosa
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
 
 /* Initiate a connection with your Tramontana node */
 t = new Tramontana(this,"192.168.1.10");
}
void draw(){
  background(255);
  fill(128);
  text("Hello Tramontana!",width/2-(textWidth("Hello Tramontana!")/2),height/2);
}
void mousePressed(){
  /* On mouse pressed trigger the haptic motor of your iDevice */
  t.makeVibrate();
  
}