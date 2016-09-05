package it.unisalento.wox;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class CallWoXserverAsyncTask extends AsyncTask<AsyncTaskParams, Void, String> {


	
	@Override
	protected String doInBackground(AsyncTaskParams... params) {
		// TODO Auto-generated method stub
		String url=params[0].getUrl();
		String method=params[0].getMethod();
		if(method.equals(AsyncTaskParams.GET))
			return sendGet(url);
		else
			return sendPost(url, params[0].getXmlPayload());
	}

	// HTTP POST request
	private String sendPost(String rel, String xmlPayload) {
		 try{
				String url = WoXmanager.getInstance().getWoXbaseURL()+rel;
				System.out.println(url);
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
					e.printStackTrace();
					return null;
			 }
	}
	// HTTP POST request
	private String sendPut(String rel, String xmlPayload) {
		 try{
				String url = WoXmanager.getInstance().getWoXbaseURL()+rel;
				System.out.println(url);
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 
				//add reuqest header
				con.setRequestMethod("PUT");
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
					e.printStackTrace();
					return null;
			 }
	}
	
	// HTTP GET request
		private String sendGet(String rel){
	 
			try{
			String url = WoXmanager.getInstance().getWoXbaseURL()+rel;
			System.out.println(url);
	 
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
				e.printStackTrace();
				return null;
			}
		}

}
