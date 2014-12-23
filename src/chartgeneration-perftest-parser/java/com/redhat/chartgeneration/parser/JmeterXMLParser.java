package com.redhat.chartgeneration.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
/**
 * The parser converts Jmeter test logs to data tables (in CSV format). The raw
 * data must be XML format.
 * 
 * @author Rayson Zhu
 *
 */
public class JmeterXMLParser implements DataParser {

	public void parse(InputStream in, OutputStream out)
			throws XMLStreamException, IOException {
		XMLStreamReader reader = XMLInputFactory.newInstance()
				.createXMLStreamReader(in);
		OutputStreamWriter writer = new OutputStreamWriter(out);

		Date startTime = Settings.getInstance().getStartTime();
		Date endTime = Settings.getInstance().getEndTime();
		long startTimeVal = startTime == null ? -1 : startTime.getTime();
		long endTimeVal = endTime == null ? -1 : endTime.getTime();
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

					if (startTimeVal > 0 && timestamp + rt < startTimeVal
							|| endTimeVal > 0 && timestamp + rt > endTimeVal)
						continue;
					latency = Integer.parseInt(reader.getAttributeValue(null,
							"lt"));
					threads = Integer.parseInt(reader.getAttributeValue(null,
							"ng"));
					bytes = Integer.parseInt(reader.getAttributeValue(null,
							"by"));
					error = !Boolean.parseBoolean(reader.getAttributeValue(
							null, "s"));
					JmeterParser.writeFields(writer, "TX-" + label
							+ (error ? "-F" : "-S"), timestamp, threads,
							error ? '1' : '0', latency, rt, bytes);
				}
				if (isHttpSample) {
					label = reader.getAttributeValue(null, "lb");
					timestamp = Long.parseLong(reader.getAttributeValue(null,
							"ts"));
					rt = Integer.parseInt(reader.getAttributeValue(null, "t"));

					if (startTimeVal > 0 && timestamp < startTimeVal
							|| endTimeVal > 0 && timestamp > endTimeVal)
						continue;

					error = !Boolean.parseBoolean(reader.getAttributeValue(
							null, "s"));
					JmeterParser.writeFields(
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
}
