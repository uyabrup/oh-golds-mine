package vn.luis.goldsmine.golduser;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import vn.luis.goldsmine.R;
import vn.luis.goldsmine.database.SQLite;
import vn.luis.goldsmine.object.ItemGoldUser;
import vn.luis.goldsmine.object.ItemSession;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListGoldUserUser extends ArrayAdapter<ItemGoldUser> implements OnLongClickListener {
	ItemSession itemSession; 
	List<ItemGoldUser> list_gold;
	Context context;
	int resource;
	
	static class GoldHolder{
		public TextView txt_no, txt_type, txt_anumb, txt_price, txt_date;
	}
	
	public CustomListGoldUserUser(Context context, int textViewResourceId,
			List<ItemGoldUser> list) {
		super(context, textViewResourceId, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resource = textViewResourceId;
		this.list_gold = list;
	}
	
	@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View row = convertView;
			GoldHolder holder = null;
			itemSession = new ItemSession(getContext());
			if(row == null){
				LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.fragment_list_gold_row, parent, false);
				holder = new GoldHolder();
				holder.txt_no = (TextView)row.findViewById(R.id.txt_no);
				holder.txt_type = (TextView)row.findViewById(R.id.txt_type);
				holder.txt_anumb = (TextView)row.findViewById(R.id.txt_anumb);
				holder.txt_date = (TextView)row.findViewById(R.id.txt_date);
				holder.txt_price = (TextView)row.findViewById(R.id.txt_price);
				row.setTag(holder);
			}else{
				holder = (GoldHolder)row.getTag();
			}
			ItemGoldUser itemGoldUser = list_gold.get(position);
			Locale locale = Locale.US;
			holder.txt_no.setText(String.valueOf(itemGoldUser.getId()));
			holder.txt_type.setText(itemGoldUser.getType());
			holder.txt_anumb.setText(String.valueOf(itemGoldUser.getAnumb()));
			holder.txt_date.setText(itemGoldUser.getDate());
			Double anumb = itemGoldUser.getPrice();
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
			holder.txt_price.setText(NumberFormat.getNumberInstance(locale).format(anumb)+" "+code);
	    	final TextView txt = new TextView(getContext());
	    	txt.setText(String.valueOf(itemGoldUser.getId()));
			row.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
					builder.setTitle(getContext().getResources().getString(R.string.sort));
			    	//CharSequence[] type = getContext().getResources().getStringArray(R.array.array_sort);
			    	CharSequence[] type = {"Edit","Delete"};
			    	builder.setItems(type, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							final String id = (String) txt.getText();
//							LinearLayout row = (LinearLayout)0.getConte
//							final String id = ((TextView)row.findViewById(R.id.txt_no)).getText().toString();
//							String anumb = ((TextView)row.findViewById(R.id.txt_anumb)).getText().toString();
//							String date = ((TextView)row.findViewById(R.id.txt_date)).getText().toString();
//							String price = ((TextView)row.findViewById(R.id.txt_price)).getText().toString();
							switch(which){
								case 0:
									Intent intent_edit = new Intent(getContext(),EditGoldUserFragment.class);
									intent_edit.putExtra("position", id);
						    		getContext().startActivity(intent_edit);
								break;
								case 1:
									AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
									alertdialog.setTitle(getContext().getResources().getString(R.string.title_delete));
									alertdialog.setMessage(getContext().getResources().getString(R.string.message_ask_delete));
							        alertdialog.setPositiveButton(getContext().getResources().getString(R.string.title_delete), new DialogInterface.OnClickListener() {
							            public void onClick(DialogInterface dialog,int which) {
							            	SQLite db = new SQLite(getContext());
							            	db.delete_list(Integer.parseInt(id));
							            	Toast.makeText(getContext(), getContext().getResources().getString(R.string.message_delete_success), Toast.LENGTH_LONG).show();
							            	
							            }
							        });
							        alertdialog.setNegativeButton(getContext().getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
							            public void onClick(DialogInterface dialog, int which) {
							            	dialog.cancel();
							            }
							        });
							        alertdialog.show();
								break;
							}
						}
						
					});
			    	builder.show();
					return true;
				}
			});
			return row;
		}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_gold.size();
	}

}
