package vn.luis.goldsmine.cache;

import java.io.File;

import android.content.Context;

public class FileCache {
	private File cacheDir;
	
	public FileCache(Context context){
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"RelaxCache");
		}else{
            cacheDir = context.getCacheDir();
		}
        if (!cacheDir.exists()){
            cacheDir.mkdirs();
        }
	}
	
	public File getFile(String url){
		String filename = String.valueOf(url.hashCode());
		// String filename = URLEncoder.encode(url);
		File file = new File(cacheDir, filename);
		return file;
	}
	
	public void clear(){
		File[] files = cacheDir.listFiles();
		if(files == null){
			return;
		}
		for (File file : files) {
			file.delete();
		}
	}
}
