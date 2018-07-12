/****
Tramontana for Processing
Tramontana is a tool for interactive spaces. It communicates with iOS devices. You can download the app here: https://itunes.apple.com/us/app/tramontana/id1121069555?mt=8
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
 /* Start the connection with Tramontana iOS/AppleTV */
 t = new Tramontana(this,"10.0.1.4");
}
void draw(){
  background(255);
  fill(128);
  text("Hello Tramontana!",width/2-(textWidth("Hello Tramontana!")/2),height/2);
}
void keyPressed(){
  /*  */
  if(key=='q')
  {
    t.setColorEmbedded(2,3,20,3);
  }
  else if(key=='a')
  {
    t.setAllColorEmbedded(0,0,0);
     t.setColorEmbedded(2,0,0,0);
  }
  else if(key=='w')
  {
    t.setServoEmbedded(1,168);
  }
  else if(key=='s')
  {
     t.setServoEmbedded(1,0);
  }
  else if(key=='e')
  {
    t.setRelayEmbeddedOn(1);
  }
  else if(key=='d')
  {
     t.setRelayEmbeddedOff(1);
  }
}