/****
Tramontana for Processing
Tramontana is a tool for interactive spaces. It communicates with iOS devices. You can download the app here: https://itunes.apple.com/us/app/tramontana/id1121069555?mt=8
made by Pierluigi Dalla Rosa

D_MotionSensor
With this sketch you can listen to the motion sensor of your device. 
One mouse click corresponds to a haptic vibration.

Don't forget to change the ipAddress below (192.168.1.17) to match the one visible on your device when you open the app.
***/

import tramontana.library.*;
import websockets.*;

Tramontana t;
float roll;
float pitch;
float yaw;



void setup(){
 size(480,240,P3D);
 t = new Tramontana(this,"192.168.1.18");
 
}
void draw(){
  background(255);
  fill(128);
  pushMatrix();
  translate(width/2,height/2);
  rotateX(roll);
  rotateY(pitch);
  rotateZ(yaw);
  box(100);
  popMatrix();
}
void mousePressed(){
  t.subscribeAttitude(5);
}

void onAttitudeEvent(String ipAddress, float newRoll, float newPitch, float newYaw)
{
  roll  =  newRoll;
  pitch =  newPitch;
  yaw   =  newYaw;
}
