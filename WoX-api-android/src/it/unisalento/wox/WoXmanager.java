package it.unisalento.wox;

public class WoXmanager {

	private static WoXmanager instance;
	private String app_id;
	private String WoXserverIP = "193.204.76.235";
	private String WoXserverPort = "8080";
	//private String base_url="http://192.168.1.100:8080/capturingapp/rest/topicmanager/topics/"; //193.204.76.235
	private String base_url="http://193.204.76.186:8280/wox/topics/"; //193.204.76.235
	
	public void setWoXserverAddress(String ip, String port) {
		WoXserverIP=ip;
		WoXserverPort=port;
		base_url="http://"+WoXserverIP+":"+WoXserverPort+"/wox/topics/";
	}
	
	public String getWoXbaseURL() {
		return base_url;
	}
	
	public String getAppId() {
		return app_id;
	}

	public void setAppId(String app_id) {
		this.app_id = app_id;
	}

	public static WoXmanager getInstance() {
		if(instance == null)
			instance = new WoXmanager();
		return instance;
	}
}
