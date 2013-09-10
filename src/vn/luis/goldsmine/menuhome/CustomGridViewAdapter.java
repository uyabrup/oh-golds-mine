package vn.luis.goldsmine.menuhome;

import java.util.ArrayList;

import vn.luis.goldsmine.R;
import vn.luis.goldsmine.object.ItemMenu;
import vn.luis.goldsmine.util.Function;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridViewAdapter extends ArrayAdapter<ItemMenu> {
	Function function;
	Context context;
	int layoutResourceId;
	ArrayList<ItemMenu> data = new ArrayList<ItemMenu>();

	public CustomGridViewAdapter(Context context, int layoutResourceId,
			ArrayList<ItemMenu> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	static class RecordHolder {
		  TextView txtTitle;
		  ImageView imageItem;
	 }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
	
			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
			holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

	  ItemMenu item = data.get(position);
	  holder.txtTitle.setText(item.getTitle());
	  holder.imageItem.setImageDrawable(item.getImage());
	  return row;

	 }
	 
}