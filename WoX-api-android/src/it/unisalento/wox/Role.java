package it.unisalento.wox;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

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
			System.out.println("ERROR: please set you app id! This way: WoXmanager.getInstance().setAppId('your_app_id');");
			return;
		}
		new CallWoXserverAsyncTask().execute(new AsyncTaskParams("subscribe/"+t.getFeature_id()+"/"+t.getLocation_MD5()+"/?app_id="+WoXmanager.getInstance().getAppId(), AsyncTaskParams.POST, null)); 
				
		timer = new Timer(true);
		timer.scheduleAtFixedRate(this, 0, 5*1000);
	}
	
	protected void stopPollingSensorNeed() {
		if(WoXmanager.getInstance().getAppId() == null) {
			System.out.println("ERROR: please set you app id! This way: WoXmanager.getInstance().getAppId('you_app_id');");
			return;
		}
		timer.cancel();
		new CallWoXserverAsyncTask().execute(new AsyncTaskParams("unsubscribe/"+t.getFeature_id()+"/"+t.getLocation_MD5()+"/?app_id="+WoXmanager.getInstance().getAppId(), AsyncTaskParams.POST, null));
	}

	@Override
	public void run() {
		String s="";
		try {
			s = new CallWoXserverAsyncTask().execute(new AsyncTaskParams(t.getFeature_id()+"/"+t.getLocation_MD5()+"/?app_id="+WoXmanager.getInstance().getAppId(),AsyncTaskParams.GET, null)).get();
		} catch (InterruptedException e){}
		catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(t!=null) t.setValue(s);
		//System.out.println("Valore del topic ("+t.getFeature_id()+","+t.getLocation_MD5()+") e': "+s); 
	}

	protected void sendPreferredValue(Topic t, Object value) {
		this.t = t;
		if(WoXmanager.getInstance().getAppId() == null) {
			System.out.println("ERROR: please set you app id! This way: WoXmanager.getInstance().getAppId('you_app_id');");
			return;
		}
		new CallWoXserverAsyncTask().execute(new AsyncTaskParams(t.getFeature_id()+"/"+t.getLocation_MD5()+"/?app_id="+WoXmanager.getInstance().getAppId(), AsyncTaskParams.POST, "<TopicValue><value>"+value+"</value><emulatePursuit>false</emulatePursuit></TopicValue>"));
	}
	

}
