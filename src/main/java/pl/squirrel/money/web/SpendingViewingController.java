package pl.squirrel.money.web;

import java.text.NumberFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.squirrel.money.api.SpendingTotal;
import pl.squirrel.money.api.TableData;
import pl.squirrel.money.data.SpendingDao;

@Controller
public class SpendingViewingController {
	@Autowired
	private SpendingDao spendingDao;

	@RequestMapping("/spending_list.html")
	public String renderSpendingList(ModelMap model) {
		model.addAttribute("spendings", spendingDao.getAllSpendings());
		return "spending_list";
	}

	@RequestMapping("/spending_category_monthly.html")
	public String renderMonthlyTotals(ModelMap model) {
		model.addAttribute("spendings",
				mapTotals(spendingDao.getTotalsByCategory()));
		return "spending_category_monthly";
	}

	private TableData mapTotals(List<SpendingTotal> totals) {
		TableData result = new TableData();
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		for (SpendingTotal total : totals) {
			result.put(total.getDate(), total.getCategory(),
					nf.format(total.getValue()));
		}
		return result;
	}
}
