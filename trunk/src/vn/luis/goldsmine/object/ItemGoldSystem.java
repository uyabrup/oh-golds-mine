package vn.luis.goldsmine.object;

public class ItemGoldSystem {
	private String type;
	private Double buy;
	private Double sell;
	private String date;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getBuy() {
		return buy;
	}
	public void setBuy(Double buy) {
		this.buy = buy;
	}
	public Double getSell() {
		return sell;
	}
	public void setSell(Double sell) {
		this.sell = sell;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ItemGoldSystem(String type, Double buy, Double sell, String date) {
		super();
		this.type = type;
		this.buy = buy;
		this.sell = sell;
		this.date = date;
	}
}
