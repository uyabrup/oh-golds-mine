package vn.luis.goldsmine.cache;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.util.Log;

public class MemoryCache {
	private static final String TAG = "MemoryCache";
	
	// Last argument true for LRU ordering
	private Map<String, Bitmap> cache = Collections.synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));
	
	//Current allocated size
	private long size = 0;
	
	// Max memory in bytes
	private long limit = 1000000;
	
	public MemoryCache(){
		// Use 25% of  available heap size
		setLimit(Runtime.getRuntime().maxMemory() / 4);
	}
	
	public void setLimit(long new_limit){
		limit = new_limit;
		Log.i(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");
	}
	
	public Bitmap get(String id){
		try {
			if(!cache.containsKey(id)){
				return null;
			}
			return cache.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public void put(String id,Bitmap bitmap){
		try {
			if(cache.containsKey(id)){
				size -= getSizeInBytes(bitmap);
			}
			cache.put(id, bitmap);
			size += getSizeInBytes(bitmap);
			checkSize();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void checkSize(){
		Log.i(TAG,"cache size=" + size + " lenght=" + cache.size());
		if(size > limit){
			// Least recently accessed item will be the first one iterated
			Iterator<Entry<String, Bitmap>> iterable = cache.entrySet().iterator();
			while(iterable.hasNext()){
				Entry<String, Bitmap> entry = iterable.next();
				size -= getSizeInBytes(entry.getValue());
				iterable.remove();
				if(size <= limit){
					break;
				}
			}
			Log.i(TAG, "Clean cache. New size " + cache.size());
		}
	}
	
	public void clear(){
		try {
			cache.clear();
			size = 0;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			// TODO: handle exception
		}
	}
	
	long getSizeInBytes(Bitmap bitmap){
		if(bitmap == null){
			return 0;
		}
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
	
}
