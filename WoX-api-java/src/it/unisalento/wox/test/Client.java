package it.unisalento.wox.test;

import it.unisalento.wox.Topic;
import it.unisalento.wox.WoXmanager;

public class Client {

	public static void main(String args[]) {
		new Client();
	}
 
	public Client() {
		WoXmanager.getInstance().setAppId("asd");
		Topic t1=new Topic(Topic.FEATURE_TEMPERATURE, "casa");
		t1.subscribe(new TopicObserver());
		//t1.setPreferredValue("30.0");
		
		try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		t1.unsubscribe();
	}
	
}
