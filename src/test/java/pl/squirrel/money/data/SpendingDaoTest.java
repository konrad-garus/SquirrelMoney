package pl.squirrel.money.data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.squirrel.money.api.SpendingCategory;
import pl.squirrel.money.entity.Spending;

@ContextConfiguration(classes = { DaoTestConfig.class })
public class SpendingDaoTest extends
		AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private SpendingDao spendingDao;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void shouldReturnEmptyListWhenNoCategories() {
		Assert.assertEquals(spendingDao.findSpendingCategoriesByNamePart("Fo"),
				Collections.emptyList());
	}

	@Test
	public void shouldReturnEmptyListWhenNoMatchingCategories() {
		insertSpending("Car", "Gas", "100");

		Assert.assertEquals(spendingDao.findSpendingCategoriesByNamePart("Fo"),
				Collections.emptyList());
	}

	@Test
	public void shouldReturnMatchingCategory() {
		insertSpending("Food", "Dairy", "10");

		SpendingCategory dairy = new SpendingCategory("Food", "Dairy");
		List<SpendingCategory> expect = Arrays.asList(dairy);

		List<SpendingCategory> got = spendingDao
				.findSpendingCategoriesByNamePart("Fo");

		Assert.assertEquals(got, expect);
	}

	@Test
	public void shouldReturnMatchingCategoryOnCaseMismatch() {
		insertSpending("Food", "Dairy", "10");

		SpendingCategory dairy = new SpendingCategory("Food", "Dairy");
		List<SpendingCategory> expect = Arrays.asList(dairy);

		List<SpendingCategory> got = spendingDao
				.findSpendingCategoriesByNamePart("fo");
		Assert.assertEquals(got, expect);

		got = spendingDao.findSpendingCategoriesByNamePart("FO");

		Assert.assertEquals(got, expect);
	}

	@Test
	public void shouldReturnOneCategoryIfManyMatchingItemsAndOrderByName() {
		insertSpending("Food", "Dairy", "10");
		insertSpending("Food", "Dairy", "10");
		insertSpending("Food", "Bread", "5");
		insertSpending("Foobar", null, "5");

		SpendingCategory foobar = new SpendingCategory("Foobar", null);
		SpendingCategory bread = new SpendingCategory("Food", "Bread");
		SpendingCategory dairy = new SpendingCategory("Food", "Dairy");
		List<SpendingCategory> expect = Arrays.asList(foobar, bread, dairy);

		List<SpendingCategory> got = spendingDao
				.findSpendingCategoriesByNamePart("Fo");

		Assert.assertEquals(got, expect);
	}

	@Test
	public void shouldReturnEmptyListWhenNoMatchingSubcategory() {
		insertSpending("Food", "Dairy", "10");

		Assert.assertEquals(spendingDao
				.findSpendingSubcategoriesByCategoryAndNameParts(null, "Ga"),
				Collections.emptyList());
	}

	@Test
	public void shouldReturnSubcategoryForNullCategory() {
		insertSpending("Car", "Gas", "10");

		List<SpendingCategory> expect = Arrays.asList(new SpendingCategory(
				"Car", "Gas"));

		Assert.assertEquals(spendingDao
				.findSpendingSubcategoriesByCategoryAndNameParts(null, "Ga"),
				expect);
	}

	@Test
	public void shouldReturnSubcategoryForEmptyCategory() {
		insertSpending("Car", "Gas", "10");

		List<SpendingCategory> expect = Arrays.asList(new SpendingCategory(
				"Car", "Gas"));

		Assert.assertEquals(spendingDao
				.findSpendingSubcategoriesByCategoryAndNameParts("", "Ga"),
				expect);
	}

	@Test
	public void shouldReturnSubcategoryForMatchingCategory() {
		insertSpending("Car", "Gas", "10");
		insertSpending("House", "Gas", "10");

		List<SpendingCategory> expect = Arrays.asList(new SpendingCategory(
				"Car", "Gas"));

		Assert.assertEquals(spendingDao
				.findSpendingSubcategoriesByCategoryAndNameParts("C", "Ga"),
				expect);
	}

	private void insertSpending(String category, String subcategory,
			String price) {
		Spending spending = new Spending();
		spending.setCategory(category);
		spending.setSubcategory(subcategory);
		spending.setTotalPrice(new BigDecimal(price));
		entityManager.persist(spending);
		entityManager.flush();
	}
}
