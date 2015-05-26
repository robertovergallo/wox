package it.unisalento.wox.test;

import it.unisalento.wox.Topic;

import java.util.Observable;
import java.util.Observer;

public class TopicObserver implements Observer {

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		Topic t=(Topic)arg0;
		System.out.println("Nuovo valore del topic "+t.getFeature_id()+": "+arg1);
		
	}

}
