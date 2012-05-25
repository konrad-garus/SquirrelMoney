package pl.squirrel.money.api;

import java.math.BigDecimal;

public class SpendingTotal {
	private String category;
	private String date;
	private BigDecimal value;

	public SpendingTotal() {
	}

	public SpendingTotal(String category, String date, BigDecimal value) {
		this.category = category;
		this.date = date;
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
