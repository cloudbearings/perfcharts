package com.redhat.chartgeneration.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class JmeterParser implements DataParser {

	public void parse(InputStream in, OutputStream out)
			throws XMLStreamException, IOException {
		XMLStreamReader reader = XMLInputFactory.newInstance()
				.createXMLStreamReader(in);
		OutputStreamWriter writer = new OutputStreamWriter(out);
		int level = 0;
		String label = null;
		long timestamp = 0;
		int rt = 0;
		int latency = 0;
		int threads = 0;
		int bytes = 0;
		boolean error = false;
		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamReader.START_ELEMENT:
				++level;
				boolean isSample = "sample".equals(reader.getLocalName());
				boolean isHttpSample = !isSample
						&& "httpSample".equals(reader.getLocalName());
				if (level == 2 && (isSample || isHttpSample)) {
					label = reader.getAttributeValue(null, "lb");
					timestamp = Long.parseLong(reader.getAttributeValue(null,
							"ts"));
					rt = Integer.parseInt(reader.getAttributeValue(null, "t"));
					latency = Integer.parseInt(reader.getAttributeValue(null,
							"lt"));
					threads = Integer.parseInt(reader.getAttributeValue(null,
							"ng"));
					bytes = Integer.parseInt(reader.getAttributeValue(null,
							"by"));
					error = !Boolean.parseBoolean(reader.getAttributeValue(
							null, "s"));
					writeFields(writer, "TX-" + label + (error ? "-F" : "-S"),
							timestamp, threads, error ? '1' : '0', latency, rt,
							bytes);
				} 
				if (isHttpSample) {
					label = reader.getAttributeValue(null, "lb");
					timestamp = Long.parseLong(reader.getAttributeValue(null,
							"ts"));
					rt = Integer.parseInt(reader.getAttributeValue(null, "t"));
					error = !Boolean.parseBoolean(reader.getAttributeValue(
							null, "s"));
					writeFields(
							writer,
							"HIT-" + (error ? "F-" : "S-")
									+ label.replace("\"", "\"\""), timestamp,
							rt);
				}
				break;
			case XMLStreamReader.END_ELEMENT:
				--level;
				break;
			}
		}
		writer.flush();
	}

	private static void writeFields(OutputStreamWriter writer, Object... objs)
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

	private static void writeField(OutputStreamWriter writer, Object obj)
			throws IOException {
		writer.write('\"');
		writer.write(obj.toString().replace("\"", "\"\""));
		writer.write('\"');
	}
}
