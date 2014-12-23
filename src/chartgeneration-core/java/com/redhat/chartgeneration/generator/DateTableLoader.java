package com.redhat.chartgeneration.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.Utilities;
import com.redhat.chartgeneration.model.DataTable;

/**
 * Loads the data table file (in CSV format) to memory
 * 
 * @author Rayson Zhu
 *
 */
public class DateTableLoader {
	/**
	 * Load the data table file (in CSV format) to memory.
	 * 
	 * @param in
	 *            the {@link InputStream} contains the content of data table
	 *            file
	 * @return the data table
	 * @throws IOException
	 */
	public DataTable load(InputStream in) throws IOException {
		List<List<Object>> rows = new ArrayList<List<Object>>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = reader.readLine()) != null) {
			rows.add(getFields(line));
		}
		return new DataTable(rows);
	}

	private static String getField(String rawField) {
		if (rawField.length() >= 2 && rawField.startsWith("\"")
				&& rawField.endsWith("\"")) {
			return rawField.substring(1, rawField.length() - 1).replace("\"\"",
					"\"");
		}
		return rawField.replace("\"\"", "\"");
	}

	private static List<Object> getFields(String line) {
		String[] columns = line.split(",");
		List<Object> result = new ArrayList<Object>(columns.length);
		for (String col : columns) {
			result.add(Utilities.parseString(getField(col)));
		}
		return result;
	}
}
