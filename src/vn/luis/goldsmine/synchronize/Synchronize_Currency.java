package vn.luis.goldsmine.synchronize;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
		
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
            mProgressStatus = 0;
        }
 
        @Override
        protected Void doInBackground(String...params) {
            while(mProgressStatus<100){
                try{
 
                    mProgressStatus++;
 
                    publishProgress(mProgressStatus);
 
                    Thread.sleep(100);
 
                }catch(Exception e){
                    Log.d("Exception", e.toString());
                }
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
            mProgressDialog.dismiss();
        }

    }

}
