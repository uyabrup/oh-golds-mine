package vn.luis.goldsmine.database;

import java.util.ArrayList;
import java.util.List;

import vn.luis.goldsmine.object.ItemCurrencySystem;
import vn.luis.goldsmine.object.ItemGoldSystem;
import vn.luis.goldsmine.object.ItemGoldUser;
import vn.luis.goldsmine.object.ItemSetting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "manager_goldsmine";
	/* Table List Gold User */
	private static final String TABLE_GOLD_USER = "gold_user";
	private static final String COLUMN_GOLD_USER_ID = "id";
	private static final String COLUMN_GOLD_USER_TYPE = "type";
	private static final String COLUMN_GOLD_USER_QUANTUM = "quantum";
	private static final String COLUMN_GOLD_USER_PRICE = "price";
	private static final String COLUMN_GOLD_USER_DATE = "date_buy";
	private static final String CREATE_TABLE_GOLD_USER = "CREATE TABLE " + TABLE_GOLD_USER + " (" +
			COLUMN_GOLD_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			COLUMN_GOLD_USER_TYPE + " TEXT NOT NULL," +
			COLUMN_GOLD_USER_QUANTUM + " INTEGER NOT NULL," +
			COLUMN_GOLD_USER_PRICE + " NUMERIC NOT NULL," +
			COLUMN_GOLD_USER_DATE + " DATE)";
	
	/* Table Currency System */
	private static final String TABLE_CURRENCY_SYSTEM = "currency_system";
	private static final String COLUMN_CURRENCY_SYSTEM_ID = "id";
	private static final String COLUMN_CURRENCY_SYSTEM_NAME = "name";
	private static final String COLUMN_CURRENCY_SYSTEM_CODE = "code";
	private static final String COLUMN_CURRENCY_SYSTEM_PRICE = "price";
	private static final String CREATE_TABLE_CURRENCY_SYSTEM = "CREATE TABLE " + TABLE_CURRENCY_SYSTEM + " (" +
			COLUMN_CURRENCY_SYSTEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			COLUMN_CURRENCY_SYSTEM_NAME + " TEXT NOT NULL," +
			COLUMN_CURRENCY_SYSTEM_CODE + " TEXT NOT NULL," +
			COLUMN_CURRENCY_SYSTEM_PRICE + " NUMERIC NOT NULL)";
	
	/* Table Gold System*/
	private static final String TABLE_GOLD_SYSTEM = "gold_system";
	private static final String COLUMN_GOLD_SYSTEM_TYPE = "type";
	private static final String COLUMN_GOLD_SYSTEM_BUY = "buy";
	private static final String COLUMN_GOLD_SYSTEM_SELL = "sell";
	private static final String COLUMN_GOLD_SYSTEM_DATE = "date";
	private static final String CREATE_TABLE_GOLD_SYSTEM = "CREATE TABLE " + TABLE_GOLD_SYSTEM + " (" +
			COLUMN_GOLD_SYSTEM_TYPE + " TEXT NOT NULL," +
			COLUMN_GOLD_SYSTEM_BUY + " NUMERIC NOT NULL," +
			COLUMN_GOLD_SYSTEM_SELL + " NUMERIC NOT NULL," +
			COLUMN_GOLD_SYSTEM_DATE + " TEXT NOT NULL)";
	
	/* Table Setting */
	private static final String TABLE_SETTING = "setting";
	private static final String COLUMN_SETTING_ID = "id";
	private static final String COLUMN_SETTING_NAME = "name";
	private static final String COLUMN_SETTING_VALUE = "value";
	private static final String CREATE_TABLE_SETTING = "CREATE TABLE " + TABLE_SETTING + " (" +
			COLUMN_SETTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			COLUMN_SETTING_NAME + " TEXT NOT NULL," +
			COLUMN_SETTING_VALUE + " TEXT NOT NULL)";
	
	/* Table History Gold*/
	private static final String TABLE_HISTORY = "history";
	private static final String COLUMN_HISTORY_ID = "id";
	private static final String COLUMN_HISTORY_ANUMB = "anumb";
	private static final String COLUMN_HISTORY_PRICE = "price";
	private static final String COLUMN_HISTORY_DATE = "date";
	private static final String CREATE_TABLE_HISTORY = "CREATE TABLE " + TABLE_HISTORY + " (" +
			COLUMN_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			COLUMN_HISTORY_ANUMB + " NUMERIC NOT NULL," +
			COLUMN_HISTORY_PRICE + " NUMERIC NOT NULL," +
			COLUMN_HISTORY_DATE + " TEXT NOT NULL)";
	
	static SQLiteDatabase db;
	
	public SQLite(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_GOLD_USER);
		db.execSQL(CREATE_TABLE_CURRENCY_SYSTEM);
		db.execSQL(CREATE_TABLE_GOLD_SYSTEM);
		db.execSQL(CREATE_TABLE_SETTING);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // Khi update lên phiên bản mới thì drop table và tạo lại
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOLD_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY_SYSTEM);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOLD_SYSTEM);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
		onCreate(db);
	}
	
	/* TABLE GOLD USER */
	
	public void insert_list(ItemGoldUser gold){ // Insert data vào database
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(COLUMN_GOLD_USER_TYPE, gold.getType());
		values.put(COLUMN_GOLD_USER_QUANTUM, gold.getAnumb());
		values.put(COLUMN_GOLD_USER_PRICE, gold.getPrice());
		values.put(COLUMN_GOLD_USER_DATE, gold.getDate());
		
		db.insert(TABLE_GOLD_USER, null, values);
		db.close();
	}
	
	public ItemGoldUser get_detail_list(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GOLD_USER + " WHERE " + COLUMN_GOLD_USER_ID +" = " + id,null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		ItemGoldUser gold = new ItemGoldUser(Integer.parseInt(cursor.getString(0)), cursor.getString(1) ,Integer.parseInt(cursor.getString(2)), Double.parseDouble(cursor.getString(3)), cursor.getString(4));
		return gold;
	}
	
	public List<ItemGoldUser> get_all_list(int id){
		List<ItemGoldUser> listgold = new ArrayList<ItemGoldUser>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sort = null;
		switch(id){
			case 0:
				sort = " ORDER BY " + COLUMN_GOLD_USER_ID + " ASC";
			break;
			case 1:
				sort = " ORDER BY " + COLUMN_GOLD_USER_QUANTUM + " ASC";
			break;
			case 2:
				sort = " ORDER BY " + COLUMN_GOLD_USER_QUANTUM + " DESC";
			break;
			case 3:
				sort = " ORDER BY " + COLUMN_GOLD_USER_PRICE + " ASC";
			break;
			case 4:
				sort = " ORDER BY " + COLUMN_GOLD_USER_PRICE + " DESC";
			break;
		}
		String sql = "SELECT * FROM " + TABLE_GOLD_USER + sort;
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				do{
					ItemGoldUser gold = new ItemGoldUser();
					gold.setId(Integer.parseInt(cursor.getString(0)));
					gold.setType(cursor.getString(1));
					gold.setAnumb(Integer.parseInt(cursor.getString(2)));
					gold.setPrice(Double.parseDouble(cursor.getString(3)));
					gold.setDate(cursor.getString(4));
					listgold.add(gold);
				}while( cursor.moveToNext() );
			}
		}
		return listgold;
	}
	
	public int count_row_list(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GOLD_USER, null);
		cursor.close();
		return cursor.getCount();
	}
	
	public String[] get_sum_list(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT SUM("+ COLUMN_GOLD_USER_QUANTUM +"), SUM("+ COLUMN_GOLD_USER_PRICE +") FROM " + TABLE_GOLD_USER, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		String[] data = {cursor.getString(0), cursor.getString(1)};
		return data;
	}
	
	public int update_list(ItemGoldUser gold){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_GOLD_USER_TYPE, gold.getType());
		values.put(COLUMN_GOLD_USER_QUANTUM, gold.getAnumb());
		values.put(COLUMN_GOLD_USER_PRICE, gold.getPrice());
		values.put(COLUMN_GOLD_USER_DATE, gold.getDate());
		return db.update(TABLE_GOLD_USER, values, COLUMN_GOLD_USER_ID + "=?", new String[] {String.valueOf(gold.getId())});
	}
	
	public void delete_list(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM " + TABLE_GOLD_USER + " WHERE " + COLUMN_GOLD_USER_ID + " = " + String.valueOf(id));
		db.close();
	}
	
	/* COLUMN CURRENCY SYSTEM */
	
	public int count_currency_system(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CURRENCY_SYSTEM,null);
		return cursor.getCount();
	}
	
	public void empty_currency(){ // Insert data vào database
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM " + TABLE_CURRENCY_SYSTEM);
		db.close();
	}
	
	public void insert_currency(ItemCurrencySystem currency){ // Insert data vào database
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_CURRENCY_SYSTEM_NAME, currency.getName());
		values.put(COLUMN_CURRENCY_SYSTEM_CODE, currency.getCode());
		values.put(COLUMN_CURRENCY_SYSTEM_PRICE, currency.getPrice());		
		db.insert(TABLE_CURRENCY_SYSTEM, null, values);
		db.close();
	}
	
	public List<ItemCurrencySystem> get_all_currency(){
		List<ItemCurrencySystem> listcurrency = new ArrayList<ItemCurrencySystem>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CURRENCY_SYSTEM, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				do{
					ItemCurrencySystem currency = new ItemCurrencySystem();
					currency.setId(Integer.parseInt(cursor.getString(0)));
					currency.setName(cursor.getString(1));
					currency.setCode(cursor.getString(2));
					currency.setPrice(Double.parseDouble(cursor.getString(3)));
					listcurrency.add(currency);
				}while( cursor.moveToNext() );
			}
		}
		return listcurrency;
	}
	
	public List<String> get_all_name_currency(){
		List<String> listcurrency = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + COLUMN_CURRENCY_SYSTEM_NAME + " FROM " + TABLE_CURRENCY_SYSTEM, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				do{
					ItemCurrencySystem currency = new ItemCurrencySystem();
					currency.setName(cursor.getString(0));
					listcurrency.add(currency.getName());
				}while( cursor.moveToNext() );
			}
		}
		return listcurrency;
	}
	
	public String getCodeByName(String name){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + COLUMN_CURRENCY_SYSTEM_CODE + " FROM " + TABLE_CURRENCY_SYSTEM + " WHERE " + COLUMN_CURRENCY_SYSTEM_NAME +" = '" + name + "'",null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor.getString(0);
	}
	
	public String getPriceByName(String name){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + COLUMN_CURRENCY_SYSTEM_PRICE + " FROM " + TABLE_CURRENCY_SYSTEM + " WHERE " + COLUMN_CURRENCY_SYSTEM_NAME +" = '" + name  + "'",null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor.getString(0);
	}
	
	/* COLUMN GOLD SYSTEM */
	
	public int count_gold_system(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GOLD_SYSTEM,null);
		return cursor.getCount();
	}
	
	public void empty_gold(){ // Insert data vào database
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM " + TABLE_GOLD_SYSTEM);
		db.close();
	}
	
	public void insert_gold(ItemGoldSystem itemGoldSystem){ // Insert data vào database
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_GOLD_SYSTEM_TYPE, itemGoldSystem.getType());
		values.put(COLUMN_GOLD_SYSTEM_BUY, itemGoldSystem.getBuy());
		values.put(COLUMN_GOLD_SYSTEM_SELL, itemGoldSystem.getSell());
		values.put(COLUMN_GOLD_SYSTEM_DATE, itemGoldSystem.getDate());		
		db.insert(TABLE_GOLD_SYSTEM, null, values);
		db.close();
	}
	
	public ItemGoldSystem get_gold(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GOLD_SYSTEM,null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		ItemGoldSystem itemGoldSystem = new ItemGoldSystem(cursor.getString(0),Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2)), cursor.getString(3));
		return itemGoldSystem;
	}
	
	/* TABLE SETTING */
	
	public void insert_setting(ItemSetting setting){ // Insert data vào database
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_SETTING_NAME, setting.getName());
		values.put(COLUMN_SETTING_VALUE, setting.getValue());
		db.insert(TABLE_SETTING, null, values);
		db.close();
	}
	
	public ItemSetting get_detail_setting(String name){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SETTING + " WHERE " + COLUMN_SETTING_NAME + " ='" + name +"'",null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		ItemSetting setting = new ItemSetting(cursor.getString(1), cursor.getString(2));
		return setting;
	}
	
	public List<ItemSetting> get_all_setting(){
		List<ItemSetting> list_setting = new ArrayList<ItemSetting>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SETTING, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				do{
					ItemSetting setting = new ItemSetting();
					setting.setId(Integer.parseInt(cursor.getString(0)));
					setting.setName(cursor.getString(1));
					setting.setValue(cursor.getString(2));
					list_setting.add(setting);
				}while( cursor.moveToNext() );
			}
		}
		return list_setting;
	}
	
	public int count_setting(String name){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SETTING + " WHERE " + COLUMN_SETTING_NAME + " = '" + name +"'",null);
		return cursor.getCount();
	}
	
	public int update_setting(ItemSetting setting){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_SETTING_NAME, setting.getName());
		values.put(COLUMN_SETTING_VALUE, setting.getValue());
		return db.update(TABLE_SETTING, values, COLUMN_SETTING_NAME + "=?", new String[] {String.valueOf(setting.getName())});
	}
	
	/* TABLE HISTORY */
	
	public void insert_history(ItemGoldUser gold){ // Insert data vào database
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_HISTORY_ANUMB, gold.getAnumb());
		values.put(COLUMN_HISTORY_PRICE, gold.getPrice());
		values.put(COLUMN_HISTORY_DATE, gold.getDate());
		db.insert(TABLE_HISTORY, null, values);
		db.close();
	}
	
	public List<ItemGoldUser> get_all_history(){
		List<ItemGoldUser> list_gold = new ArrayList<ItemGoldUser>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HISTORY, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				do{
					ItemGoldUser gold = new ItemGoldUser();
					gold.setId(Integer.parseInt(cursor.getString(0)));
					gold.setAnumb(Integer.parseInt(cursor.getString(1)));
					gold.setPrice(Double.parseDouble(cursor.getString(2)));
					gold.setDate(cursor.getString(3));
					list_gold.add(gold);
				}while( cursor.moveToNext() );
			}
		}
		return list_gold;
	}
	
	public int count_date_history(String date){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HISTORY + " WHERE " + COLUMN_HISTORY_DATE + " = '" + date +"'",null);
		return cursor.getCount();
	}
	
	public int count_history(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HISTORY,null);
		return cursor.getCount();
	}

	public void empty_history(){ // Insert data vào database
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM " + TABLE_HISTORY);
		db.close();
	}
	
	public int update_history(ItemGoldUser gold){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_HISTORY_ANUMB, gold.getAnumb());
		values.put(COLUMN_HISTORY_PRICE, gold.getPrice());
		return db.update(TABLE_HISTORY, values, COLUMN_HISTORY_DATE+ "=?", new String[] {String.valueOf(gold.getDate())});
	}
}
