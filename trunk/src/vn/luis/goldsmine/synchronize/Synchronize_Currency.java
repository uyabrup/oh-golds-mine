package vn.luis.goldsmine.synchronize;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import vn.luis.goldsmine.database.SQLite;
import vn.luis.goldsmine.object.ItemCurrencySystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class Synchronize_Currency {
	
	Context context;
	private int mProgressStatus = 0;
    private ProgressDialog mProgressDialog ;
    private ProgressBarAsync mProgressbarAsync;
    private static final String URL = "http://www.beforever.info/api/get_data_currency";
	
	public Synchronize_Currency(Context _context){
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
				JSONObject jsonResponse = null;
				try{
					JSONArray jsonMainNodeCurrency = jsonResponse.optJSONArray("currency");
					int lengthJsonArrCurrency = jsonMainNodeCurrency.length();
					SQLite db = new SQLite(context);
					db.empty_currency();
					for (int i = 0; i < lengthJsonArrCurrency; i++) {
						JSONObject jsonChildNodeCurrency = jsonMainNodeCurrency.getJSONObject(i);
						String name = jsonChildNodeCurrency.optString("name").toString();
						String code = jsonChildNodeCurrency.optString("code").toString();
						String value = jsonChildNodeCurrency.optString("value").toString();
						db.insert_currency(new ItemCurrencySystem(name, code, Double.parseDouble(value)));
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
