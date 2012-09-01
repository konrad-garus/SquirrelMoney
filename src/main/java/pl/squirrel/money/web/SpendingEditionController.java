package pl.squirrel.money.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.squirrel.money.api.SpendingCategory;
import pl.squirrel.money.data.SpendingDao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class SpendingEditionController {
	private Logger log = LoggerFactory
			.getLogger(SpendingEditionController.class);

	private SpendingDao spendingDao;

	private Gson gson;

	@Autowired
	public SpendingEditionController(SpendingDao spendingDao) {
		this.spendingDao = spendingDao;
		this.gson = new GsonBuilder().create();
	}

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

	@RequestMapping(value = "/spending_insert", params = { "term",
			"type=category" })
	public void autocompleteCategory(@RequestParam("term") String term,
			HttpServletResponse response) throws IOException {
		List<SpendingCategory> categories = new ArrayList<SpendingCategory>(
				spendingDao.findSpendingCategoriesByNamePart(term));
		for (String category : getDistinctCategoryNames(categories)) {
			categories.add(new SpendingCategory(category, null));
		}
		Collections.sort(categories);

		String json = gson.toJson(categories);
		response.getWriter().print(json);
		response.getWriter().flush();
	}

	private Set<String> getDistinctCategoryNames(
			List<SpendingCategory> categories) {
		Set<String> result = new LinkedHashSet<String>();
		for (SpendingCategory cat : categories) {
			result.add(cat.getCategory());
		}
		return result;
	}

	@RequestMapping(value = "/spending_insert", params = { "term",
			"type=subcategory" })
	public void autocompleteSubcategory(
			@RequestParam("term") String term,
			@RequestParam(required = false, value = "category") String category,
			HttpServletResponse response) throws IOException {
		log.debug("Autosuggest subcategory for " + category + " / " + term);
		List<SpendingCategory> categories = new ArrayList<SpendingCategory>(
				spendingDao.findSpendingSubcategoriesByCategoryAndNameParts(
						category, term));
		Collections.sort(categories);

		String json = gson.toJson(categories);
		response.getWriter().print(json);
		response.getWriter().flush();
	}

	@RequestMapping(value = "/spending_insert", params = { "term",
			"type=detail" })
	public void autocompleteDetail(@RequestParam("term") String term,
			HttpServletResponse response) throws IOException {
		// response.getWriter().print("[]");
		response.getWriter().println(
				"[{\"value\": \"Jada jada\", \"label\": \"Label" + term
						+ "\"}]");
	}
}
