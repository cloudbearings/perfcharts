package com.redhat.chartgeneration.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public class JmeterCSVParser implements DataParser {

	@Override
	public void parse(InputStream in, OutputStream out) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		OutputStreamWriter writer = new OutputStreamWriter(out);

		Date startTime = Settings.getInstance().getStartTime();
		Date endTime = Settings.getInstance().getEndTime();
		long startTimeVal = startTime == null ? -1 : startTime.getTime();
		long endTimeVal = endTime == null ? -1 : endTime.getTime();
		
		String line;
		while ((line = reader.readLine()) != null) {
			String[] fields = line.split(",");
			long timestamp = Long.parseLong(fields[0]);
			int rt = Integer.parseInt(fields[1]);
			if (startTimeVal > 0 && timestamp < startTimeVal
					|| endTimeVal > 0 && timestamp + rt > endTimeVal)
				continue;
			String label = fields[2];
			int skip = 0;
			if (fields[4].startsWith("\""))
				++skip;
			if (fields[skip + 5].startsWith("\""))
				++skip;
			int latency =Integer.parseInt(fields[skip + 9]);
			int threads = 0;
			int bytes = Integer.parseInt(fields[skip + 8]);
			boolean error = !Boolean.parseBoolean(fields[skip + 7]);
			JmeterParser.writeFields(writer, "TX-" + label + (error ? "-F" : "-S"),
					timestamp, threads, error ? '1' : '0', latency, rt,
					bytes);
		}
		writer.flush();
		
	}
	

}
