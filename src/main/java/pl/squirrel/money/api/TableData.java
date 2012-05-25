package pl.squirrel.money.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableData {
	private List<String> categories = new ArrayList<String>();
	private List<String> rowHeaders = new ArrayList<String>();
	private Map<String, String> values = new HashMap<String, String>();

	public List<String> getColumnHeaders() {
		return Collections.unmodifiableList(categories);
	}

	public List<String> getRowHeaders() {
		return Collections.unmodifiableList(rowHeaders);
	}

	public String getValueAt(String row, String column) {
		return values.get(key(row, column));
	}

	public void put(String row, String column, String value) {
		if (!categories.contains(column)) {
			categories.add(column);
		}
		if (!rowHeaders.contains(row)) {
			rowHeaders.add(row);
		}
		values.put(key(row, column), value);
	}

	private String key(String row, String column) {
		return row + "!!!" + column;
	}

}
