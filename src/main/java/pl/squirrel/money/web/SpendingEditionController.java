package pl.squirrel.money.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.squirrel.money.data.SpendingDao;
import pl.squirrel.money.entity.Spending;

@Controller
public class SpendingEditionController {
	@Autowired
	private Logger log;

	@Autowired
	private SpendingDao spendingDao;

	@RequestMapping({ "/spending_insert"})
	public String renderForm(ModelMap model) {
		model.addAttribute("spendings", spendingDao.getLastSpendings(100));
		model.addAttribute("command", new Spending());
		return "spending_insert";
	}
}
