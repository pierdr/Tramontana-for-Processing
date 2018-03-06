/****
Tramontana for Processing
Tramontana is a tool for interactive spaces. It communicates with iOS devices, Mac computers and Tramontana boards. You can download the app here: https://itunes.apple.com/us/app/tramontana/id1121069555?mt=8
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
 /* Start the connection with Tramontana Embedded */
 t = new Tramontana(this,"10.0.1.4");
}
void draw(){
  background(255);
  fill(128);
  text("Hello Tramontana!",width/2-(textWidth("Hello Tramontana!")/2),height/2);
}
void keyPressed(){
  /* SUBSCRIBE AND RELEASE */
  if(key=='q')
  {
    t.subscribeButtonsEventEmbedded();
  }
  else if(key=='a')
  {
     t.releaseButtonsEventEmbedded();
  }
  else if(key=='w')
  {
    t.subscribeLDREmbedded(10);
  }
  else if(key=='s')
  {
     t.releaseLDREmbedded();
  }
}
void onEmbeddedBtnsEvent(String ipaddress,  int btnNum, int val)
{
  println("Button event from btn #"+btnNum+" and value: "+val);
}

void onEmbeddedLDRUpdate(String ipaddress,int val)
{
  println("LDR:"+val);
}