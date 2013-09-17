package vn.luis.goldsmine.configuration;

import java.util.List;

import vn.luis.goldsmine.R;
import vn.luis.goldsmine.database.SQLite;
import vn.luis.goldsmine.object.ItemSession;
import vn.luis.goldsmine.object.ItemSetting;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class SettingFragment extends Fragment{
	Spinner select_currency;
	Button btt_save, btt_cancel;
	RadioGroup radio_languague;
	RadioButton radioLanguagueButton;
	ItemSession itemSession;
	
	public static Fragment newInstance(Context context) {
		SettingFragment f = new SettingFragment();
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_setting, null);
        itemSession = new ItemSession(root.getContext());
		radio_languague = (RadioGroup)root.findViewById(R.id.radioLanguague);
		String languague_session = itemSession.getLanguague();
		if(languague_session != null){
			if(languague_session.matches("Vietnamese")){
				radio_languague.check(R.id.radio_vi);
			}else{
				radio_languague.check(R.id.radio_en);
			}
		}else{
			radio_languague.check(R.id.radio_vi);
		}
		
		btt_save = (Button)root.findViewById(R.id.btt_save);
		btt_cancel = (Button)root.findViewById(R.id.btt_cancel);
		btt_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
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
		select_currency = (Spinner)root.findViewById(R.id.select_currency);
		SQLite db = new SQLite(root.getContext());
		List<String> list = db.get_all_name_currency();
		ItemSetting itemSetting = db.get_detail_setting("Currency");
		int position = list.indexOf(itemSetting.getValue());
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		select_currency.setAdapter(dataAdapter);
		select_currency.setSelection(position);
		select_currency.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
