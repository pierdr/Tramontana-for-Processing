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
Tramontana t1;

void setup(){
 size(480,240);
 t = new Tramontana(this,"192.168.1.10");
 t1 = new Tramontana(this,"192.168.1.14");
}
void draw(){
  background(255);
  fill(128);
  text("Hello Tramontana!",width/2-(textWidth("Hello Tramontana!")/2),height/2);
}
void mousePressed(){
  /* With this line of code you are subscribing for touch events from the first tramontana */
  t.subscribeTouch();
}
void onTouchEvent(String ipAddress, int x, int y)
{
  /* When the screen of the first tramontana is touched,it sets the color of the screen of the second tramontana */
  t1.setColor(255,0,0,255);
}