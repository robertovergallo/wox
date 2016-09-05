package it.vidyasoft.wox.fintech.kissfly.GUI;

import it.unisalento.wox.Feature;
import it.unisalento.wox.Topic;
import it.unisalento.wox.WoXmanager;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class MyFrame extends JFrame {

	private final JLabel lbl_setEmail, lbl_secs;
	private JButton btn_subscribe;
	
	private boolean subscribed = false;
	private int num_email = 0;
	
	private int sec_free=30;

	Topic t;
	
	public MyFrame() {
		super("WoX Kiss&Fly");
		
		WoXmanager.getInstance().setAppId("kissFlyBDS");
		t = new Topic(Feature.LIGHTING, "it:brindisi:airport:parking:kissFly01");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		//impostiamo il grid layout come layout manager
		c.setLayout(new BorderLayout());
		
		//inizializzazione componenti UI
		lbl_setEmail = new JLabel("In attesa...");
		lbl_secs = new JLabel(sec_free+"");
		btn_subscribe=new JButton("Connect WoX");
		btn_subscribe.addActionListener(new MyActionListener());
		
		c.add(btn_subscribe, BorderLayout.NORTH);
		c.add(lbl_setEmail, BorderLayout.CENTER);
		c.add(lbl_secs, BorderLayout.EAST);
		
		addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		        if(subscribed)
		        	t.unsubscribe();
		      }
		    });

		setSize(400, 400);
		setPreferredSize(new Dimension(500,200));
		setVisible(true);
	}
	
	
	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(subscribed) {
				t.unsubscribe();
				btn_subscribe.setText("Connect WoX");
			}
			else {
				t.subscribe(new MyObserver());
				btn_subscribe.setText("Disconnect WoX");
			}
			subscribed=!subscribed;
		}
		
	}
	
	public class MyObserver implements Observer {

		Date d1, d2;
		Timer timer;
		@Override
		public void update(Observable arg0, Object arg1) {
			Topic t=(Topic)arg0;
			
			switch(t.getFeature_id()) {
				case Feature.LIGHTING:
					Float f=Float.valueOf(arg1.toString());
					System.out.println("f="+f);
					if(f==1.0) {
						if(d1 == null) {
							d1=new Date();
							lbl_setEmail.setText("Servizio in corso");
							   TimerTask tasknew = new TimerScheduleFixedRateDelay();
							      
							   // scheduling the task at fixed rate delay
							   timer = new Timer();
							   timer.scheduleAtFixedRate(tasknew,0,1000);
						}
					}
					else {
						if(d1==null) return;
						timer.cancel();
						d2=new Date();
						num_email++;
						long seconds = (d2.getTime()-d1.getTime())/1000 - sec_free;
						if(seconds > 0) {
							lbl_setEmail.setText("Fattura num."+num_email+" generata per "+seconds+" secondi di servizio");
							lbl_secs.setText(sec_free+"");
							d1=null;
							d2=null;
						}
						else {
							lbl_setEmail.setText("Servizio reso gratuitamente, grazie!");
							lbl_secs.setText(sec_free+"");
							d1=null;
							d2=null;
						}
					}
					
					
					break;
				default: break;
			}
		}
		
	}
	
	class TimerScheduleFixedRateDelay extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int i = Integer.parseInt(lbl_secs.getText());
			if(i>0) i-=1;
			lbl_secs.setText(i+"");
		}
		
	}
	
}
