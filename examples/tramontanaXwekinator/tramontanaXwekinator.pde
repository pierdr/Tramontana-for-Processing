/****
Tramontana for Processing
Tramontana is a tool for interactive spaces. It communicates with iOS devices. 
You can download the app here: https://itunes.apple.com/us/app/tramontana/id1121069555?mt=8
made by Pierluigi Dalla Rosa
***/
///IMPORT TRAMONTANA and WEBSOCKET
import tramontana.library.*;
import websockets.*;
///IMPORT OSC Library in order to communicate with WEKINATOR
import oscP5.*;

Tramontana t;

OscP5 oscP5;

void setup()
{
  /* Start the connection with Tramontana iOS */
  t = new Tramontana(this,"192.168.1.13");
  
  /* Send attitude to wekinator.
  parameters:
  t.sendAttitudeDataToOSC((required)FREQUENCY,(required)PORT,(optional)IPADDRESS);
  
  if IPADDRESS is omitted then the library will assume that wekinator is on the same machine as the processing sketch.
  */
  t.sendAttitudeDataToOSC(15,9090);
  
  
  /* Send touch to wekinator.
  parameters:
  t.sendTouchDataToOSC((required)NUM_OF_FINGERS,(required)PORT,(optional)IPADDRESS);
  
  · NUM_OF_FINGERS determines how many fingers you want to forward to Wekintor/OSC. 
  When reading the finger data from Wekinator specify 2xNUM_OF_FINGERS+1 inputs. 
  So if I want to send touch data relative to 3 fingers the amount of inputs will be 7 (3 fingers * 2 (number of components (x,y)) +1(absolute number of fingers))
  
  · if IPADDRESS is omitted then the library will assume that wekinator is on the same machine as the processing sketch.
  
  */
  //t.sendTouchDataToOSC(3,9090);
  
  oscP5 = new OscP5(this,12000);
  
}
void draw()
{
  
}
void oscEvent(OscMessage theOscMessage) {
 if (theOscMessage.checkAddrPattern("/wek/outputs")==true) {
   println(theOscMessage.get(0).floatValue() + " "+theOscMessage.get(1).floatValue());
   
   if(theOscMessage.get(0).floatValue()>theOscMessage.get(1).floatValue())
   {
     t.setColor(128,128,255,128);
   }
   else
   {
     t.setColor(128,255,128,128);
   }
   
 }
}
void mousePressed(){
  //** stop sending attitude data to Wekinator/OSC **//
  t.stopAttitudeDataToOSC();
  
  //** stop sending touch data to Wekinator/OSC **//
  //t.stopTouchDataToOSC();
}