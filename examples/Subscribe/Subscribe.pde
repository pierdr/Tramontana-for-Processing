import tramontana.library.*;
import websockets.*;

Tramontana t;
float roll;
float pitch;
float yaw;



void setup(){
 size(480,240,P3D);
 t = new Tramontana(this,"192.168.1.10");
 
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