package tramontana.library;
import processing.core.*;

import java.lang.reflect.Method;
import java.net.URI;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;


import processing.core.PApplet;
import processing.data.*;




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
	private Method gotBatteryUpdate;
	private Method onEmbeddedRxEvent;
	
	private WebsocketClientEventsTramontana socket;
	WebSocketClient client;
	public String ipAddress;
	private JSONObject workingJson;
	
	
	public final static String VERSION = "1.0.0";
	

	public Tramontana(PApplet parent,String IP) {
		sketch = parent;
		ipAddress = IP;
		
		
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
			Class<?> params[] = new Class[2];
			params[0] = String.class;
			params[1] = float.class;
			gotBatteryUpdate = parent.getClass().getMethod("gotBatteryUpdate", params);
        } catch (Exception e) {
        		// no such method, or an error.. which is fine, just ignore
        }
		try {
			Class<?> params[] = new Class[2];
			params[0] = String.class;
			params[1] = String.class;
			onEmbeddedRxEvent = parent.getClass().getMethod("onEmbeddedRxEvent", params);
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
	
	public void reconnect()
	{
		connectToSocket("ws://"+ipAddress+":9092");
	}
	
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
	public void onMessageEvent(String msg) {
		System.out.println(msg);
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
					onAudioJackEvent.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("proximity")));
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
			else if(event.equals("rx"))
			{
				try {	
					onEmbeddedRxEvent.invoke(sketch,ipAddress,(String)workingJson.get("v"));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else if(event.contains("touched"))
			{
				try {	
					onTouchEvent.invoke(sketch,ipAddress,PApplet.parseInt((String)workingJson.get("x")),PApplet.parseInt((String)workingJson.get("y")));
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			
			
		
	}
	/* EMBEDDED */
	public void setServoEmbedded(int servoIndex, int value) {
		
		socket.sendMessage("{\"m\":\"srv\",\"n\":"+servoIndex+",\"v\":"+value+"}");
	}
	public void setRelayEmbedded(int relayIndex, int value) {
		socket.sendMessage("{\"m\":\"rel\",\"n\":"+relayIndex+",\"v\":"+Math.round(value)+"}");
	}
	public void sendSerialMessageEmbedded(String msg)
	{
		socket.sendMessage("{\"m\":\"tx\",\"v\":\""+msg+"\"}");
	}
	public void setColorEmbedded(int ledIndex,int red, int green, int blue) {
		socket.sendMessage("{\"m\":\"col\",\"n\":\""+ledIndex+"\",\"r\":\""+Math.floor(red)+"\",\"g\":\""+Math.floor(green)+"\",\"b\":\""+Math.floor(blue)+"\"}");
	}
	public void blinkColorEmbedded(int ledIndex,int red, int green, int blue)
	{
		socket.sendMessage("{\"m\":\"blk\",\"n\":\""+ledIndex+"\",\"r\":\""+Math.floor(red)+"\",\"g\":\""+Math.floor(green)+"\",\"b\":\""+Math.floor(blue)+"\"}");
	}
	public void setAllColorEmbedded(int red, int green, int blue) {
		socket.sendMessage("{\"m\":\"all\",\"r\":\""+Math.floor(red)+"\",\"g\":\""+Math.floor(green)+"\",\"b\":\""+Math.floor(blue)+"\"}");;
	}
	/* ACTUATION */
	/**
	 * Actuate the haptic engine.
	 * 
	 * @return void
	 */
	
	public void makeVibrate() {
		
		sendMessage("{\"m\":\"makeVibrate\"}");
				
	}
	/**
	 * Change iDevice brightness. brightness should be between 0.0 and 1.0
	 * 
	 * @return void
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
	 * @return void
	 */
	public void setColor(int red, int green, int blue, int intensity) {
		sendMessage("{\"m\":\"setColor\",\"r\":\""+((float)red/255.0)+"\",\"g\":\""+((float)green/255.0)+"\",\"b\":\""+((float)blue/255.0)+"\",\"a\":\""+((float)intensity/255.0)+"\"}");
	}
	/**
	 * Change the screen color. 
	 * Parameters 'float red, float green, float blue, float intensity' should be between 0.0 and 1.0
	 * 
	 * @return void
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
	 * @return void
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
	 * @return void
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
	 * @return void
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
	 * @return void
	 */
	public void takePictureWithUI(int camera) {
		
			sendMessage("{\"m\":\"takePicture\",\"c\":\""+camera+"\",\"i\":\"ui\"}");
		
	}
	/**
	 * Provide two colors and duration. The screen of your iDevice will be changing the color screen accordingly.
	 * 
	 * @return void
	 */
	public void transitionColors(float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2, float duration) {
		sendMessage("{\"m\":\"transitionColors\",\"r1\":\""+r1+"\",\"g1\":\""+g1+"\",\"b1\":\""+b1+"\",\"a1\":\""+a1+"\",\"r2\":\""+r2+"\",\"g2\":\""+g2+"\",\"b2\":\""+b2+"\",\"a2\":\""+a2+"\",\"duration\":\""+duration+"\"}");
	}
	/* INFO */
	public void getBatteryLevel() {
		sendMessage("{\"m\":\"getBattery\"}");
	}
	/* SENSING */
	/**
	 * Subscribe to the distance sensor.
	 * 
	 * @return void
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
	 * @return void
	 */
	public void subscribeTouch() {
		sendMessage("{\"m\":\"registerTouch\"}");
	}
	public void releaseTouch() {
		sendMessage("{\"m\":\"releaseTouch\"}");
	}
	/**
	 * Subscribe to Motion sensor (accelerometer).
	 * @param
	 * the parameter specify the update frequency in Hz. Keep it low, suggested: 5Hz, default: 1Hz
	 * {@code Test}
	 * @return void
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
	 * @return void
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
	 * @return void
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
	 * @return void
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
	 * @return void
	 */
	public void subscribeOrientation() {
		sendMessage("{\"m\":\"registerOrientation\"}");
	}
	public void releaseOrientation() {
		sendMessage("{\"m\":\"releaseOrientation\"}");
	}
	/**
	 * 
	 * @return void
	 */
	public void subscribeRxEmbedded() {
		sendMessage("{\"m\":\"srx\"}");
	}
	public void releaseRxEmbedded() {
		sendMessage("{\"m\":\"drx\"}");
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

