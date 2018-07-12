/****
Tramontana for Processing
Tramontana is a tool for interactive spaces. It communicates with iOS devices. 
You can download the app here: https://itunes.apple.com/us/app/tramontana/id1121069555?mt=8
made by Pierluigi Dalla Rosa
***/

import tramontana.library.*;
import websockets.*;

Tramontana t;
String allSensors = "";
String messageReceived = "";
String subscribedTo    = "";

void setup(){
 size(960,240,P3D);
t = new Tramontana(this,"192.168.8.108");
 
}
void draw(){
  background(255);
  fill(0);
  text("toggle: 1 for attitude, 2 for audioJack, 3 for distance, 4 for magnetometer, 5 for orientation, 6 for powerSource, 7 for touch, 0 for getBatteryLevel",10,height*0.25);
  text(subscribedTo, 10, height*0.5);
  text(messageReceived,10,height*0.75);
}
void keyPressed(){
  // SUBSCRIBE
  if(key == '1')
  {
    if(subscribedTo.indexOf("attitude")==-1)
    {
      //The parameter is the refresh rate in Hz. Be careful, network might slow if refresh rate is too high.
      t.subscribeAttitude(15);
      subscribedTo +="attitude ";
    } 
    else
    {
      t.releaseAttitude();
      subscribedTo.replace("attitude "," ");
    }
  }
  else if(key == '2')
  {
    if(subscribedTo.indexOf("audiojack")==-1)
    {
      t.subscribeAudioJack();
      subscribedTo +="audiojack ";
    } 
    else
    {
      t.releaseAudioJack();
      subscribedTo.replace("audiojack ","");
    }
  }
  else if(key == '3')
  {
    if(subscribedTo.indexOf("distance")==-1)
    {
      t.subscribeDistance();
      subscribedTo +="distance ";
    } 
    else
    {
      t.releaseDistance();
      subscribedTo.replace("distance ","");
    }
    
  }
  else if(key == '4')
  {
    if(subscribedTo.indexOf("magnetometer")==-1)
    {
      t.subscribeMagnetometer();
      subscribedTo +="magnetometer ";
     } 
    else
    {
      t.releaseMagnetometer();
      subscribedTo.replace("magnetometer ","");
    }
  }
  else if(key == '5')
  {
    if(subscribedTo.indexOf("orientation")==-1)
    {
      t.subscribeOrientation();
      subscribedTo +="orientation ";
    } 
    else
    {
      t.releaseOrientation();
      subscribedTo.replace("orientation ","");
    }
  }
  else if(key == '6')
  {
    if(subscribedTo.indexOf("powerSource")==-1)
    {
      t.subscribePowerSource();
       subscribedTo +="powerSource ";
    } 
    else
    {
      t.releasePowerSource();
      subscribedTo.replace("powerSource ","");
    }
  }
  else if(key == '7')
  {
    if(subscribedTo.indexOf("touch")==-1)
    {
      t.subscribeTouch();
      subscribedTo +="touch ";
    } 
    else
    {
      t.releaseTouch();
      subscribedTo.replace("touch ","");
    }
  }
  else if(key == '0')
  {
    t.getBatteryLevel();
  }
  
  
}

void onAttitudeEvent(String ipAddress, float newRoll, float newPitch, float newYaw)
{
  messageReceived = "attitude event from "+ipAddress+" orientation("+newRoll+","+newPitch+","+newYaw+")" ;
}
void onAudioJackEvent(String ipAddress, int audioJackStatus){
  messageReceived = "audio jack changed event from "+ipAddress+" audioJackStatus("+audioJackStatus+")" ;
}
void onDistanceEvent(String ipAddress, int distance)
{
  messageReceived = "distance event from "+ipAddress+" distance("+distance+")" ;
}
void onMagnetometerEvent(String ipAddress, int magnetSwitch, float magnetPower)
{
  messageReceived = "magnetometer changed event from "+ipAddress+" magnetometer("+magnetSwitch+","+magnetPower+")" ;
}
void onOrientationEvent(String ipAddress,int orientation){
  messageReceived = "orientation changed event from "+ipAddress+" orientation("+orientation+")" ;
}
void onPowerSourceEvent(String ipAddress,int powerSource){
  messageReceived = "powerSource changed event from "+ipAddress+" powerSource("+powerSource+")" ;
}
void onTouchEvent(String ipAddress, int x, int y)
{
  messageReceived = "touch event from "+ipAddress+" location("+x+","+y+")" ;
}
void gotBatteryUpdate(String ipAddress, float batteryLevel){
  messageReceived = "batteryLevel received from "+ipAddress+" batteryLevel("+batteryLevel+")" ;
}