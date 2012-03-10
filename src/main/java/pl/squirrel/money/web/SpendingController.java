package pl.squirrel.money.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.squirrel.money.data.SpendingDao;

@Controller
public class SpendingController {
	@Autowired
	private SpendingDao spendingDao;

	@RequestMapping("/spending.html")
	public String mav(ModelMap model) {
		model.addAttribute("spendings", spendingDao.getAllSpendings());
		return "spending_list";
	}
}
