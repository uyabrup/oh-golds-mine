package vn.luis.goldsmine.object;

import java.text.NumberFormat;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class ItemSession {
	SharedPreferences sharedPreferences;
	Editor editor;
	Context _context;
	
	int PRIVATE_MODE = 0;
	private static final String SESSION_NAME = "SessionSetting";
	
	public static final String CURRENCY_NAME = "Currency";
	public static final String DEFAULT_CURRENCY_NAME = "Default Currency";
	
	public static final String CODE_NAME = "Code";
	public static final String DEFAULT_CODE_NAME = "Default Code";
	
	public static final String DATE_UPDATE_GOLD_SYSTEM = "Date Update Gold System";
	public static final String DATE_UPDATE_CURRENCY_SYSTEM = "Date Update Currency System";
	
	public static final String LANGUAGUE_NAME = "Languague";
	public static final String DEFAULT_LANGUAGUE_NAME = "Default Languague";
	
	public static final String TAEL_NAME = "Tael";
	public static final String GOLD_SYSTEM = "Gold System";
	public static final String PRICE_NAME = "Price";
	
	public ItemSession(Context context) {
		this._context = context;
		sharedPreferences = _context.getSharedPreferences(SESSION_NAME, PRIVATE_MODE);
		editor = sharedPreferences.edit();
	}
	
	public void createSessionSetting(String name, String value){
		if(name.matches(CURRENCY_NAME)){
			editor.putString(CURRENCY_NAME, value);
			
		}else if(name.matches(DEFAULT_CURRENCY_NAME)){
			editor.putString(DEFAULT_CURRENCY_NAME, value);
			
		}else if(name.matches(LANGUAGUE_NAME)){
			editor.putString(LANGUAGUE_NAME, value);
			
		}else if(name.matches(DEFAULT_LANGUAGUE_NAME)){
			editor.putString(DEFAULT_LANGUAGUE_NAME, value);
			
		}else if(name.matches(TAEL_NAME)){
			editor.putString(TAEL_NAME, value);
			
		}else if(name.matches(GOLD_SYSTEM)){
			Locale locale = Locale.US;
			editor.putString(GOLD_SYSTEM, NumberFormat.getNumberInstance(locale).format(Double.parseDouble(value)));
			
		}else if(name.matches(DATE_UPDATE_GOLD_SYSTEM)){
			editor.putString(DATE_UPDATE_GOLD_SYSTEM, value);
			
		}else if(name.matches(DATE_UPDATE_CURRENCY_SYSTEM)){
			editor.putString(DATE_UPDATE_CURRENCY_SYSTEM, value);
			
		}else if(name.matches(CODE_NAME)){
			editor.putString(CODE_NAME, value);
			
		}else if(name.matches(DEFAULT_CODE_NAME)){
			editor.putString(DEFAULT_CODE_NAME, value);
			
		}else if(name.matches(PRICE_NAME)){
			editor.putString(PRICE_NAME, value);
			
		}
		editor.commit();
	}
	
	/* Currency */
	public String getCurrency(){
		return sharedPreferences.getString(CURRENCY_NAME, null);	
	}
	
	public String getDefaultCurrency(){
		return sharedPreferences.getString(DEFAULT_CURRENCY_NAME, null);	
	}
	
	/* Code */
	public String getCode(){
		return sharedPreferences.getString(CODE_NAME, null);	
	}
	
	public String getDefaultCode(){
		return sharedPreferences.getString(DEFAULT_CODE_NAME, null);	
	}
	
	/* Price */	
	public String getPrice(){
		return sharedPreferences.getString(PRICE_NAME, null);	
	}
	
	/* Languague */
	public String getLanguague(){
		return sharedPreferences.getString(LANGUAGUE_NAME, null);	
	}
	
	public String getDefaultLanguague(){
		return sharedPreferences.getString(DEFAULT_LANGUAGUE_NAME, null);	
	}
	
	/* Tael */
	public String getTael(){
		return sharedPreferences.getString(TAEL_NAME, null);	
	}
	
	/* Gold System */
	public String getGoldSystem(){
		return sharedPreferences.getString(GOLD_SYSTEM, null);
	}
	
	/* Update */
	public String getDateUpdateGoldSystem(){
		return sharedPreferences.getString(DATE_UPDATE_GOLD_SYSTEM, null);
	}
	
	public String getDateUpdateCurrencySystem(){
		return sharedPreferences.getString(DATE_UPDATE_CURRENCY_SYSTEM, null);
	}
	
	public void clearSession(){
		editor.clear();
		editor.commit();
	}

}
