package vn.luis.goldsmine.golduser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.luis.goldsmine.R;
import vn.luis.goldsmine.database.SQLite;
import vn.luis.goldsmine.object.ItemGoldUser;
import vn.luis.goldsmine.util.DialogUtil;
import vn.luis.goldsmine.util.Function;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class AddGoldUserFragment extends Fragment {
	Function function;
	SQLite db;
	EditText edit_price, edit_date;
	Spinner select_type_gold, select_tael, select_small_tael;
	Button btt_calendar, btt_add, btt_cancel;
	private static String TITLE = null;
	private static String MESSAGE = null;
	
	public static Fragment newInstance(Context context) {
		AddGoldUserFragment f = new AddGoldUserFragment();
        return f;
    }
	
	public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    	@Override
    	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	final Calendar calendar = Calendar.getInstance();
	    	int yy = calendar.get(Calendar.YEAR);
	    	int mm = calendar.get(Calendar.MONTH);
	    	int dd = calendar.get(Calendar.DAY_OF_MONTH);
	    	return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    	}
    	 
    	public void onDateSet(DatePicker view, int yy, int mm, int dd) {
    		populateSetDate(yy, mm+1, dd);
    	}
    }
	
	public void populateSetDate(int year, int month, int day) {
		edit_date.setText(year+"/"+function.checkDigit(month)+"/"+function.checkDigit(day));
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_add_gold, null);
        function = new Function();
        TITLE = getResources().getString(R.string.title_error);
		edit_price = (EditText)root.findViewById(R.id.edit_price);
		edit_date = (EditText)root.findViewById(R.id.edit_date);
		btt_calendar = (Button)root.findViewById(R.id.btt_calendar);
		btt_add = (Button)root.findViewById(R.id.btt_add);
		btt_cancel = (Button)root.findViewById(R.id.btt_cancel);
		btt_calendar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new SelectDateFragment();
				newFragment.show(getFragmentManager(), "DatePicker");
			}
			
		});
		btt_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db = new SQLite(getActivity());
				String type = select_type_gold.getSelectedItem().toString();
	        	int tael = Integer.parseInt(select_tael.getSelectedItem().toString()) * 10;
	        	int small_tael = Integer.parseInt(select_small_tael.getSelectedItem().toString());
	        	int total_tael = tael + small_tael;
	        	if((tael + small_tael) == 0){	        		
	        		MESSAGE = getResources().getString(R.string.message_error_empty_tael); 
	        		DialogUtil.messageAlert(getActivity(), TITLE, MESSAGE, 0);
	        	}else{
	        		String price_string = edit_price.getText().toString();
	        		String date = edit_date.getText().toString();
	        		if(price_string.matches("")){
		        		MESSAGE = getResources().getString(R.string.message_error_empty_price);
		        		DialogUtil.messageAlert(getActivity(), TITLE, MESSAGE, 0);
	        		}else{
	        			if(date.matches("")){
	        				MESSAGE = getResources().getString(R.string.message_error_empty_date);
	        				DialogUtil.messageAlert(getActivity(), TITLE, MESSAGE, 0);
	        			}else{
	        				Double price = Double.parseDouble(price_string);
	        				db.insert_list(new ItemGoldUser(type, total_tael, price, date));
	        				getFragmentManager().popBackStack();
	        				Toast.makeText(getActivity(), getResources().getString(R.string.message_insert_success), Toast.LENGTH_LONG).show();
	        			}
	        		}
	        	}
			}
		});
		btt_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager().popBackStack();
			}
		});
		setSelect(root);
        return root;
    }
    
    public void setSelect(ViewGroup root){
    	List<String> list = new ArrayList<String>();
    	String[] list_type = getResources().getStringArray(R.array.array_type_gold);
    	for (int j = 0; j < list_type.length; j++) {
    		list.add(list_type[j]);
		}
    	
    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		select_type_gold = (Spinner)root.findViewById(R.id.select_type_gold);
		select_type_gold.setAdapter(dataAdapter);
    	
		list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add(String.valueOf(i));
		}
		dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		select_tael = (Spinner)root.findViewById(R.id.select_tael);
		select_tael.setAdapter(dataAdapter);
		
		select_small_tael = (Spinner)root.findViewById(R.id.select_small_tael);
		select_small_tael.setAdapter(dataAdapter);
	}
    
}
