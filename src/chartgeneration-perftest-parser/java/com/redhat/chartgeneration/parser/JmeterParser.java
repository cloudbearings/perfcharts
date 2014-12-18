package com.redhat.chartgeneration.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class JmeterParser implements DataParser {

	@Override
	public void parse(InputStream in, OutputStream out) throws Exception {
		in.mark(512);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String firstLine = reader.readLine();
		if (firstLine == null || firstLine.isEmpty())
			return;
		in.reset();
		if (firstLine.startsWith("<?xml")) {
			new JmeterXMLParser().parse(in, out);
		} else {
			new JmeterCSVParser().parse(in, out);
		}
	}

	static void writeFields(OutputStreamWriter writer, Object... objs)
			throws IOException {
		if (objs.length < 1)
			return;
		writeField(writer, objs[0]);
		for (int i = 1; i < objs.length; ++i) {
			writer.write(',');
			writeField(writer, objs[i]);
		}
		writer.write('\n');
	}

	static void writeField(OutputStreamWriter writer, Object obj)
			throws IOException {
		writer.write('\"');
		writer.write(obj.toString().replace("\"", "\"\""));
		writer.write('\"');
	}

}
