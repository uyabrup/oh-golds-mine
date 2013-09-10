package vn.luis.goldsmine.object;

import android.graphics.drawable.Drawable;

public class ItemMenu {
	private Drawable image;
	private String title;
	public ItemMenu(Drawable icon_menu, String title) {
		super();
		this.image = icon_menu;
		this.title = title;
	}
	public Drawable getImage() {
		return image;
	}
	public void setImage(Drawable image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
