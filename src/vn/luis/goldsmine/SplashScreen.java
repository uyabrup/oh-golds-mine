package vn.luis.goldsmine;

import java.util.List;
import java.util.Locale;

import vn.luis.goldsmine.database.SQLite;
import vn.luis.goldsmine.menuhome.MenuActivity;
import vn.luis.goldsmine.object.ItemSession;
import vn.luis.goldsmine.object.ItemSetting;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
	 
    private static int SPLASH_TIME_OUT = 3000;
    ItemSession itemSession;
    SQLite db;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
 
        new Handler().postDelayed(new Runnable() {
 
            @Override
            public void run() {
            	saveSession();
                Intent i = new Intent(SplashScreen.this, MenuActivity.class);
                startActivity(i);
                finish();
                
            }
        }, SPLASH_TIME_OUT);
    }
    
    public void saveSession(){
		itemSession = new ItemSession(getApplicationContext());
		itemSession.clearSession();
		
		/* Default */
		String[] name_default = {
				"Default Currency","Default Code","Default Languague"
		};
		
		String[] value_default = {
				"Việt Nam","VND","Vietnamese"
		};
		
		for (int i = 0; i < name_default.length; i++) {
			itemSession.createSessionSetting(name_default[i],value_default[i]);
		}
		
		/* Get Database */
		db = new SQLite(getApplicationContext());
		List<ItemSetting> list_setting = db.get_all_setting();
		
		if(list_setting.size() != 0){
			for (int i = 0; i < list_setting.size(); i++) {
				itemSession.createSessionSetting(list_setting.get(i).getName(),list_setting.get(i).getValue());
			}
			
			String code_lang;
			if(itemSession.getLanguague() != null){
				if(itemSession.getLanguague().matches("Vietnamese")){
		    		code_lang = "vi_VN";
		    	}else{
		    		code_lang = "en_US";
		    	}
		    	Locale locale = new Locale(code_lang);
		    	Locale.setDefault(locale);
		    	Configuration config = new Configuration();
		    	config.locale = locale;
		    	Context context = getApplicationContext();
		    	context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
			}
		}
		
	}
 
}