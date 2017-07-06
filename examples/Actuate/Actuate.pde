import tramontana.library.*;
import websockets.*;

Tramontana t;


void setup(){
 size(480,240);
 t = new Tramontana(this,"192.168.1.10");
 
}
void draw(){
  background(255);
  fill(255,128,128);
  noStroke();
  rect(0,0,width/2,height/2);
  
   fill(128,255,128);
  rect(width/2,0,width/2,height/2);
  
   fill(128,128,255);
  rect(0,height/2,width/2,height/2);
  
   fill(128,128,128);
    rect(width/2,height/2,width/2,height/2);
}

void mousePressed(){
 if(mouseX<width/2 && mouseY<height/2)
 {
   t.setColor(255,128,128,255);
 }
 else if(mouseX>=width/2 && mouseY<height/2)
 {
    t.setColor(128,255,128,255);
 }
 else if(mouseX<width/2 && mouseY>=height/2)
 {
    t.setColor(128,128,255,255);
 }
 else if(mouseX>width/2 && mouseY>=height/2)
 {
   t.setColor(128,128,128,255);
 }
 
  
}