package pl.squirrel.money.tools;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import pl.squirrel.money.data.SpendingDao;
import pl.squirrel.money.entity.Spending;

public class SpendingImporter {
	@Autowired
	private Logger log;

	@Autowired
	private SpendingDao spendingDao;

	public void importSpendings(InputStream inputStream) throws IOException {
		log.info("Starting import of spendings");
		CSVReaderWrapper reader = new CSVReaderWrapper(inputStream);
		log.info("Got columns: " + reader.getColumns());
		int imported = 0;
		while (reader.hasNext()) {
			Spending s = new Spending();
			s.setCategory(reader.getString("Kategoria"));
			s.setSubcategory(reader.getString("Podkategoria"));
			s.setName(reader.getString("Opis"));
			s.setTotalPrice(reader.getBigDecimal("Kwota"));
			s.setSpendingDate(reader.getLocalDate("Data"));
			if (s.getCategory() != null || s.getSubcategory() != null
					|| s.getName() != null) {
				spendingDao.persist(s);
				imported++;
			} else {
				log.warn("Skipping line {}", reader.getLineNumber());
			}
		}
		log.info("Imported {} spendings", imported);
	}
}
