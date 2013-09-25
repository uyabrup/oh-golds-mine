package vn.luis.goldsmine.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class JSONfunctions {
	public static JSONObject getJSONfromURL(String url){
		InputStream inputStream = null;
		String result = "";
		JSONObject jsonObject = null;
		
		// Download json data from url
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("PhanMinh","Error in http connection "+e.toString());
		}
		
		// Convert respone to string
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"),8);
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while((line = bufferedReader.readLine()) != null){
				stringBuilder.append(line+"\n");
			}
			inputStream.close();
			result = stringBuilder.toString();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("PhanMinh","Error converting result "+e.toString());
		}
		
		// Parsing data
		try {
			jsonObject = new JSONObject(result);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("PhanMinh","Error parsing data "+e.toString());
		}
		return jsonObject;
	}
}
