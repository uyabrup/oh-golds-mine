package vn.luis.goldsmine.news;

import java.util.ArrayList;
import java.util.HashMap;

import vn.luis.goldsmine.R;
import vn.luis.goldsmine.helper.ImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	Context context;
	ArrayList<HashMap<String, String>> arrayList;
	ImageLoader imageLoader;
	
	public NewsAdapter(Context context, ArrayList<HashMap<String, String>> arrayList){
		this.context = context;
		this.arrayList = arrayList;
		imageLoader = new ImageLoader(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View itemView;
		itemView = convertView;
		if(itemView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.news_row, parent, false);
		}
		HashMap<String, String> result = arrayList.get(position);
		TextView titleNews = (TextView) itemView.findViewById(R.id.titleNews);
		titleNews.setText(result.get("title"));
		ImageView imageNews = (ImageView) itemView.findViewById(R.id.imageNews);
		String img = result.get("images");
		if(img != "noimg"){
			imageLoader.DisplayImage(img, imageNews);
		}
		return itemView;
	}
	
}
