package pl.squirrel.money.web;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.joda.time.LocalDate;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SpendingDateConverterTest {

	private SpendingCommand command;
	@Mock
	private BindingResult binding;
	@Mock
	private Clock clock;

	private SpendingDateConverter converter;

	@BeforeMethod
	public void setUp() {
		command = new SpendingCommand();
		converter = new SpendingDateConverter();
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void cleanUp() {
		command = null;
		binding = null;
		converter = null;
	}

	@Test
	public void shouldRejectWhenNoDate() {
		converter.convertDate(command, binding);

		verify(binding).rejectValue("spendingDate", "spendingDate.missing");
	}

	@Test(dataProvider = "InvalidDates")
	public void shouldRejectWhenDateFormatInvalid(String date) {
		command.setSpendingDate(date);

		converter.convertDate(command, binding);

		verify(binding).rejectValue("spendingDate",
				"spendingDate.formatInvalid");
	}

	@DataProvider(name = "InvalidDates")
	protected Object[][] invalidDatesProvider() {
		return new Object[][] { { "0" }, { "123" }, { "32" }, { "5/5/5/5" },
				{ "5/" }, { "5 january" }, { "whatever" } };
	}

	@Test(dataProvider = "DaysInThisMonth")
	public void shouldParseDateInThisMonthFromDay(String today, String in,
			String expectedOut) {
		setToday(today);
		command.setSpendingDate(in);

		converter.convertDate(command, binding);

		verifyZeroInteractions(binding);
		Assert.assertEquals(command.getSpendingDate(), expectedOut);
	}

	@DataProvider(name = "DaysInThisMonth")
	protected Object[][] daysInThisMonthProvider() {
		return new Object[][] { { "2012-07-20", "1", "1/7/12" },
				{ "2012-07-20", "15", "15/7/12" },
				{ "2012-07-20", "20", "20/7/12" } };
	}

	@Test(dataProvider = "DaysInLastMonth")
	public void shouldParseDateInLastMonthFromDay(String today, String in,
			String expectedOut) {
		setToday(today);
		command.setSpendingDate(in);

		converter.convertDate(command, binding);

		verifyZeroInteractions(binding);
		Assert.assertEquals(command.getSpendingDate(), expectedOut);
	}

	@DataProvider(name = "DaysInLastMonth")
	protected Object[][] daysInLastMonthProvider() {
		return new Object[][] { { "2012-07-20", "21", "21/6/12" },
				{ "2012-07-20", "30", "30/6/12" },
				{ "2012-03-02", "29", "29/2/12" } };
	}

	@Test(dataProvider = "DayMonth")
	public void shouldParseDateInThePastFromDayMonth(String today, String in,
			String expectedOut) {
		setToday(today);
		command.setSpendingDate(in);

		converter.convertDate(command, binding);

		verifyZeroInteractions(binding);
		Assert.assertEquals(command.getSpendingDate(), expectedOut);
	}

	@DataProvider(name = "DayMonth")
	protected Object[][] dayMonthProvider() {
		return new Object[][] { { "2012-07-20", "20/7", "20/7/12" },
				{ "2012-07-20", "20/6", "20/6/12" },
				{ "2012-07-20", "20/9", "20/9/11" } };
	}

	@Test(dataProvider = "InvalidDayMonth")
	public void shouldRejectInvalidDateFromDayMonth(String today, String in) {
		setToday(today);
		command.setSpendingDate(in);

		converter.convertDate(command, binding);

		verify(binding).rejectValue("spendingDate",
				"spendingDate.formatInvalid");
	}

	@DataProvider(name = "InvalidDayMonth")
	protected Object[][] invalidDayMonthProvider() {
		return new Object[][] { { "2012-07-20", "20/13" },
				{ "2012-07-20", "30/2" }, { "2012-07-20", "32/7" } };
	}

	@Test(dataProvider = "DayMonthYear")
	public void shouldParseDayMonthYear(String in, String expectedOut) {
		command.setSpendingDate(in);

		converter.convertDate(command, binding);

		verifyZeroInteractions(binding);
		Assert.assertEquals(command.getSpendingDate(), expectedOut);
	}

	@DataProvider(name = "DayMonthYear")
	protected Object[][] dayMonthYearProvider() {
		return new Object[][] { { "2012-07-20", "20/7/12" },
				{ "20/7/2012", "20/7/12" }, { "20/7/12", "20/7/12" } };
	}

	private void setToday(String today) {
		when(clock.today()).thenReturn(new LocalDate(today));
		converter.setClock(clock);
	}
}
