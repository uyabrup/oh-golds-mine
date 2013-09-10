package vn.luis.goldsmine.object;

public class ItemGoldSystem {
	private Double buy;
	private Double sell;
	private String date;
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
	public ItemGoldSystem(Double buy, Double sell, String date) {
		super();
		this.buy = buy;
		this.sell = sell;
		this.date = date;
	}
}
