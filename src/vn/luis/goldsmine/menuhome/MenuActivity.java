package vn.luis.goldsmine.menuhome;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.luis.goldsmine.MainActivity;
import vn.luis.goldsmine.R;
import vn.luis.goldsmine.database.SQLite;
import vn.luis.goldsmine.object.ItemMenu;
import vn.luis.goldsmine.object.ItemSession;
import vn.luis.goldsmine.object.ItemSetting;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MenuActivity extends Activity {
	GridView gridView_menu;
	ArrayList<ItemMenu> gridArray = new ArrayList<ItemMenu>();
	CustomGridViewAdapter customGridAdapter;
	Resources resources;
	AlertDialog.Builder alert;
	ItemSession itemSession;
	SQLite db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		resources = getResources();
		alert = new AlertDialog.Builder(MenuActivity.this);
		saveSession();
		String[] name_menu = getResources().getStringArray(R.array.menu_home_text);
		Drawable[] icon_menu = {
			resources.getDrawable(R.drawable.add_gold),
			resources.getDrawable(R.drawable.add_money),
			
			resources.getDrawable(R.drawable.add_gold),
			resources.getDrawable(R.drawable.add_money),
			
			resources.getDrawable(R.drawable.statistics_gold),
			resources.getDrawable(R.drawable.statistics_money),
			
			resources.getDrawable(R.drawable.chart_gold),
			resources.getDrawable(R.drawable.chart_money),
			
			resources.getDrawable(R.drawable.setting),
			resources.getDrawable(R.drawable.log_out)
		};
		
		int count_menu = name_menu.length;
		final int last_menu = count_menu - 1; 
		
		for (int i = 0; i < count_menu; i++) {
			gridArray.add(new ItemMenu(icon_menu[i], name_menu[i]));
		}
		 
		gridView_menu = (GridView) findViewById(R.id.gridView_main);
		customGridAdapter = new CustomGridViewAdapter(this, R.layout.activity_menu_row, gridArray);
		gridView_menu.setAdapter(customGridAdapter);
		gridView_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if(position != last_menu){
					Intent intent = new Intent(MenuActivity.this, MainActivity.class);
					startActivityForResult(intent, 0);
					intent.putExtra("position", position);
	                startActivity(intent);
				}else{
					alert.setTitle(getResources().getString(R.string.exit));
			    	alert.setIcon(getResources().getDrawable(R.drawable.log_out));
			    	alert.setMessage(getResources().getString(R.string.message_exit));
			    	alert.setCancelable(false);
			    	alert.setPositiveButton(getResources().getString(R.string.yes), new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							MenuActivity.this.finish();
						}
					});
			    	alert.setNegativeButton(getResources().getString(R.string.no), new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
			    	alert.create().show();
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 0:
			if(resultCode == RESULT_OK){
				finish();
			}
			break;

		default:
			break;
		}
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
