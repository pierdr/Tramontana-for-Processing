package tramontana.library;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;


/**
 * Tramontana is a platform for interactive spaces.
 * 
 * @author Pierluigi Dalla Rosa
 * 
 * @example Hello 
 * 
 * 
 */

public class Tramontana {
	
	// sketch is a reference to the parent sketch
	PApplet sketch;
	
	
	private Method onVideoEndEvent;
	private Method onAttitudeEvent;
	private Method onAudioJackEvent;
	private Method onDistanceEvent;
	private Method onMagnetometerEvent;
	private Method onOrientationEvent;
	private Method onPowerSourceEvent;
	private Method onTouchEvent;
	private Method onTouchDownEvent;
	private Method onTouchDragEvent;
	
	private Method onMultiTouchEvent;
	private Method onMultiTouchDownEvent;
	private Method onMultiTouchDragEvent;
	
	private Method gotBatteryUpdate;
	
	private Method onEmbeddedRxEvent;
	private Method onEmbeddedLDRUpdate;
	private Method onEmbeddedBtnsEvent;
	private Method onEmbeddedAnalogUpdate;
	
	
	
	private WebsocketClientEventsTramontana socket;
	WebSocketClient client;
	public String ipAddress;
	private JSONObject workingJson;
	private ArrayList<TVector> workingArray;
	
	public final static String VERSION = "1.0.0";
	

	public Tramontana(PApplet parent,String IP) {
		sketch = parent;
		ipAddress = IP;
		workingArray = new ArrayList<TVector>();
		
		/* COLLECT METHODS ON PROCESSING */
		try {
			onVideoEndEvent = parent.getClass().getMethod("onVideoEndEvent", String.class);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[4];
			params[0] = String.class;
			params[1] = float.class;
			params[2] = float.class;
			params[3]  = float.class;
			onAttitudeEvent = parent.getClass().getMethod("onAttitudeEvent", params);
        } catch (Exception e) {
        		
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[2];
			params[0] = String.class;
			params[1] = int.class;
			
			onAudioJackEvent = parent.getClass().getMethod("onAudioJackEvent", params);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[2];
			params[0] = String.class;
			params[1] = int.class;
			
			onDistanceEvent = parent.getClass().getMethod("onDistanceEvent", params);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[3];
			params[0] = String.class;
			params[1] = int.class;
			params[2] = float.class;
			onMagnetometerEvent = parent.getClass().getMethod("onMagnetometerEvent", params);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[2];
			params[0] = String.class;
			params[1] = int.class;
			onOrientationEvent = parent.getClass().getMethod("onOrientationEvent", params);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[2];
			params[0] = String.class;
			params[1] = int.class;
			onPowerSourceEvent = parent.getClass().getMethod("onPowerSourceEvent", params);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[3];
			params[0] = String.class;
			params[1] = int.class;
			params[2] = int.class;
			onTouchEvent = parent.getClass().getMethod("onTouchEvent", params);
        } catch (Exception e) {
        	
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[3];
			params[0] = String.class;
			params[1] = int.class;
			params[2] = int.class;
			onTouchDragEvent = parent.getClass().getMethod("onTouchDragEvent", params);
        } catch (Exception e) {
        	
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[3];
			params[0] = String.class;
			params[1] = int.class;
			params[2] = int.class;
			onTouchDownEvent = parent.getClass().getMethod("onTouchDownEvent", params);
        } catch (Exception e) {
        	
        		// no such method, or an error.. which is fine, just ignore
        }
		//-------------------------
		try {
			Class<?> params[] = new Class[3];
			params[0] = String.class;
			params[1] = ArrayList.class;
			params[2] = int.class;
			onMultiTouchEvent = parent.getClass().getMethod("onMultiTouchEvent", params);
        } catch (Exception e) {
        	
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[3];
			params[0] = String.class;
			params[1] = ArrayList.class;
			params[2] = int.class;
			onMultiTouchDragEvent = parent.getClass().getMethod("onMultiTouchDragEvent", params);
        } catch (Exception e) {
        		//System.out.println("missing touch drag method");
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[3];
			params[0] = String.class;
			params[1] = ArrayList.class;
			params[2] = int.class;
			onMultiTouchDownEvent = parent.getClass().getMethod("onMultiTouchDownEvent", params);
        } catch (Exception e) {
        	
        		// no such method, or an error.. which is fine, just ignore
        }
		//-------------------------
		try {
			Class<?> params[] = new Class[2];
			params[0] = String.class;
			params[1] = float.class;
			gotBatteryUpdate = parent.getClass().getMethod("gotBatteryUpdate", params);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		/****
		EMBEDDED
		****/
		//-------------------------
		try {
			Class<?> params[] = new Class[2];
			params[0] = String.class;
			params[1] = String.class;
			onEmbeddedRxEvent = parent.getClass().getMethod("onEmbeddedRxEvent", params);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		//-------------------------
		
		try {
			Class<?> params[] = new Class[2];
			params[0] = String.class;
			params[1] = int.class;
			onEmbeddedLDRUpdate = parent.getClass().getMethod("onEmbeddedLDRUpdate", params);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		//-------------------------
		
		try {
			Class<?> params[] = new Class[2];
			params[0] = String.class;
			params[1] = int.class;
			onEmbeddedAnalogUpdate = parent.getClass().getMethod("onEmbeddedAnalogUpdate", params);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		
		//-------------------------
				
		try {
			Class<?> params[] = new Class[3];
			params[0] = String.class;
			params[1] = int.class;
			params[2] = int.class;
			onEmbeddedBtnsEvent = parent.getClass().getMethod("onEmbeddedBtnsEvent", params);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		/* WEBSOCKET START */
		connectToSocket("ws://"+IP+":9092");
	}
	private void connectToSocket(String endpointURI) {
		client = new WebSocketClient();
		try {
			Method onMessageEvent = this.getClass().getMethod("onMessageEvent",	 String.class);
			socket = new WebsocketClientEventsTramontana(this, onMessageEvent);
			client.start();
			URI echoUri = new URI(endpointURI);
			ClientUpgradeRequest request = new ClientUpgradeRequest();
			client.connect(socket, echoUri, request);
			socket.getLatch().await();

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	/**
	 * Reconnect can be called if you lose connection with a tramontana node.
	 */
	public void reconnect()
	{
		connectToSocket("ws://"+ipAddress+":9092");
	}
	/**
	 * @deprecated  Don't use sendMessage.
	 * 
	 */
	public void sendMessage(String msg)
	{
		try {
			socket.sendMessage(msg);
		}
		catch(Exception e)
		{
			System.out.println(e);
			//reconnect?
		}
	}
	/**
	 * @deprecated  Don't listen to onMessageEvent.
	 * 
	 */
	public void onMessageEvent(String msg) {
//		System.out.println(msg);
		try {
			workingJson = sketch.parseJSONObject(msg);
		}catch(Exception e)
		{
			System.out.println(e);
			return;
		}
		String event = (String) workingJson.get("m");
			if(event.contains("videoEnded")) 
			{
				try {	
					onVideoEndEvent.invoke(sketch, ipAddress);
				}
				catch (Exception e) {
				}
			}
			else if(event.equals("a"))
			{
				
				try {	
					onAttitudeEvent.invoke(sketch,ipAddress,PApplet.parseFloat( (String) workingJson.get("r")),PApplet.parseFloat( (String)workingJson.get("p")),PApplet.parseFloat( (String)workingJson.get("y")));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else if(event.contains("orientationChanged"))
			{
				
				try {	
					onOrientationEvent.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("value")));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else if(event.contains("audioJackChanged"))
			{
				try {	
					onAudioJackEvent.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("in")));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else if(event.contains("distanceChanged"))
			{
				try {	
					onDistanceEvent.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("proximity")));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else if(event.contains("magnetometerUpdate"))
			{
				try {	
					onMagnetometerEvent.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("t")),PApplet.parseFloat((String)workingJson.get("i")));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else if(event.contains("powerSourceChanged"))
			{
				try {	
					onPowerSourceEvent.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("source")));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else if(event.contains("battery"))
			{
				try {	
					gotBatteryUpdate.invoke(sketch,ipAddress,PApplet.parseFloat((String)workingJson.get("v")));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			
			else if(event.equals("touched"))
			{
				try {	
					if(workingJson.hasKey("ts"))
					{
						JSONArray arrTmp = workingJson.getJSONArray("ts");
						workingArray.clear();
						for(int i=0;i<arrTmp.size();i++)
						{
							workingArray.add(new TVector(PApplet.parseInt((String)arrTmp.getJSONObject(i).get("x")),PApplet.parseInt((String)arrTmp.getJSONObject(i).get("y"))));
						}
						onMultiTouchEvent.invoke(sketch,ipAddress,workingArray,(int)arrTmp.size());
					}
					else
					{
						onTouchEvent.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("x")),PApplet.parseInt((String)workingJson.get("y")));
					}
				}
				catch (Exception e) {
					
				}
			}
			else if(event.equals("drag"))
			{
				try {
					
					if(workingJson.hasKey("ts"))
					{
						
						JSONArray arrTmp = workingJson.getJSONArray("ts");
						
						workingArray.clear();
						for(int i=0;i<arrTmp.size();i++)
						{
							workingArray.add(new TVector(PApplet.parseInt( (String)arrTmp.getJSONObject(i).get("x") ),PApplet.parseInt((String) arrTmp.getJSONObject(i).get("y") ) ) );
						}
						
						onMultiTouchDragEvent.invoke(sketch,ipAddress,workingArray,PApplet.parseInt(arrTmp.size()));
					}
					else
					{
						onTouchDragEvent.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("x")),PApplet.parseInt((String)workingJson.get("y")));
					}
				}
				catch (Exception e) {
					
				}
			}
			else if(event.equals("touchedDown"))
			{
				try {
					if(workingJson.hasKey("ts"))
					{
						JSONArray arrTmp = workingJson.getJSONArray("ts");
						workingArray.clear();
						for(int i=0;i<arrTmp.size();i++)
						{
							workingArray.add(new TVector(PApplet.parseInt((String)arrTmp.getJSONObject(i).get("x")),PApplet.parseInt((String)arrTmp.getJSONObject(i).get("y"))));
						}
						onMultiTouchDownEvent.invoke(sketch,ipAddress,workingArray,(int)arrTmp.size());
					}
					else
					{
						onTouchDownEvent.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("x")),PApplet.parseInt((String)workingJson.get("y")));
					}
				}
				catch (Exception e) {
					
				}
			}
			else if(event.equals("rx"))
			{
				try {	
					onEmbeddedRxEvent.invoke(sketch,ipAddress,(String)workingJson.get("v"));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else if(event.equals("btn"))
			{
				try {	
					onEmbeddedBtnsEvent.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("n")),PApplet.parseInt((String)workingJson.get("v")));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else if(event.equals("ldr"))
			{
				try {	
					onEmbeddedLDRUpdate.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("v")));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else if(event.equals("analog"))
			{
				try {	
					onEmbeddedAnalogUpdate.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("n")),PApplet.parseInt((String)workingJson.get("v")));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			
			
		
	}
	/* EMBEDDED */
	
	
	
	/**
	 * SetServoEmbedded is a method to control servo motors on tramontana boards.                           
	 * <p>
	 *
	 * @param  servoIndex will define the index of the servo you want to control          
	 * @param  the second parameter is a number between 0 and 180.
	 */
	public void setServoEmbedded(int servoIndex, int value) {
		
		socket.sendMessage("{\"m\":\"srv\",\"n\":"+servoIndex+",\"v\":"+value+"}");
	}
	/**
	 * setRelayEmbedded is a method to deactivate relay modules on tramontana boards.                           
	 * <p>
	 *
	 * @param  relayIndex will define the index of the relay you want to turn OFF          
	 * 
	 */
	public void setRelayEmbeddedOff(int relayIndex) {
		socket.sendMessage("{\"m\":\"rel\",\"n\":\""+relayIndex+"\",\"v\":\"0\"}");
	}
	/**
	 * setRelayEmbeddedOn is a method to activate relay modules on tramontana boards.                           
	 * <p>
	 *
	 * @param  relayIndex will define the index of the relay you want to turn ON          
	 * 
	 */
	public void setRelayEmbeddedOn(int relayIndex) {
		socket.sendMessage("{\"m\":\"rel\",\"n\":\""+relayIndex+"\",\"v\":\"1\"}");
	}
//	public void setRelayEmbedded(int relayIndex, int value) {
//		socket.sendMessage("{\"m\":\"rel\",\"n\":"+relayIndex+",\"v\":"+(int)Math.round(value)+"}");
//	}
	/**
	 * sendSerialMessageEmbedded allows to connect a tramontana board with an other serial board like an Arduino.                           
	 * <p>
	 *
	 * @param  relayIndex will define the index of the relay you want to control          
	 * @param  the second parameter can be 0 (relay off) and 1 (relay on).
	 */
	public void sendSerialMessageEmbedded(String msg)
	{
		socket.sendMessage("{\"m\":\"tx\",\"v\":\""+msg+"\"}");
	}
	/**
	 * setColorEmbedded allows to change the color of leds connected to tramontana board (led should be WS2812). The index number 0 is controlling the onboard LED.                          
	 * <p>
	 *
	 * @param  ledIndex will define the index of the led you want to control          
	 * @param  red component.
	 * @param  blue component.
	 * @param  green component.
	 */
	
	public void setColorEmbedded(int ledIndex,int red, int green, int blue) {
		socket.sendMessage("{\"m\":\"col\",\"n\":\""+ledIndex+"\",\"r\":\""+Math.floor(red)+"\",\"g\":\""+Math.floor(green)+"\",\"b\":\""+Math.floor(blue)+"\"}");
	}
	/**
	 * blinkColorEmbedded works similar to {@link #setColorEmbedded(int ledIndex,int red, int green, int blue) setColorEmbedded}.                          
	 * <p>
	 *
	 * @param  ledIndex will define the index of the led you want to control          
	 * @param  red component.
	 * @param  blue component.
	 * @param  green component.
	 */
	public void blinkColorEmbedded(int ledIndex,int red, int green, int blue)
	{
		socket.sendMessage("{\"m\":\"blk\",\"n\":\""+ledIndex+"\",\"r\":\""+Math.floor(red)+"\",\"g\":\""+Math.floor(green)+"\",\"b\":\""+Math.floor(blue)+"\"}");
	}
	/**
	 * setAllColorEmbedded will affect all the leds connected to a tramontana board (max 255).                          
	 * <p>
	 *          
	 * @param  red component.
	 * @param  blue component.
	 * @param  green component.
	 */
	public void setAllColorEmbedded(int red, int green, int blue) {
		socket.sendMessage("{\"m\":\"all\",\"r\":\""+Math.floor(red)+"\",\"g\":\""+Math.floor(green)+"\",\"b\":\""+Math.floor(blue)+"\"}");;
	}
	/* ACTUATION */
	/**
	 * Actuate the haptic engine.
	 * 
	 * 
	 */
	public void makeVibrate() {
		
		sendMessage("{\"m\":\"makeVibrate\"}");
				
	}
	/**
	 * Change iDevice brightness. brightness should be between 0.0 and 1.0
	 * 
	 * 
	 */
	public void setBrightness(float brightness)
	{
		if(brightness<=1.0)
		{
		sendMessage("{\"m\":\"setBrightness\",\"b\":\""+brightness+"\"}");
		}
		else {
			System.out.println("brightness should be between 0.0 and 1.0");
		}
	}
	/**
	 * Change the screen color. 
	 * Parameters 'int red, int green, int blue, int intensity' should be between 0 and 255
	 * 
	 * 
	 */
	public void setColor(int red, int green, int blue, int intensity) {
		sendMessage("{\"m\":\"setColor\",\"r\":\""+((float)red/255.0)+"\",\"g\":\""+((float)green/255.0)+"\",\"b\":\""+((float)blue/255.0)+"\",\"a\":\""+((float)intensity/255.0)+"\"}");
	}
	/**
	 * Change the screen color. 
	 * Parameters 'float red, float green, float blue, float intensity' should be between 0.0 and 1.0
	 * 
	 * 
	 */
	public void setColor(float red, float green, float blue, float intensity) {
		if(red<=1.0 && green<=1.0 && blue<=1.0 && intensity<=1.0)
		{
			sendMessage("{\"m\":\"setColor\",\"r\":\""+red+"\",\"g\":\""+green+"\",\"b\":\""+blue+"\",\"a\":\""+intensity+"\"}");
		}
		else
		{
			System.out.println("the inputs for setColor should be between 0.0 and 1.0");
		}
	}
	/**
	 * Actuate the flash light. Input value between 0.0 (off) and 1.0 (full brightness)
	 * 
	 * 
	 */
	public void setFlashLight(float onValue)
	{
		sendMessage("{\"m\":\"setLED\",\"value\":\""+((onValue>0.0)?1:0)+"\",\"in\":\""+onValue+"\"}");
	}
	/**
	 * Pulse the led light for the numberOfPulses (first parameter).
	 * Each pulse will have the duration specified in the second parameter.
	 * The last parameter determines the intensity of the LED.
	 * 
	 * 
	 */
	public void pulseFlashLight(int numberOfPulses, float duration,float intensity)
	{
		sendMessage("{\"m\":\"pulseLED\",\"t\":\""+numberOfPulses+"\",\"d\":\""+duration+"\",\"i\":\""+intensity+"\"}");
	}
	/**
	 * Display an image from URL (must be PNG, JPEG or GIF[won't be animated, send videos instead]).
	 * Image URL can be from the internet or local file (update through iTunes).
	 * 
	 */
	public void showImage(String url)
	{
		sendMessage("{\"m\":\"showImage\",\"url\":\""+url+"\"}");
	}
	/**
	 * Play a sound file (must be MP3 or WAV or AIF).
	 * The URL of audio files can be from the internet or local file (update through iTunes).
	 * 
	 */
	public void playAudio(String url)
	{
		sendMessage("{\"m\":\"playAudio\",\"url\":\""+url+"\"}");
	}
	/**
	 * Play a video file (must be MP4).
	 * The URL of video files can be from the internet or local file (update through iTunes).
	 * When the end of the video is reached the device will invoke the method onVideoEndEvent
	 * 
	 */
	public void playVideo(String url)
	{
		sendMessage("{\"m\":\"playVideo\",\"url\":\""+url+"\"}");
	}
	/**
	 * takePicture will take a picture with your camera. It is possible to choose the parameters:
	 * @param 
	 * (int) 		which camera 0 frontal camera, 1 back camera
	 * 
	 * 
	 */
	public void takePicture() {
		sendMessage("{\"m\":\"takePicture\"}");
	}
	
	public void takePicture(int camera) {
		sendMessage("{\"m\":\"takePicture\",\"c\":\""+camera+"\"");
	}
	/**
	 * takePictureWithUI  will take a picture showing the modal view provided by iOS for taking pictures.
	 * @param 
	 * (int) 		which camera 0 frontal camera, 1 back camera
	 * 
	 * 
	 */
	public void takePictureWithUI(int camera) {
		
			sendMessage("{\"m\":\"takePicture\",\"c\":\""+camera+"\",\"i\":\"ui\"}");
		
	}
	/**
	 * Provide two colors and duration. The screen of your iDevice will be changing the color screen accordingly.
	 * 
	 * 
	 */
	public void transitionColors(float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2, float duration) {
		sendMessage("{\"m\":\"transitionColors\",\"r1\":\""+r1+"\",\"g1\":\""+g1+"\",\"b1\":\""+b1+"\",\"a1\":\""+a1+"\",\"r2\":\""+r2+"\",\"g2\":\""+g2+"\",\"b2\":\""+b2+"\",\"a2\":\""+a2+"\",\"duration\":\""+duration+"\"}");
	}
	
	/* INFO */
	public void getBatteryLevel() {
		sendMessage("{\"m\":\"getBattery\"}");
	}
	/* OSC - WEKINATOR */
	/**
	 * Activate the OSC engine to send accelerometer information, useful to communicate with wekinator for example or a live performance installation that requires better realtime performances than websockets.
	 * 
	 * 
	 */
	public void sendAttitudeDataToOSC(float frequency, int port) {
		try {
			sendAttitudeDataToOSC(frequency,port,java.net.InetAddress.getLocalHost().getHostAddress());
			
		} catch(Exception e) {
			System.out.println("sendAttitudeDataToOSC error - impossible to retrieve local host address:\n "+e);
		}
		
	}
	public void sendAttitudeDataToOSC(float frequency, int port,String ip) {
		sendMessage("{\"m\":\"a2w\",\"p\":\""+port+"\",\"i\":\""+ip+"\",\"f\":\""+frequency+"\"}");
	}
	/**
	 * Activate the OSC engine to send touch data, useful to communicate with wekinator for example or a live performance installation that requires better realtime performances than websockets.
	 * 
	 * 
	 */
	public void sendTouchDataToOSC(int maxNumFingers) {
		sendTouchDataToOSC(maxNumFingers,9093);
	}
	public void sendTouchDataToOSC(int maxNumFingers,int port) {
		try {
			sendTouchDataToOSC(maxNumFingers,port,java.net.InetAddress.getLocalHost().getHostAddress());
			
		} catch(Exception e) {
			System.out.println("sendTouchDataToOSC error - impossible to retrieve local host address:\n "+e);
		}
		
	}
	public void sendTouchDataToOSC(int maxNumFingers,int port,String ip) {
		sendMessage("{\"m\":\"t2w\",\"p\":\""+port+"\",\"i\":\""+ip+"\",\"n\":\""+maxNumFingers+"\"}");
	}
	public void stopAttitudeDataToOSC()
	{
		sendMessage("{\"m\":\"sa2w\"}");
	}
	public void stopTouchDataToOSC()
	{
		sendMessage("{\"m\":\"st2w\"}");
	}
	
	/* SENSING */
	/**
	 * Subscribe to the distance sensor.
	 * 
	 * 
	 */
	public void subscribeDistance() {
		sendMessage("{\"m\":\"registerDistance\"}");
	}
	
	public void releaseDistance() {
		sendMessage("{\"m\":\"releaseDistance\"}");
	}
	/**
	 * Subscribe to the touch events. Doesn't support multitouch.
	 * 
	 * 
	 */
	public void subscribeTouch() {
		sendMessage("{\"m\":\"registerTouch\"}");
	}
	public void subscribeTouch(Boolean multitouch) {
		
		sendMessage("{\"m\":\"registerTouch\""+((multitouch)?",\"multi\":\"1\"":"")+"}");
	}
	public void releaseTouch() {
		sendMessage("{\"m\":\"releaseTouch\"}");
	}
	public void subscribeTouchDrag() {
		sendMessage("{\"m\":\"registerTouchDrag\"}");
	}
	public void subscribeTouchDrag(Boolean multitouch) {
		sendMessage("{\"m\":\"registerTouchDrag\" "+((multitouch)?",\"multi\":\"1\"":"")+" }");
	}
	public void releaseTouchDrag() {
		sendMessage("{\"m\":\"releaseTouchDrag\"}");
	}
	/**
	 * Subscribe to Motion sensor (accelerometer).
	 * @param
	 * the parameter specify the update frequency in Hz. Keep it low, suggested: 5Hz, default: 1Hz
	 * {@code Test}
	 * 
	 */
	public void subscribeAttitude() {
		sendMessage("{\"m\":\"registerAttitude\",\"f\":\"1\"}");
	}
	public void subscribeAttitude(int frequency) {
		sendMessage("{\"m\":\"registerAttitude\",\"f\":\""+frequency+"\"}");
	}
	public void releaseAttitude() {
		sendMessage("{\"m\":\"releaseAttitude\"}");
	}
	/**
	 * Be notified if the audio jack is inserted or removed.
	 * 
	 * 
	 */
	public void subscribeAudioJack() {
		sendMessage("{\"m\":\"registerAudioJack\"}");
	}
	public void releaseAudioJack() {
		sendMessage("{\"m\":\"releaseAudioJack\"}");
	}
	/**
	 * Be notified if the power source change.
	 * 
	 * 
	 */
	public void subscribePowerSource() {
		sendMessage("{\"m\":\"registerPowerSource\"}");
	}
	public void releasePowerSource() {
		sendMessage("{\"m\":\"releasePowerSource\"}");
	}
	/**
	 * Subscribe to magnetometer. The magnetometer is filtering low magnetic fields. You will be notified if a consistent magnetic field is near your iDevice.
	 * 
	 * 
	 */
	public void subscribeMagnetometer() {
		sendMessage("{\"m\":\"registerMagnetometer\"}");
	}
	public void releaseMagnetometer() {
		sendMessage("{\"m\":\"releaseMagnetometer\"}");
	}
	/**
	 * Subscribe to orientation change.
	 * 
	 * 
	 */
	public void subscribeOrientation() {
		sendMessage("{\"m\":\"registerOrientation\"}");
	}
	public void releaseOrientation() {
		sendMessage("{\"m\":\"releaseOrientation\"}");
	}
	
	

	
	/**
	 * 
	 * EMBEDDED
	 * Subscribe RX will make Tramontana NANO or Tramontana PICO act as proxy for incoming messaging coming on the serial port
	 * It is possible to intercept the messages from Processing with the method:
	 *  
	 *  
	 *  void onEmbeddedRxEvent(String ip,String msg)
	 *  {
	 *  		println("serial message received:"+msg);
	 *  }
	 * 
	 */
	public void subscribeRxEmbedded() {
		sendMessage("{\"m\":\"srx\"}");
	}
	public void releaseRxEmbedded() {
		sendMessage("{\"m\":\"drx\"}");
	}
	/**
	 * 
	 * EMBEDDED
	 * Listen for buttons event from Tramontana NANO or Tramontana PICO.
	 * The second parameter (btnIndex) identify which of the two buttons and the third parameter (btnValue) correspond to the state of the button, 1 pressed, 0 depressed. 
	 * 
	 * void onEmbeddedBtnsEvent(String ip,int btnIndex, int btnValue)
	 *  {
	 *  		println("serial message received:"+msg);
	 *  }
	 * 
	 */
	public void subscribeButtonsEventEmbedded() {
		sendMessage("{\"m\":\"sbtn\"}");
	}
	public void releaseButtonsEventEmbedded() {
		sendMessage("{\"m\":\"dbtn\"}");
	}
	/**
	 * 
	 * EMBEDDED
	 *  You can subscribe to the analog input on Tramontana NANO or Tramontana PICO.
	 *  The board will send an updated reading with the specified frequency.
	 *  The value is received by the method:
	 *  void onEmbeddedAnalogUpdate(String ip, int value)
	 *  {
	 *  		println(value);
	 *  }
	 *  
	 */
	public void subscribeAnalogEmbedded(int frequency) {
		if(frequency>0 )
		{
			sendMessage("{\"m\":\"sanalog\",\"f\":\""+frequency+"\"}");
		}
		else
		{
			//frequency must be greater than 0
		}
	}
	
	public void releaseAnalogEmbedded() {
		sendMessage("{\"m\":\"danalog\"}");
	}
	/**
	 * 
	 * EMBEDDED
	 * You can subscribe to the integrated LDR on Tramontana NANO or Tramontana PICO.
	 *  The board will send an updated reading of the light value with the specified frequency.
	 * 
	 *  void onEmbeddedLDRUpdate(String ip, int value)
	 *  {
	 *  		println(value);
	 *  }
	 */
	public void subscribeLDREmbedded(int frequency) {
		if(frequency>0 )
		{
			sendMessage("{\"m\":\"sldr\",\"f\":\""+frequency+"\"}");
		}
		else
		{
			//frequency must be greater than 0
		}
	}
	
	public void releaseLDREmbedded() {
		sendMessage("{\"m\":\"dldr\"}");
	}
	/**
	 * return the version of the Library.
	 * 
	 * @return String
	 */
	public static String version() {
		return VERSION;
	}
	
	
	
	/**
	 * 
	 * @param theA
	 *          the width of test
	 * @param theB
	 *          the height of test
	 */
	

	/**
	 * 
	 * @return int
	 */
	
}

