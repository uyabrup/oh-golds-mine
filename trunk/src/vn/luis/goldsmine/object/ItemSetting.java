package vn.luis.goldsmine.object;

public class ItemSetting{
	private int id;
	private String name;
	private String value;
	public ItemSetting() {
		
	}
	public ItemSetting(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	public ItemSetting(int id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
