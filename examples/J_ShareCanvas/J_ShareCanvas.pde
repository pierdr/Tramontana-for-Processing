/****
Tramontana for Processing
Tramontana is a tool for interactive spaces. It communicates with iOS and Android devices. 
check tramontana.xyz
made by Pierluigi Dalla Rosa
***/

///IMPORT TRAMONTANA and WEBSOCKET
import tramontana.library.*;
import websockets.*;

///IMPORT WEB SERVER
import processing.net.*;

Tramontana t;

void setup()
{
  size(768,1024);
  s = new Server(this,1026); //start server on http-alt
  t = new Tramontana(this, "10.0.1.51");
}


void draw()
{
  
  background(128,128,255);
  noStroke();
  for(int i = 0; i< 10;i++)
  {
    
    fill( map(i,0,10,0, 128), map(i,0,10,128,255),255 );
    float w = map(i,0,10,width,width*0.25);
    float h = map(i,0,10,height,height*0.25);
    rect((width-w)/2,(height-h)/2,w,h);
  }
  fill(255,255,0);
  ellipse(mouseX,mouseY,100,100);
  
  updateServer();
}
void keyPressed(){
  sendFrame();
}


//---------------------------------- UTILS METHODS -------------------------------------------
//You can ignore this part (it just creates a web server and serves the frame (save as img) to the device
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

Server s;
Client c;
String input;
String HTTP_GET_REQUEST = "GET /";
String HTTP_HEADER = "HTTP/1.0 200 OK\r\nContent-Type: image/jpeg\r\n\r\n";
byte img[];

void sendFrame()
{
  saveFrame("img.jpg");
  img = loadBytes("img.jpg");
  t.showImage("http://"+getIPAddress()+":1026/"+random(123));
}
void sendFrame(PGraphics p)
{
  p.save("img.jpg");
  img = loadBytes("img.jpg");
  t.showImage("http://"+getIPAddress()+":1026/"+random(123));
}
void updateServer()
{
  
  c = s.available();
  if(c != null)
  {
    println("updated request");
    input = c.readString();
    println(input);
    input = input.substring(0, input.indexOf("\n")); 
    println(input);
    if(input.indexOf(HTTP_GET_REQUEST) == 0)
    {
      c.write(HTTP_HEADER); //answer that we are going to send the image
      c.write(img);         // send the image
      c.stop();             //close client connection
    }
  }
}
String getIPAddress()
{
  String ip = "";
  try {
      // Replace eth0 with your interface name
      NetworkInterface i = NetworkInterface.getByName("en0");
  
      if (i != null) {
  
          Enumeration<InetAddress> iplist = i.getInetAddresses();
  
          InetAddress addr = null;
  
          while (iplist.hasMoreElements()) {
              InetAddress ad = iplist.nextElement();
              byte bs[] = ad.getAddress();
              if (bs.length == 4 && bs[0] != 127) {
                  addr = ad;
                  // You could also display the host name here, to 
                  // see the whole list, and remove the break.
                  break;
              }
          }
  
          if (addr != null) {
            ip = addr.getCanonicalHostName();
              System.out.println( addr.getCanonicalHostName() );
          }
      }
  }catch (Exception e) { 
      
  }
  return ip;
}
