package pl.squirrel.money.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
public class Spending {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spending_sequence")
	@SequenceGenerator(name = "spending_sequence", sequenceName = "spending_sequence")
	@Column(name = "spending_id")
	private long id;

	private String category;

	private String subcategory;

	private String name;

	@Column(name = "spending_date")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate spendingDate;

	private Integer quantity;

	@Column(name = "unit_price")
	private BigDecimal unitPrice;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public LocalDate getSpendingDate() {
		return spendingDate;
	}

	public void setSpendingDate(LocalDate spendingDate) {
		this.spendingDate = spendingDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

}
