package pl.squirrel.money.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pl.squirrel.money.data.SpendingDao;
import pl.squirrel.money.tools.SpendingImporter;

@Controller
public class SpendingImportController {
	@Autowired
	private Logger log;

	@Autowired
	private SpendingDao spendingDao;

	@Autowired
	private SpendingImporter spendingImporter;

	@RequestMapping("/spending_import.html")
	public String showImport() {
		return "spending_import";
	}

	@RequestMapping(value = "/spending_import.html", method = RequestMethod.POST)
	public String importFile(@RequestParam("file") MultipartFile file)
			throws IOException {
		log.debug("Import file size: " + file.getBytes().length + ", type: "
				+ file.getContentType());
		spendingImporter.importSpendings(file.getInputStream());
		return "redirect:/spending_list.html";
	}
}
