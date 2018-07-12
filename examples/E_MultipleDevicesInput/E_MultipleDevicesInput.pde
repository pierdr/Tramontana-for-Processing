/****
Tramontana for Processing
Tramontana is a tool for interactive spaces. It communicates with iOS devices. You can download the app here: https://itunes.apple.com/us/app/tramontana/id1121069555?mt=8
made by Pierluigi Dalla Rosa

C_MultipleDevices
Touch one device to affect the color of the other.

Don't forget to change the ipAddresss below (192.168.1.17, 192.168.1.11) to match the one visible on your devices when you open the app.
***/

///IMPORT TRAMONTANA
import tramontana.library.*;

///Tramontana needs websockets that can be found at:
///https://github.com/alexandrainst/processing_websockets
import websockets.*;

/* Create an instance of Tramonana */
Tramontana tTouch;
Tramontana tChangeColor;

boolean isStarted = false;
color c = 0;

void setup(){
 size(480,240);
 tTouch = new Tramontana(this,"192.168.1.17");
 tChangeColor = new Tramontana(this,"192.168.1.11");
}
void draw(){
  background(255);
  fill(128);
  if(!isStarted)
  {
    fill(128);
    text("Click to start!",width/2-(textWidth("Click to start!")/2),height/2);
    text("When you click you are going to subscribe\n to the touch sensor of one device!",width/2-(textWidth("hen you click you are going to subscribe")/2),height/2+30);
  }
  else
  {
    text("Tap the screen of the green device to change the color of the other",width/2-(textWidth("Tap the screen of the green device to change the color of the other")/2),height/2-100);
    fill(0,255,0);
    rect(width/4-50,height/2-80,100,160);
    
    
    
    fill(c);
    rect((width*0.75)-50,height/2-80,100,160);
     
   
  }
}
void mousePressed(){
  /* With this line of code you are subscribing for touch events from the first tramontana */
  tTouch.subscribeDistance();
  tTouch.setColor(0,255,0,128);
  isStarted = true;
}
void onTouchEvent(String ipAddress, int x, int y)
{
  /* When the screen of the first tramontana is touched,it sets the color of the screen of the second tramontana */
  tChangeColor.setColor(constrain(x/2,0,255),0,constrain(y/2,0,255),180);
  c = color(constrain(x/2,0,255),0,constrain(y/2,0,255));
}
void onDistanceEvent(String ipAddress, int distance)
{
  /* When the screen of the first tramontana is touched,it sets the color of the screen of the second tramontana */
  tChangeColor.setColor(random(0,1.0),0,random(0,1.0),0.5);
  c = color(constrain(distance/2,0,255),0,constrain(distance/2,0,255));
}
