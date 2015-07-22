package chartgeneration.parser;

import java.io.*;
import java.util.Date;
import java.util.logging.Logger;

/**
 * The parser converts Jmeter test logs to data tables (in CSV format). The raw
 * data must be CSV format.
 * 
 * @author Rayson Zhu
 *
 */
public class JmeterCSVParser implements DataParser {

	private static Logger LOGGER = Logger.getLogger(JmeterCSVParser.class
			.getName());
	@Override
	public void parse(InputStream in, OutputStream out) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

		Date startTime = Settings.getInstance().getStartTime();
		Date endTime = Settings.getInstance().getEndTime();
		long startTimeVal = startTime == null ? -1 : startTime.getTime();
		long endTimeVal = endTime == null ? -1 : endTime.getTime();

		String line;
		while ((line = reader.readLine()) != null) {
			String[] fields = line.split(",");
			long timestamp = Long.parseLong(fields[0]);
			if (timestamp == 0){
				LOGGER.warning("Skip invalid data row: " + line);
				continue;
			}
			int rt = Integer.parseInt(fields[1]);
			if (startTimeVal > 0 && timestamp + rt < startTimeVal
					|| endTimeVal > 0 && timestamp + rt > endTimeVal)
				continue;
			String label = fields[2];
			int skip = 0;
			if (fields[4].startsWith("\""))
				++skip;
			if (fields[skip + 5].startsWith("\""))
				++skip;
			int latency = Integer.parseInt(fields[skip + 9]);
			int threads = 0;
			int bytes = Integer.parseInt(fields[skip + 8]);
			boolean error = !Boolean.parseBoolean(fields[skip + 7]);
			JmeterParser.writeFields(writer, "TX-" + label
					+ (error ? "-F" : "-S"), timestamp, threads, error ? '1'
					: '0', latency, rt, bytes);
		}
		writer.flush();

	}

}
