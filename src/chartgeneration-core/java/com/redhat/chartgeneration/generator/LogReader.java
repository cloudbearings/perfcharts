package com.redhat.chartgeneration.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.redhat.chartgeneration.common.Utilities;
import com.redhat.chartgeneration.model.DataMapper;

public class LogReader {
	public <T> List<T> read(InputStream in, DataMapper<T> mapper)
			throws IOException {
		List<T> rows = new ArrayList<T>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = reader.readLine()) != null) {
			rows.add(mapper.map(getFieldStrings(line)));
		}
		return rows;
	}

	public List<List<Object>> read(InputStream in/* , List<Class<?>> fieldTypes */)
			throws IOException {
		List<List<Object>> rows = new ArrayList<List<Object>>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = reader.readLine()) != null) {
			rows.add(getFields(line/* , fieldTypes */));
		}
		return rows;
	}

	private static String getField(String rawField) {
		if (rawField.length() >= 2 && rawField.startsWith("\"")
				&& rawField.endsWith("\"")) {
			return rawField.substring(1, rawField.length() - 1).replace("\"\"",
					"\"");
		}
		return rawField.replace("\"\"", "\"");
	}

	private static List<String> getFieldStrings(String line) {
		List<String> result = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(line, ",");
		while (tokenizer.hasMoreTokens()) {
			result.add(getField(tokenizer.nextToken()));
		}
		return result;
	}

	private static List<Object> getFields(String line) {
		List<Object> result = new ArrayList<Object>(10);
		StringTokenizer tokenizer = new StringTokenizer(line, ",");
		for (; tokenizer.hasMoreTokens();) {
			String fieldString = getField(tokenizer.nextToken());
			result.add(Utilities.parseString(fieldString));
		}
		return result;
	}
}
