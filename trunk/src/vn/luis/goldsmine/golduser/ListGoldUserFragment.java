package vn.luis.goldsmine.golduser;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import vn.luis.goldsmine.R;
import vn.luis.goldsmine.database.SQLite;
import vn.luis.goldsmine.object.ItemGoldUser;
import vn.luis.goldsmine.object.ItemSession;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class ListGoldUserFragment extends Fragment {
	SQLite db;
	ListView listViewGoldUser;
	List<ItemGoldUser> arrayGoldUser;
	CustomListGoldUserUser adapter;
	MenuItem menuItem;
	ItemSession itemSession;
	
	public static Fragment newInstance(Context context) {
		ListGoldUserFragment f = new ListGoldUserFragment();
        return f;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		itemSession = new ItemSession(getActivity());
		setHasOptionsMenu(true);
	}
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_list_gold, null);
        db = new SQLite(getActivity());
        listViewGoldUser = (ListView)root.findViewById(R.id.listViewGoldUser);
        arrayGoldUser = db.get_all_list(0);
        if(arrayGoldUser.size() == 0){
			View view = inflater.inflate(R.layout.fragment_list_gold_empty, container, false);
			return view;
		}else{
			final String[] data = db.get_sum_list();
			TextView txt_total_anumb = (TextView)root.findViewById(R.id.txt_total_anumb);
			TextView txt_total = (TextView)root.findViewById(R.id.txt_total);
			ImageView save_history = (ImageView)root.findViewById(R.id.save_history);
			txt_total_anumb.setText(data[0]);
			Locale locale = Locale.US;
			final Double anumb = Double.parseDouble(data[1]);
//			Double from = Double.parseDouble(session.getDefaultPrice());
//			Double to = Double.parseDouble(session.getPrice());
//			Double percent = to / from;
//			Double price = anumb * percent;
			String code = null;
			if(itemSession.getCurrency() == null){
				code = itemSession.getDefaultCode();
			}else{
				code = itemSession.getCode();
			}
			txt_total.setText(NumberFormat.getNumberInstance(locale).format(anumb)+" "+code);
			save_history.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SQLite db = new SQLite(getActivity());
					Calendar c = Calendar.getInstance();
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					String date = df.format(c.getTime());
					int count_date = db.count_date_history(date);
					if(count_date == 0){
						db.insert_history(new ItemGoldUser(Integer.parseInt(data[0]), anumb, date));
					}else{
						db.update_history(new ItemGoldUser(Integer.parseInt(data[0]), anumb, date));
					}
					
				}
			});
			adapter = new CustomListGoldUserUser(getActivity(), R.layout.fragment_list_gold_row, arrayGoldUser);
			listViewGoldUser.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
        return root;
    }
    
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menuItem = menu.add(Menu.NONE, 801, Menu.NONE, getResources().getString(R.string.add_gold));
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuItem.setIcon(getResources().getDrawable(R.drawable.add_gold));
		
		menuItem = menu.add(Menu.NONE, 802, Menu.NONE, getResources().getString(R.string.refesh));
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuItem.setIcon(getResources().getDrawable(R.drawable.synchronize_gold)); /* �?ể �?ỡ chưa có hình refesh */
		
		menuItem = menu.add(Menu.NONE, 803, Menu.NONE, getResources().getString(R.string.sort));
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		super.onCreateOptionsMenu(menu, inflater);
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case 801:
				Fragment fragment = new AddGoldUserFragment();

				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				transaction.replace(R.id.main, fragment);
				transaction.addToBackStack(null);
				transaction.commit();
			return true;
			case 802:
				SQLite db = new SQLite(getActivity());
				arrayGoldUser = db.get_all_list(0);
				adapter = new CustomListGoldUserUser(getActivity(), R.layout.fragment_list_gold_row, arrayGoldUser);
				listViewGoldUser.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			return true;
			case 803:
				AlertDialog.Builder alert_sort = new Builder(getActivity());
	        	alert_sort.setTitle(getResources().getString(R.string.sort));
	        	String[] type = getResources().getStringArray(R.array.array_sort);
	        	alert_sort.setItems(type, new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						SQLite db = new SQLite(getActivity());
						arrayGoldUser = db.get_all_list(which);
						adapter = new CustomListGoldUserUser(getActivity(), R.layout.fragment_list_gold_row, arrayGoldUser);
						listViewGoldUser.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
	        	});
	        	alert_sort.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
