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

public class CPULoadParser implements DataParser {
	public void parse(InputStream in, OutputStream out) throws IOException,
			ParseException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		String lineStr;
		//TimeZone utcZone = TimeZone.getTimeZone("UTC");
		SimpleDateFormat timeFormat = new SimpleDateFormat("y-M-d H:m:s");
		//timeFormat.setTimeZone(utcZone);
		while ((lineStr = reader.readLine()) != null) {
			String[] extractedLine = lineStr.split(",");
			long time = 0;
			if (extractedLine.length < 2)
				continue;
			try {
				time = timeFormat.parse(extractedLine[0]).getTime();
			} catch (Exception ex) {
				System.err.print("[Warning] com.redhat.chartgeneration.parser.CPULoadParser.parse: Ignore line\n" + lineStr);
				continue;
			}
			writer.write("CPULOAD,");
			writer.write(Long.toString(time));
			String[] cpuLoadStr = extractedLine[1].trim().split(" ");
			writer.write(String.format(",%.2f", Float.parseFloat(cpuLoadStr[0])));
			writer.write(String.format(",%.2f", Float.parseFloat(cpuLoadStr[1])));
			writer.write(String.format(",%.2f", Float.parseFloat(cpuLoadStr[2])));
			writer.write(",");
			writer.write(extractedLine.length > 2 ? Integer.toString(Integer
					.parseInt(extractedLine[2])) : "0");
			writer.write("\n");
		}
		writer.flush();
	}
}
