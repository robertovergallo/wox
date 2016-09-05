package it.vidyasoft.wox.fintech.kissfly;

import it.unisalento.wox.Topic;
import it.unisalento.wox.WoXmanager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private static Boolean preferredBarrierStatus = false;
	private static Topic t;
	private static Button b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b = (Button) findViewById(R.id.button1);
		
		//initialize WoX
		WoXmanager.getInstance().setAppId("kissFlyBDS");
		//topic instance
		t = new Topic(Topic.FEATURE_BARRIER, "it:brindisi:airport:parking:kissFly01");
	}
	
	public static void toggleKissFly(View v) {
		preferredBarrierStatus = !preferredBarrierStatus;
		b.setText(preferredBarrierStatus? R.string.btn_exit : R.string.btn_enter);
		//change topic preferred value
		t.setPreferredValue(preferredBarrierStatus);
	}

	public static synchronized void updateKissAndFlyStatus() {
		preferredBarrierStatus = !preferredBarrierStatus;
	}
	
	public static synchronized boolean getPreferredBarrierStatus() {
		return preferredBarrierStatus;
	}

	public static void updateButtonText() {
		b.setText(preferredBarrierStatus? R.string.btn_exit : R.string.btn_enter);
	}
}
