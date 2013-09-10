package vn.luis.goldsmine.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	private static final int TYPE_WIFI = 1;
	private static final int TYPE_MOBILE = 2;
	private static final int TYPE_NOT_CONNECTED = 0;
	
	public static int getConnectivityStatus(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
		if(activeNetwork != null){
			if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
				return TYPE_MOBILE;
			}
			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
				return TYPE_WIFI;
			}
		}
		return TYPE_NOT_CONNECTED;
	}
}
