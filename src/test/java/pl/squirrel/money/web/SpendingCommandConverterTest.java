package pl.squirrel.money.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.joda.time.LocalDate;
import org.springframework.validation.BindingResult;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SpendingCommandConverterTest {

	private SpendingCommand command;
	private BindingResult binding;

	private Clock clock;
	private SpendingCommandConverter converter;

	@BeforeMethod
	public void setUp() {
		command = new SpendingCommand();
		binding = mock(BindingResult.class);
		clock = mock(Clock.class);
		converter = new SpendingCommandConverter();
	}

	@AfterMethod
	public void cleanUp() {
		command = null;
		binding = null;
		converter = null;
	}

	@Test
	public void shouldRejectWhenNoDate() {
		converter.convert(command, binding);

		verify(binding).rejectValue("spendingDate", "spendingDate.missing");
	}

	@Test(dataProvider = "InvalidDates")
	public void shouldRejectWhenDateFormatInvalid(String date) {
		command.setSpendingDate(date);

		converter.convert(command, binding);

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

		converter.convert(command, binding);

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

		converter.convert(command, binding);

		verifyZeroInteractions(binding);
		Assert.assertEquals(command.getSpendingDate(), expectedOut);
	}

	@DataProvider(name = "DaysInLastMonth")
	protected Object[][] daysInLastMonthProvider() {
		return new Object[][] { { "2012-07-20", "21", "21/6/12" },
				{ "2012-07-20", "30", "30/6/12" },
				{ "2012-03-02", "29", "29/2/12" } };
	}

	@Test(dataProvider = "InvalidDatesInLastMonth")
	public void shouldRejectWhenLastMonthDayIsTooBig(String today, String in) {
		setToday(today);
		command.setSpendingDate(in);

		converter.convert(command, binding);

		verify(binding).rejectValue("spendingDate",
				"spendingDate.formatInvalid");
	}

	@DataProvider(name = "InvalidDatesInLastMonth")
	protected Object[][] invalidDaysInLastMonthProvider() {
		return new Object[][] { { "2012-07-20", "31" }, { "2011-03-02", "29" } };
	}

	private void setToday(String today) {
		when(clock.today()).thenReturn(new LocalDate(today));
		converter.setClock(clock);
	}
}
