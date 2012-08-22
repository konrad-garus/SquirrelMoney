package pl.squirrel.money.web;

import org.apache.commons.lang.StringUtils;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.validation.BindingResult;

public class SpendingDateConverter {
	private Clock clock = new Clock();

	public void convertDate(SpendingCommand spending, BindingResult result) {
		if (StringUtils.isBlank(spending.getSpendingDate())) {
			result.rejectValue("spendingDate", "spendingDate.missing");
			return;
		}

		String str = spending.getSpendingDate().trim();
		LocalDate convertedDate = null;
		try {
			if (str.matches("\\d{1,2}")) {
				convertedDate = convertDateFromDay(str);
			} else if (str.matches("\\d{1,2}/\\d{1,2}")) {
				convertedDate = convertDateFromDayMonth(str);
			} else if (str.matches("\\d{4}-\\d{2}-\\d{2}")) {
				convertedDate = convertDateFromDayMonthYearIso(str);
			} else if (str.matches("\\d{1,2}/\\d{1,2}/\\d{2}")
					|| str.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
				convertedDate = convertDateFromDayMonthYear(str);
			}

		} catch (IllegalFieldValueException e) {
		}
		if (convertedDate == null) {
			result.rejectValue("spendingDate", "spendingDate.formatInvalid");
		} else {
			spending.setSpendingDate(dateToString(convertedDate));
			spending.setSpendingDateConverted(convertedDate);
		}
	}

	private LocalDate convertDateFromDay(String str) {
		int day = Integer.parseInt(str);
		LocalDate today = clock.today();
		LocalDate date;
		if (day > today.getDayOfMonth()) {
			date = today.minusMonths(1).withDayOfMonth(day);
		} else {
			date = today.withDayOfMonth(day);
		}
		return date;
	}

	private LocalDate convertDateFromDayMonth(String str) {
		String[] split = str.split("/");
		int day = Integer.parseInt(split[0]);
		int month = Integer.parseInt(split[1]);
		LocalDate today = clock.today();
		LocalDate date = new LocalDate(today.getYear(), month, day);
		if (date.isAfter(today)) {
			date = date.minusYears(1);
		}
		return date;
	}

	private LocalDate convertDateFromDayMonthYearIso(String str) {
		return DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(str);
	}

	private LocalDate convertDateFromDayMonthYear(String str) {
		return DateTimeFormat.forPattern("d/M/yy").parseLocalDate(str);
	}

	public static String dateToString(LocalDate date) {
		return DateTimeFormat.forPattern("d/M/yy").print(date);
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}
}
