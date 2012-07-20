package pl.squirrel.money.web;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.squirrel.money.data.SpendingDao;

@Controller
public class SpendingEditionController {
	@Autowired
	private Logger log;

	@Autowired
	private SpendingDao spendingDao;

	@RequestMapping({ "/spending_insert" })
	public String renderForm(ModelMap model) {
		SpendingCommand command = new SpendingCommand();
		command.setSpendingDate(dateToString(new LocalDate()));
		return renderForm(command, model);
	}

	@RequestMapping(value = "/spending_insert", method = RequestMethod.POST)
	public String addSpending(
			@ModelAttribute("command") @Valid SpendingCommand spending,
			BindingResult result, ModelMap model) {
		new SpendingCommandConverter().convert(spending, result);

		return renderForm(spending, model);

	}

	private String renderForm(SpendingCommand command, ModelMap model) {
		model.addAttribute("spendings", spendingDao.getLastSpendings(100));
		model.addAttribute("command", command);
		return "spending_insert";
	}

	private String dateToString(LocalDate date) {
		return DateTimeFormat.forPattern("d/M/yy").print(date);
	}

	private LocalDate stringToDate(String date) {
		date = date.trim();
		if (date.matches("\\d{1,2}")) {
			int day = Integer.parseInt(date);
			// int today =
		}
		return null;
	}
}
