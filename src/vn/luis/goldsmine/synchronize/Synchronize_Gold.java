package vn.luis.goldsmine.synchronize;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import vn.luis.goldsmine.database.SQLite;
import vn.luis.goldsmine.object.ItemGoldSystem;
import vn.luis.goldsmine.object.ItemSetting;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class Synchronize_Gold {
	
	Context context;
	private int mProgressStatus = 0;
    private ProgressDialog mProgressDialog ;
    private ProgressBarAsync mProgressbarAsync;
    private static final String URL = "http://www.beforever.info/api/get_data_gold";
	
	public Synchronize_Gold(Context _context){
		this.context = _context;
	}

	public void synchronize(){
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage("Work in Progress ...");
        mProgressDialog.show();
        
        mProgressbarAsync = new ProgressBarAsync();
        mProgressbarAsync.execute(URL);
	}
	
	private class ProgressBarAsync extends AsyncTask<String, Integer, Void>{
		
		private final HttpClient Client = new DefaultHttpClient();
    	private String Content;
    	private String Error = null;
    	String data = "";
    	
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
            mProgressStatus = 0;
            try{
                // Set Request parameter
                data +="&" + URLEncoder.encode("data", "UTF-8") + "=1";
                     
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
 
        @Override
        protected Void doInBackground(String...params) {
        	BufferedReader reader = null;
        	try {
				URL url = new URL(params[0]);
				
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                wr.write(data);
                wr.flush(); 
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;
				
				while((line = reader.readLine()) != null){
					sb.append(line + "");
				}
				while(mProgressStatus<100){
	                try{
	 
	                    mProgressStatus++;
	 
	                    publishProgress(mProgressStatus);
	 
	                    Thread.sleep(100);
	 
	                }catch(Exception e){
	                    Log.d("Exception", e.toString());
	                }
	            }
				Content = sb.toString();
			} catch (Exception e) {
				// TODO: handle exception
				Error = e.getMessage();
				Log.e("PhanMinh", Error);
			}
			return null;
        }
 
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setProgress(mProgressStatus);
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(Error != null){
				Log.e("PhanMinh", Error);
			}else{
				String OutPutData = "";
				JSONObject jsonResponse;
				try{
					jsonResponse = new JSONObject(Content);
					JSONArray jsonMainNodeGold = jsonResponse.optJSONArray("gold");
					int lengthJsonArrGold = jsonMainNodeGold.length();
					SQLite db = new SQLite(context);
					db.empty_gold();
					for (int i = 0; i < lengthJsonArrGold; i++) {
						JSONObject jsonChildNodeGold = jsonMainNodeGold.getJSONObject(i);
						String type = jsonChildNodeGold.optString("type").toString();
						Double buy = Double.parseDouble(jsonChildNodeGold.optString("buy").toString());
						Double sell = Double.parseDouble(jsonChildNodeGold.optString("sell").toString());
						Calendar c = Calendar.getInstance();
						SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
						String date = df.format(c.getTime());
						db.insert_gold(new ItemGoldSystem(type, buy, sell, date));
						int count_gold = db.count_setting("Gold");
						if(count_gold == 0){
							db.insert_setting(new ItemSetting("Gold", buy.toString()));
						}else{
							db.update_setting(new ItemSetting("Gold", buy.toString()));
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			Toast.makeText(context, "Update Newest Data Successful", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();
        }

    }
	
}
