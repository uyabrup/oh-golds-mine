package vn.luis.goldsmine;

import vn.luis.goldsmine.configuration.InformationFragment;
import vn.luis.goldsmine.configuration.SettingFragment;
import vn.luis.goldsmine.drawer.CustomNavDrawer;
import vn.luis.goldsmine.drawer.AbstractNavDrawerActivity;
import vn.luis.goldsmine.drawer.NavDrawerActivityConfiguration;
import vn.luis.goldsmine.drawer.NavDrawerItem;
import vn.luis.goldsmine.drawer.NavMenuItem;
import vn.luis.goldsmine.drawer.NavMenuSection;
import vn.luis.goldsmine.function.Function;
import vn.luis.goldsmine.golduser.ChartGoldUserFragment;
import vn.luis.goldsmine.golduser.ListGoldUserFragment;
import vn.luis.goldsmine.golduser.StatisticsGoldUserFragment;
import vn.luis.goldsmine.money.ChartMoneyFragment;
import vn.luis.goldsmine.money.ListMoneyFragment;
import vn.luis.goldsmine.money.StatisticsMoneyFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface.OnClickListener;

@SuppressLint("Recycle")
public class MainActivity extends AbstractNavDrawerActivity {
	
	String[] fragments;
	AlertDialog.Builder alert;
	Function function;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alert = new AlertDialog.Builder(MainActivity.this);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        FragmentTransaction fragmentsTransaction = getSupportFragmentManager().beginTransaction();
        fragments = getResources().getStringArray(R.array.menu_fragment);
		fragmentsTransaction.replace(R.id.main,Fragment.instantiate(MainActivity.this, fragments[position]));
    }

	@Override
    protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
		
        NavDrawerItem[] menu = new NavDrawerItem[] {
        	/* Gold */
            NavMenuSection.create( 100, getResources().getString(R.string.section_gold)),
            NavMenuItem.create(101, getResources().getString(R.string.list_gold), "list_gold", false, this),
            NavMenuItem.create(102, getResources().getString(R.string.statistics_gold), "statistics_gold", false, this),
            NavMenuItem.create(103, getResources().getString(R.string.chart_gold), "chart_gold", false, this),
            
            /* Money */
            NavMenuSection.create(200, getResources().getString(R.string.section_money)),
            NavMenuItem.create(201, getResources().getString(R.string.list_money), "ic_launcher", false, this),
            NavMenuItem.create(202, getResources().getString(R.string.statistics_money), "statistics_money", false, this),
            NavMenuItem.create(203, getResources().getString(R.string.chart_money), "chart_money", false, this),
            
            /* Configuration */
            NavMenuSection.create(900, getResources().getString(R.string.section_configuration)),
            NavMenuItem.create(901, getResources().getString(R.string.setting), "setting", false, this),
            NavMenuItem.create(902, getResources().getString(R.string.information), "information", false, this),
            NavMenuItem.create(903, getResources().getString(R.string.exit), "log_out", false, this)
        };        
       
        NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
        navDrawerActivityConfiguration.setMainLayout(R.layout.activity_main);
        navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer_layout);
        navDrawerActivityConfiguration.setLeftDrawerId(R.id.drawer);
        navDrawerActivityConfiguration.setNavItems(menu);
        navDrawerActivityConfiguration.setDrawerShadow(R.drawable.drawer_shadow);      
        navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.open_nav);
        navDrawerActivityConfiguration.setDrawerCloseDesc(R.string.close_nav);
        navDrawerActivityConfiguration.setBaseAdapter(
            new CustomNavDrawer(this, R.layout.navdrawer_item, menu));
        return navDrawerActivityConfiguration;
    }

	@Override
    protected void onNavItemSelected(int id) {
        switch ((int)id) {
        case 101:
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new ListGoldUserFragment()).commit();
            break;
        case 102:
        	getSupportFragmentManager().beginTransaction().replace(R.id.main, new StatisticsGoldUserFragment()).commit();
        	break;
        case 103:
        	getSupportFragmentManager().beginTransaction().replace(R.id.main, new ChartGoldUserFragment()).commit();
        	break;
        case 201:
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new ListMoneyFragment()).commit();
            break;
        case 202:
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new StatisticsMoneyFragment()).commit();
            break;
        case 203:
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new ChartMoneyFragment()).commit();
            break;
        case 901:
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new SettingFragment()).commit();
            break;
        case 902:
        	getSupportFragmentManager().beginTransaction().replace(R.id.main, new InformationFragment()).commit();
        	break;
        case 903:
	    	alert.setTitle(getResources().getString(R.string.exit));
	    	alert.setIcon(getResources().getDrawable(R.drawable.log_out));
	    	alert.setMessage(getResources().getString(R.string.message_exit));
	    	alert.setCancelable(false);
	    	alert.setPositiveButton(getResources().getString(R.string.yes), new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				    Intent intent = new Intent();
				    setResult(Activity.RESULT_OK, intent);
				    finish();
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
	    	break;
        }
    }
}

