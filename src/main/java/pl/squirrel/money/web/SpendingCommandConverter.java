package pl.squirrel.money.web;

import org.apache.commons.lang.StringUtils;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.validation.BindingResult;

public class SpendingCommandConverter {
	private Clock clock = new Clock();

	public void convert(SpendingCommand spending, BindingResult result) {
		convertDate(spending, result);
	}

	private void convertDate(SpendingCommand spending, BindingResult result) {
		if (StringUtils.isBlank(spending.getSpendingDate())) {
			result.rejectValue("spendingDate", "spendingDate.missing");
			return;
		}

		String str = spending.getSpendingDate().trim();
		if (str.matches("\\d{1,2}")) {
			convertDateFromDay(spending, result, str);
		} else {
			result.rejectValue("spendingDate", "spendingDate.formatInvalid");
		}
	}

	private void convertDateFromDay(SpendingCommand spending,
			BindingResult result, String str) {
		int day = Integer.parseInt(str);
		try {
			LocalDate date;
			if (day > clock.today().getDayOfMonth()) {
				date = clock.today().minusMonths(1).withDayOfMonth(day);
			} else {
				date = clock.today().withDayOfMonth(day);
			}
			spending.setSpendingDate(dateToString(date));
		} catch (IllegalFieldValueException e) {
			rejectDateFormat(result);
		}
	}

	private void rejectDateFormat(BindingResult result) {
		result.rejectValue("spendingDate", "spendingDate.formatInvalid");
	}

	// TODO: Duplication with controller
	private String dateToString(LocalDate date) {
		return DateTimeFormat.forPattern("d/M/yy").print(date);
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}
}
