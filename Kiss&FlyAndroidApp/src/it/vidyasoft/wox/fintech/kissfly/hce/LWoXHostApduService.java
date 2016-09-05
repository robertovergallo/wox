package it.vidyasoft.wox.fintech.kissfly.hce;

import it.vidyasoft.wox.fintech.kissfly.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

public class LWoXHostApduService extends HostApduService {
	   
	@Override
	public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
		MainActivity.updateKissAndFlyStatus();
		MainActivity.updateButtonText();
		Boolean f = MainActivity.getPreferredBarrierStatus();
		String s = Integer.toHexString(Float.floatToIntBits(((float)(f?1:0))));
		if("0".equals(s)) s="00000000";
		else s = s.substring(0, 4)+s.substring(4, 8);
		s ="0000"+s;
        return s.getBytes();
        
	}

	@Override
	public void onDeactivated(int reason) {
	}

}
