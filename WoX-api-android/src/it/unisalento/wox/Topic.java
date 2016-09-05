package it.unisalento.wox;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;
import java.util.Observer;

public class Topic extends Observable{

	private int feature_id;
	private String location_MD5;
	private String location;
	private String value;
	private Role sn_role;
	private Role an_role;

	public final static int FEATURE_TEMPERATURE			= 0;
	public final static int FEATURE_PRESSURE 			= 1;
	public final static int FEATURE_LIGHTING 			= 2;
	public final static int FEATURE_ALARM 				= 3;
	public final static int FEATURE_SUM 				= 4;
	public final static int FEATURE_FALL				= 5;
	public final static int FEATURE_MISSED_DRUG			= 6;
	public final static int FEATURE_SHOCK				= 7;
	public final static int FEATURE_INDOOR_AREA_CHANGE	= 8;
	public final static int FEATURE_PROLONGED_STAY		= 9;
	public final static int FEATURE_ENTERTAINMENT		= 10;
	public final static int FEATURE_BARRIER				= 11;
	
	public Topic(int feature_id, String location) {
		super();
		this.feature_id = feature_id;
		this.location = location;
		this.location_MD5 = convertMD5(location);
	}
	
	public Topic() {}
	
	public int getFeature_id() {
		return feature_id;
	}
	public void setFeature_id(int feature_id) {
		this.feature_id = feature_id;
	}
	public String getLocation_MD5() {
		return location_MD5;
	}
	public void setLocation_MD5(String location_MD5) {
		this.location_MD5 = location_MD5;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		setChanged();
		notifyObservers(value);
	}

	public void subscribe(Observer o) {
		addObserver(o);
		subscribe();
	}

	public void subscribe() {
		sn_role=new Role(Role.SENSOR_NEED);
		sn_role.start(this);
	}

	public void unsubscribe() {
		if(sn_role!=null)
			sn_role.stop();
	}

	public void setPreferredValue(Object value) {
		an_role = new Role(Role.ACTUATOR_NEED);
		an_role.sendPreferredValue(this, value);
	}
	

	private static String convertMD5(String toConvert)
	{
		String generatedMD5 = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(toConvert.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedMD5 = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        System.out.println("************* MD5 for "+toConvert+" is "+ generatedMD5);
        return generatedMD5;
	}
}
