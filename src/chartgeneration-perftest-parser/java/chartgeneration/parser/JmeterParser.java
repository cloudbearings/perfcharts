package chartgeneration.parser;

import java.io.*;

/**
 * The parser converts Jmeter test logs to data tables (in CSV format). The raw
 * data is can be XML or CSV format.
 * 
 * @author Rayson Zhu
 *
 */
public class JmeterParser implements DataParser {

	@Override
	public void parse(InputStream in, OutputStream out) throws Exception {
		final int BUFFER_SIZE = 512;
		in.mark(BUFFER_SIZE);
		byte[] buffer = new byte[BUFFER_SIZE];
		in.read(buffer, 0, BUFFER_SIZE);
		String firstLine = new String(buffer, "ascii");
		in.reset();
		if (firstLine.startsWith("<?xml")) {
			new JmeterXMLParser().parse(in, out);
		} else {
			new JmeterCSVParser().parse(in, out);
		}
	}

	static void writeFields(Writer writer, Object... objs)
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

	static void writeField(Writer writer, Object obj)
			throws IOException {
		writer.write('\"');
		writer.write(obj.toString().replace("\"", "\"\""));
		writer.write('\"');
	}

}
