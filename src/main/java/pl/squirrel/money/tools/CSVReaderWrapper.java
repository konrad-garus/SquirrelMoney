package pl.squirrel.money.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import au.com.bytecode.opencsv.CSVReader;

public class CSVReaderWrapper {

	private CSVReader reader;
	private List<String> columns;
	private Map<String, Integer> columnIndexes;

	private int lineNumber;
	private String[] nextRow;

	public CSVReaderWrapper(InputStream inputStream) throws IOException {
		reader = new CSVReader(new InputStreamReader(inputStream));
		readHeader();
	}

	private void readHeader() throws IOException {
		columns = Collections
				.unmodifiableList(Arrays.asList(reader.readNext()));
		columnIndexes = new HashMap<String, Integer>();
		for (int i = 0; i < columns.size(); i++) {
			columnIndexes.put(columns.get(i), i);
		}
	}

	public List<String> getColumns() {
		return columns;
	}

	public boolean hasNext() throws IOException {
		nextRow = reader.readNext();
		lineNumber++;
		return nextRow != null;
	}

	public String getString(String column) {
		String val = nextRow[columnIndexes.get(column)];
		if (StringUtils.isEmpty(val)) {
			return null;
		} else {
			return val.trim();
		}
	}

	public BigDecimal getBigDecimal(String column) {
		String val = getString(column);
		return val == null ? null : new BigDecimal(val);
	}

	public LocalDate getLocalDate(String column) {
		String val = getString(column);
		return val == null ? null : new LocalDate(val);
	}

	public int getLineNumber() {
		return lineNumber;
	}
}
