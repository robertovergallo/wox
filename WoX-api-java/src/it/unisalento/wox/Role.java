package it.unisalento.wox;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Role extends TimerTask{

	public final static int SENSOR_CAPABILITY 	= 0;
	public final static int SENSOR_NEED 		= 1;
	public final static int ACTUATOR_CAPABILITY = 2;
	public final static int ACTUATOR_NEED 		= 3;
	
	private int roleType;
	private boolean hasStarted = false;
	private Topic t;
	private Timer timer;
	
	public Role(int roleType) {
		this.roleType = roleType;
	}
	
	protected void start(Topic t) {
		if(hasStarted) return;
		this.t=t;
		switch(roleType) {
			case SENSOR_CAPABILITY: break;
			case SENSOR_NEED: startPollingSensorNeed(); break;
			case ACTUATOR_CAPABILITY: break;
			case ACTUATOR_NEED: break;
			default: System.out.println("Nothing to start");
		}
		hasStarted=true;
	}
	
	protected void stop() {
		if(!hasStarted) return;
		switch(roleType) {
			case SENSOR_CAPABILITY: break;
			case SENSOR_NEED: stopPollingSensorNeed(); break;
			case ACTUATOR_CAPABILITY: break;
			case ACTUATOR_NEED: break;
			default: System.out.println("Nothing to stop");
		}
		t=null;
		hasStarted=false;
	}
	
	protected void startPollingSensorNeed() {
		if(WoXmanager.getInstance().getAppId() == null) {
			System.out.println("ERROR: please set your app id! This way: WoTmanager.getInstance().setAppId('you_app_id');");
			return;
		}
		String s = sendPost("subscribe/"+t.getFeature_id()+"/"+t.getLocation_MD5()+"/?app_id="+WoXmanager.getInstance().getAppId(), "<appId/>");
		timer = new Timer(true);
		timer.scheduleAtFixedRate(this, 0, 5*1000);
	}
	
	protected void stopPollingSensorNeed() {
		if(WoXmanager.getInstance().getAppId() == null) {
			System.out.println("ERROR: please set your app id! This way: WoTmanager.getInstance().setAppId('you_app_id');");
			return;
		}
		timer.cancel();
		String s = sendPost("unsubscribe/"+t.getFeature_id()+"/"+t.getLocation_MD5()+"/?app_id="+WoXmanager.getInstance().getAppId(), null);
	}

	@Override
	public void run() {
		String s = sendGet(t.getFeature_id()+"/"+t.getLocation_MD5()+"/?app_id="+WoXmanager.getInstance().getAppId());
		if(t!=null) t.setValue(s);
		//System.out.println("Valore del topic ("+t.getFeature_id()+","+t.getLocation_MD5()+") e': "+s); 
	}

	protected void sendPreferredValue(Topic t, String value) {
		this.t = t;
		if(WoXmanager.getInstance().getAppId() == null) {
			System.out.println("ERROR: please set you app id! This way: WoTmanager.getInstance().setAppId('you_app_id');");
			return;
		}
		String s = sendPost(t.getFeature_id()+"/"+t.getLocation_MD5()+"/?app_id="+WoXmanager.getInstance().getAppId(), "<TopicValue><value>"+value+"</value><emulatePursuit>false</emulatePursuit></TopicValue>");
	}
	
	protected void sendActualValue(Topic t, String value) {
		this.t = t;
		if(WoXmanager.getInstance().getAppId() == null) {
			System.out.println("ERROR: please set you app id! This way: WoTmanager.getInstance().setAppId('you_app_id');");
			return;
		}
		String s = sendPut(t.getFeature_id()+"/"+t.getLocation_MD5()+"/?app_id="+WoXmanager.getInstance().getAppId(), "<TopicValue><value>"+value+"</value><emulatePursuit>false</emulatePursuit></TopicValue>");
	}
	
	// HTTP POST request
	private String sendPost(String rel, String xmlPayload) {
		 try{
				String url = WoXmanager.getInstance().getWoXbaseURL()+rel;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 
				//add reuqest header
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", "app");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				if(xmlPayload!=null) con.setRequestProperty("Content-Type","application/xml; charset=utf-8");
		 
				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				if(xmlPayload!=null) wr.writeBytes(xmlPayload);
				wr.flush();
				wr.close();
		 
				int responseCode = con.getResponseCode();
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);
		 
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
		 
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
		 
				//print result
				return(response.toString());
			 }catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR: "+e.getMessage());
					return null;
			 }
	}
	
	// HTTP GET request
		private String sendGet(String rel){
	 
			try{
			String url = WoXmanager.getInstance().getWoXbaseURL()+rel;
	 
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
	 
			//add request header
			con.setRequestProperty("User-Agent", "app");
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			return(response.toString());
			}catch(Exception e) {
				System.out.println("ERROR: "+e.getMessage());
				return null;
			}
		}
		

		private String sendPut(String rel, String xmlPayload)
		{
			try
			{
				String url = WoXmanager.getInstance().getWoXbaseURL() + rel;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection)obj.openConnection();


				con.setRequestMethod("PUT");
				con.setRequestProperty("User-Agent", "app");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				if (xmlPayload != null) {
					con.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
				}
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				if (xmlPayload != null) {
					wr.writeBytes(xmlPayload);
				}
				wr.flush();
				wr.close();

				int responseCode = con.getResponseCode();
				System.out.println("\nSending 'PUT' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream()));

				StringBuffer response = new StringBuffer();
				String inputLine;
				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine);
				}
				in.close();


				return response.toString();
			}
			catch (Exception e)
			{
				System.out.println("ERROR: " + e.getMessage());
			}
			return null;
		}
}
