package vn.luis.goldsmine.menuhome;

import java.util.ArrayList;

import vn.luis.goldsmine.MainActivity;
import vn.luis.goldsmine.R;
import vn.luis.goldsmine.database.SQLite;
import vn.luis.goldsmine.object.ItemMenu;
import vn.luis.goldsmine.object.ItemSession;
import vn.luis.goldsmine.synchronize.Synchronize_Currency;
import vn.luis.goldsmine.synchronize.Synchronize_Gold;
import vn.luis.goldsmine.util.DialogUtil;
import vn.luis.goldsmine.util.NetworkUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
		String[] name_menu = getResources().getStringArray(R.array.menu_home_text);
		Drawable[] icon_menu = {
			resources.getDrawable(R.drawable.add_gold),
			resources.getDrawable(R.drawable.gold),
			resources.getDrawable(R.drawable.statistics_gold),
			resources.getDrawable(R.drawable.chart_gold),
			resources.getDrawable(R.drawable.information),
			resources.getDrawable(R.drawable.setting),
			resources.getDrawable(R.drawable.news),
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
					intent.putExtra("position", position);
	                startActivity(intent);
				}else{
					DialogUtil.confirmationAlert(MenuActivity.this, getResources().getString(R.string.exit), getResources().getString(R.string.message_exit), new DialogInterface.OnClickListener() {
		                @Override
		                public void onClick(DialogInterface dialogInterface, int i) {
		                    // do something important, user confirmed the alert
		                	MenuActivity.this.finish();
		                }
		            }, R.drawable.log_out);
				}
			}
		});
		
		db = new SQLite(this);
		int total_row_gold_system = db.count_gold_system();
		int total_row_currency_system = db.count_currency_system();
		
		if(total_row_gold_system == 0 && total_row_currency_system == 0){
			int check_internet = NetworkUtil.getConnectivityStatus(getApplicationContext());
			if(check_internet != 0){
				if(check_internet == 2){
					DialogUtil.confirmationAlert(this, "Mobile Network", "AAA", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							controller_download();
						}
						
					}, 0);
				}else{
					controller_download();
				}
			}else{
				DialogUtil.messageAlert(this, "Not Connected Internet", "Connected Internet Please", 0);
			}
		}
		
	}
	
	public void controller_download(){
		Synchronize_Gold synchronize_Gold = new Synchronize_Gold(this);
		synchronize_Gold.synchronize();
		Synchronize_Currency synchronize_Currency = new Synchronize_Currency(this);
		synchronize_Currency.synchronize();
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
	
}
