package pl.squirrel.money.api;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SpendingCategory implements Comparable<SpendingCategory> {
	private String category;
	private String subcategory;

	public SpendingCategory(String category, String subcategory) {
		super();
		this.category = category;
		this.subcategory = subcategory;
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

	@Override
	public int hashCode() {
		return category == null ? 0 : category.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public int compareTo(SpendingCategory o) {
		return CompareToBuilder.reflectionCompare(this, o);
	}
}
