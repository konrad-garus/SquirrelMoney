package pl.squirrel.money.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new SpendingCommandValidator());
	}

	@RequestMapping({ "/spending_insert" })
	public String renderForm(ModelMap model) {
		model.addAttribute("spendings", spendingDao.getLastSpendings(100));
		model.addAttribute("command", new SpendingCommand());
		return "spending_insert";
	}

	@RequestMapping(value = "/spending_insert", method = RequestMethod.POST)
	public String addSpending(@ModelAttribute SpendingCommand spending,
			BindingResult result, ModelMap model) {
		System.out.println(spending);
		if (spending != null) {
			System.out.println(spending.getName());
			System.out.println(spending.getCategory());
		}
		model.addAttribute("command", spending);
		return "spending_insert";

	}
}
