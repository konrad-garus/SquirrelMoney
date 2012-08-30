package pl.squirrel.money.web;

import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.squirrel.money.api.SpendingCategory;
import pl.squirrel.money.data.SpendingDao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SpendingEditionControllerTest {

	@Mock
	SpendingDao dao;

	@Mock
	HttpServletResponse response;

	ByteArrayOutputStream out;
	PrintWriter writer;

	Gson gson;

	SpendingEditionController controller;

	@BeforeMethod
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		out = new ByteArrayOutputStream();
		writer = new PrintWriter(out);
		when(response.getWriter()).thenReturn(writer);

		controller = new SpendingEditionController(dao);

		gson = new GsonBuilder().create();
	}

	@Test
	public void shouldReturnEmptyListWhenNoMatchingCategory()
			throws IOException {
		when(dao.findSpendingCategoriesByNamePart("Fo")).thenReturn(
				Arrays.<SpendingCategory> asList());

		controller.autocompleteCategory("Fo", response);

		assertResponseEquals("[]");
	}

	@Test
	public void shouldSuggestCategory() throws IOException {
		SpendingCategory dairy = new SpendingCategory("Food", "Dairy");
		SpendingCategory meat = new SpendingCategory("Food", "Meat");
		when(dao.findSpendingCategoriesByNamePart("Fo")).thenReturn(
				Arrays.asList(dairy, meat));

		controller.autocompleteCategory("Fo", response);

		StringBuilder expect = new StringBuilder();
		expect.append("[");
		expect.append("{\"category\":\"Food\"},");
		expect.append("{\"category\":\"Food\",\"subcategory\":\"Dairy\"},");
		expect.append("{\"category\":\"Food\",\"subcategory\":\"Meat\"}");
		expect.append("]");

		assertResponseEquals(expect.toString());
	}

	@Test
	public void shouldReturnEmptyListWhenNoMatchingSubcategory()
			throws IOException {
		when(dao.findSpendingSubcategoriesByCategoryAndNameParts(null, "Da"))
				.thenReturn(Arrays.<SpendingCategory> asList());

		controller.autocompleteSubcategory("Da", null, response);

		assertResponseEquals("[]");
	}

	@Test
	public void shouldSuggestSubcategories() throws IOException {
		SpendingCategory dairy = new SpendingCategory("Food", "Dairy");
		SpendingCategory meat = new SpendingCategory("Car", "Daboom");
		when(dao.findSpendingSubcategoriesByCategoryAndNameParts(null, "Da"))
				.thenReturn(Arrays.asList(dairy, meat));

		controller.autocompleteSubcategory(null, "Da", response);

		StringBuilder expect = new StringBuilder();
		expect.append("[");
		expect.append("{\"category\":\"Car\",\"subcategory\":\"Daboom\"},");
		expect.append("{\"category\":\"Food\",\"subcategory\":\"Dairy\"}");
		expect.append("]");

		assertResponseEquals(expect.toString());
	}

	// private void assertResponseEquals(List<SpendingCategory> expected) {
	// Assert.assertEquals(gson.toJson(expected),
	// new String(out.toByteArray()));
	// }

	private void assertResponseEquals(String expected) {
		Assert.assertEquals(new String(out.toByteArray()), expected);
	}
}
