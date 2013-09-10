package vn.luis.goldsmine.object;

public class ItemGoldUser {
	private int id;
	private String type; 
	private int anumb;
	private double price;
	private String date;
	public ItemGoldUser() { // Contructor Empty
		// TODO Auto-generated constructor stub
	}
	public ItemGoldUser(int anumb, double price, String date) { // Contructer Custom
		super();
		this.anumb = anumb;
		this.price = price;
		this.date = date;
	}
	public ItemGoldUser(String type, int anumb, double price, String date) { // Contructer Custom
		super();
		this.type = type;
		this.anumb = anumb;
		this.price = price;
		this.date = date;
	}
	public ItemGoldUser(int id, String type, int anumb, double price, String date) { // Contructer Full
		super();
		this.id = id;
		this.type = type;
		this.anumb = anumb;
		this.price = price;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAnumb() {
		return anumb;
	}
	public void setAnumb(int anumb) {
		this.anumb = anumb;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
