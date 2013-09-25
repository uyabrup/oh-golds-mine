package vn.luis.goldsmine.news;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.LoadMoreListView.OnLoadMoreListener;

import vn.luis.goldsmine.R;
import vn.luis.goldsmine.helper.ImageLoader;
import vn.luis.goldsmine.helper.JSONfunctions;
import vn.luis.goldsmine.object.ItemSession;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListNewsFragment extends ListFragment {
	
	ProgressDialog progressDialog;
	JSONObject jsonObject;
	JSONArray jsonArray;
	ArrayList<HashMap<String, String>> arrayList;
	ItemSession itemSession;
	static String URL = "http://www.beforever.info/api/get_data_news?section=all&start=0&offset=10";
	
	public static Fragment newsInstance(Context context){
		return new ListFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		itemSession = new ItemSession(getActivity());
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
//		listView = (LoadMoreListView) view.findViewById(R.id.listViewHaivl);
		arrayList = new ArrayList<HashMap<String,String>>();
        new DownloadNews().execute();
//        ((LoadMoreListView)getListView()).setOnLoadMoreListener(new OnLoadMoreListener() {
//						
//			@Override
//			public void onLoadMore() {
//				// TODO Auto-generated method stub
//				new DownloadNews().execute();
//				Log.e("mylog", "pulled");
//			}
//		});
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_list_news, null);
		return root;
	}
	
	private class DownloadNews extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
    		progressDialog.setTitle("Update News");
    		progressDialog.setMessage("Updating ...");
    		progressDialog.setIndeterminate(false);
    		progressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			jsonObject = JSONfunctions.getJSONfromURL(URL);
			try {
				jsonArray = jsonObject.getJSONArray("news");
				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					jsonObject = jsonArray.getJSONObject(i);
					// Retrive JSON Objects
					map.put("id",jsonObject.getString("id"));
					map.put("title", jsonObject.getString("title"));
					map.put("images", jsonObject.getString("img_thumb"));
					map.put("info", jsonObject.getString("info"));
					// Set JSON Objects into the array
					arrayList.add(map);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			NewsAdapter newsAdapter = new NewsAdapter(getActivity(), arrayList);
			setListAdapter(newsAdapter);
			progressDialog.dismiss();
		}
		
	}

}
