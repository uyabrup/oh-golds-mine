package vn.luis.goldsmine;

import vn.luis.goldsmine.configuration.InformationFragment;
import vn.luis.goldsmine.configuration.SettingFragment;
import vn.luis.goldsmine.drawer.CustomNavDrawer;
import vn.luis.goldsmine.drawer.AbstractNavDrawerActivity;
import vn.luis.goldsmine.drawer.NavDrawerActivityConfiguration;
import vn.luis.goldsmine.drawer.NavDrawerItem;
import vn.luis.goldsmine.drawer.NavMenuItem;
import vn.luis.goldsmine.drawer.NavMenuSection;
import vn.luis.goldsmine.golduser.ChartGoldUserFragment;
import vn.luis.goldsmine.golduser.ListGoldUserFragment;
import vn.luis.goldsmine.golduser.StatisticsGoldUserFragment;
import vn.luis.goldsmine.news.ListNewsFragment;
import vn.luis.goldsmine.util.DialogUtil;
import vn.luis.goldsmine.util.Function;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

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
        int position = intent.getIntExtra("position", -1);
        if(position >= 0){
	        FragmentTransaction fragmentsTransaction = getSupportFragmentManager().beginTransaction();
	        fragments = getResources().getStringArray(R.array.menu_fragment);
			fragmentsTransaction.replace(R.id.main,Fragment.instantiate(MainActivity.this, fragments[position]),fragments[position]);
			fragmentsTransaction.addToBackStack(null);
			fragmentsTransaction.commit();
        }
    }

	@Override
    protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
		
        NavDrawerItem[] menu = new NavDrawerItem[] {
        	/* Gold */
            NavMenuSection.create( 100, getResources().getString(R.string.section_gold)),
            NavMenuItem.create(101, getResources().getString(R.string.list_gold), "gold", false, this),
            NavMenuItem.create(102, getResources().getString(R.string.statistics_gold), "statistics_gold", false, this),
            NavMenuItem.create(103, getResources().getString(R.string.chart_gold), "chart_gold", false, this),
            
            /* Money */
            NavMenuSection.create(200, getResources().getString(R.string.section_dashboard)),
            NavMenuItem.create(201, getResources().getString(R.string.update_gold), "synchronize_gold", false, this),
            NavMenuItem.create(202, getResources().getString(R.string.update_currency), "synchronize_money", false, this),
            
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
		Fragment myFragment = null;
		String fragmentName = "";
        switch ((int)id) {
        case 101:
        	myFragment = new ListGoldUserFragment();
        	fragmentName = fragments[1];
            break;
        case 102:
        	myFragment = new StatisticsGoldUserFragment();
        	fragmentName = fragments[2];
        	break;
        case 103:
        	myFragment = new ChartGoldUserFragment();
        	fragmentName = fragments[3];
        	break;
        case 201:
            break;
        case 202:
            break;
        case 901:
        	myFragment = new SettingFragment();
        	fragmentName = fragments[4];
            break;
        case 902:
        	myFragment = new InformationFragment();
        	fragmentName = fragments[5];
        	break;
        case 903:
        	DialogUtil.confirmationAlert(this, getResources().getString(R.string.exit), getResources().getString(R.string.message_exit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // do something important, user confirmed the alert
                	finish();
                }
            }, R.drawable.log_out);
	    	break;
        }
        if(myFragment != null){
        	Fragment currentFragment =(Fragment)getSupportFragmentManager().findFragmentByTag(fragmentName);
        	if(currentFragment == null){
        		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        		fragmentTransaction.replace(R.id.main, myFragment, fragmentName);
        		fragmentTransaction.addToBackStack(null);
        		fragmentTransaction.commit();
        	}else{
	        	if(!currentFragment.isVisible()){
	        		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
	        		fragmentTransaction.replace(R.id.main, myFragment, fragmentName);
	        		fragmentTransaction.addToBackStack(null);
	        		fragmentTransaction.commit();
	        	}
        	}
        }
    }
}

