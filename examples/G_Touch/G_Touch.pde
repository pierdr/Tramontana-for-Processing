/****
Tramontana for Processing
Tramontana is a tool for interactive spaces. It communicates with iOS devices. 
You can download the app here: https://itunes.apple.com/us/app/tramontana/id1121069555?mt=8
made by Pierluigi Dalla Rosa
***/

import tramontana.library.*;
import websockets.*;

Tramontana t;
boolean isTouchedDown;
PVector touchedDown;
PVector touchedUp;
float   touchUpEvent=0;

void setup(){
   size(320,568,P3D);
   t = new Tramontana(this,"192.168.1.11");
   t.subscribeTouch();
   touchedDown = new PVector(-100,-100);
   touchedUp = new PVector(-100,-100);
}

void draw(){
  background((isTouchedDown)?0:255);
  
  if(millis()-touchUpEvent<3000 || isTouchedDown)
  {
      fill(128,230,230);
      ellipse(touchedDown.x,touchedDown.y,10,10);
      if(!isTouchedDown)
      {
        fill(230,128,128);
        ellipse(touchedUp.x,touchedUp.y,10,10);
      }
  }
}

void onTouchDownEvent(String ipAddress, int x, int y)
{
  isTouchedDown = true;
  touchedDown.x =x;
  touchedDown.y =y;
}

void onTouchEvent(String ipAddress, int x, int y)
{
  isTouchedDown = false;
  touchedUp.x = x;
  touchedUp.y = y;
  touchUpEvent = millis();
}
