package com.redhat.chartgeneration.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class NMONParser implements DataParser {
	public void parse(InputStream in, OutputStream out) throws IOException,
			ParseException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String lineStr;
		List<String[]> lines = new LinkedList<String[]>();
		Map<Integer, Long> timeTable = new HashMap<Integer, Long>(16000);
		Map<String, String> metaInfo = new HashMap<String, String>(30);
		// TimeZone utcZone = TimeZone.getTimeZone("UTC");
		SimpleDateFormat timeFormat = new SimpleDateFormat("H:m:s d-MMM-y");
		// timeFormat.setTimeZone(utcZone);

		Map<String, Integer> diskColumnMap = new HashMap<String, Integer>(4);
		Pattern diskPattern = Pattern.compile("^[hsv]d[a-z]$");

		Date startTime = Settings.getInstance().getStartTime();
		Date endTime = Settings.getInstance().getEndTime();

		while ((lineStr = reader.readLine()) != null) {
			String[] extractedLine = lineStr.split(",");
			if (extractedLine.length == 0)
				continue;
			lines.add(extractedLine);
			if (extractedLine[0].equals("ZZZZ") && extractedLine.length >= 4) {
				int tsLabelValue = Integer.parseInt(extractedLine[1]
						.substring(1));
				Date date = timeFormat.parse(extractedLine[2] + " "
						+ extractedLine[3]);
				if (startTime != null && date.before(startTime)
						|| endTime != null && date.after(endTime))
					continue;
				timeTable.put(tsLabelValue, date.getTime());
			} else if (extractedLine[0].startsWith("DISK")
					&& extractedLine.length > 2
					&& !extractedLine[1].startsWith("T")) {
				for (int i = 2; i < extractedLine.length; ++i) {
					if (diskPattern.matcher(extractedLine[i]).matches()) {
						// diskColumnMap.putIfAbsent(extractedLine[i], i);
						diskColumnMap.put(extractedLine[i], i);
					}
				}
			} else if (extractedLine[0].equals("AAA")
					&& extractedLine.length >= 3) {
				metaInfo.put(extractedLine[1], extractedLine[2]);
			}

		}
		in.close();

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		for (Iterator<String[]> it = lines.iterator(); it.hasNext();) {
			String[] line = it.next();
			if (!line[1].startsWith("T"))
				continue;
			if (line[0].equals("CPU_ALL"))
				parseCPUUtilization(writer, line, timeTable);
			else if (line[0].equals("MEM"))
				parseMemoryUtilization(writer, line, timeTable);
			else if (line[0].equals("NET"))
				parseNetworkThroughput(writer, line, timeTable);
			else if (line[0].equals("DISKBUSY"))
				parseDiskBusy(writer, line, timeTable, diskColumnMap);
			else if (line[0].equals("DISKREAD"))
				parseDiskRead(writer, line, timeTable, diskColumnMap);
			else if (line[0].equals("DISKWRITE"))
				parseDiskWrite(writer, line, timeTable, diskColumnMap);
		}
		writer.flush();
	}

	private static void parseCPUUtilization(BufferedWriter writer,
			String[] line, Map<Integer, Long> timeTable) throws IOException {
		Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
		if (ts == null)
			return;
		writer.write("CPU");
		writer.write(",");
		writer.write(ts.toString());
		writer.write(",");
		writer.write(Float.toString(Float.parseFloat(line[2])));
		writer.write(",");
		writer.write(Float.toString(Float.parseFloat(line[3])));
		writer.write(",");
		writer.write(Float.toString(Float.parseFloat(line[4])));
		writer.write(",");
		writer.write(String.format("%.1f", 100.0f - Float.parseFloat(line[5])));
		writer.write(",");
		writer.write(Integer.toString(Integer.parseInt(line[7])));
		writer.write("\n");
	}

	private static void parseMemoryUtilization(BufferedWriter writer,
			String[] line, Map<Integer, Long> timeTable) throws IOException {
		Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
		if (ts == null)
			return;
		writer.write("MEM");
		writer.write(",");
		writer.write(ts.toString());
		writer.write(",");
		writer.write(Float.toString(Float.parseFloat(line[2])));
		writer.write(",");
		writer.write(Float.toString(Float.parseFloat(line[6])));
		writer.write(",");
		writer.write(Float.toString(Float.parseFloat(line[11])));
		writer.write(",");
		writer.write(Float.toString(Float.parseFloat(line[14])));
		writer.write("\n");
	}

	private static void parseNetworkThroughput(BufferedWriter writer,
			String[] line, Map<Integer, Long> timeTable) throws IOException {
		Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
		if (ts == null)
			return;
		writer.write("NET");
		writer.write(",");
		writer.write(ts.toString());

		int numberOfNetworkAdapters = (line.length - 2) >> 1;
		float read = 0;
		float write = 0;
		for (int i = 0; i < numberOfNetworkAdapters; ++i) {
			read += Float.parseFloat(line[2 + i]);
			write += Float.parseFloat(line[2 + numberOfNetworkAdapters + i]);
		}
		writer.write(",");
		writer.write(Float.toString(read));
		writer.write(",");
		writer.write(Float.toString(write));
		writer.write("\n");
	}

	private static void parseDiskBusy(final BufferedWriter writer,
			final String[] line, final Map<Integer, Long> timeTable,
			final Map<String, Integer> diskColumnMap) throws IOException {
		Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
		if (ts == null)
			return;
		final String tsStr = ts.toString();
		for (String disk : diskColumnMap.keySet()) {
			int index = diskColumnMap.get(disk).intValue();
			writer.write("DISKBUSY-");
			writer.write(disk);
			writer.write(",");
			writer.write(tsStr);
			writer.write(",");
			writer.write(Float.toString(Float.parseFloat(line[index])));
			writer.write("\n");
		}
	}

	private static void parseDiskRead(final BufferedWriter writer,
			final String[] line, final Map<Integer, Long> timeTable,
			final Map<String, Integer> diskColumnMap) throws IOException {
		Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
		if (ts == null)
			return;
		final String tsStr = ts.toString();
		for (String disk : diskColumnMap.keySet()) {
			int index = diskColumnMap.get(disk).intValue();
			writer.write("DISKREAD-");
			writer.write(disk);
			writer.write(",");
			writer.write(tsStr);
			writer.write(",");
			writer.write(Float.toString(Float.parseFloat(line[index])));
			writer.write("\n");
		}
	}

	private static void parseDiskWrite(final BufferedWriter writer,
			final String[] line, final Map<Integer, Long> timeTable,
			final Map<String, Integer> diskColumnMap) throws IOException {
		Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
		if (ts == null)
			return;
		final String tsStr = ts.toString();
		for (String disk : diskColumnMap.keySet()) {
			int index = diskColumnMap.get(disk).intValue();
			writer.write("DISKWRITE-");
			writer.write(disk);
			writer.write(",");
			writer.write(tsStr);
			writer.write(",");
			writer.write(Float.toString(Float.parseFloat(line[index])));
			writer.write("\n");
		}
	}
}
