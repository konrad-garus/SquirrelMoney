package pl.squirrel.money.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.squirrel.money.data.SpendingDao;

@Controller
public class SpendingEditionController {
	private Logger log;

	@Autowired
	private SpendingDao spendingDao;

	@RequestMapping({ "/spending_insert" })
	public String renderForm(ModelMap model) {
		SpendingCommand command = new SpendingCommand();
		command.setSpendingDate(SpendingDateConverter
				.dateToString(new LocalDate()));
		return renderForm(command, model);
	}

	@RequestMapping(value = "/spending_insert", method = RequestMethod.POST)
	public String addSpending(
			@ModelAttribute("command") @Valid SpendingCommand spending,
			BindingResult result, ModelMap model) {
		new SpendingDateConverter().convertDate(spending, result);
		spendingDao.persist(spending.toSpending());
		return renderForm(spending, model);
	}

	private String renderForm(SpendingCommand command, ModelMap model) {
		model.addAttribute("spendings", spendingDao.getLastSpendings(100));
		model.addAttribute("command", command);
		return "spending_insert";
	}

	@RequestMapping(value = "/spending_insert", params = "term")
	public void autocomplete(@RequestParam("term") String term,
			HttpServletResponse response) throws IOException {
		response.getWriter().println(
				"[{\"value\": \"Jada jada\", \"label\": \"Label" + term
						+ "\"}]");
	}
}
