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
import java.util.TimeZone;

public class CPULoadParser implements DataParser {
	public void parse(InputStream in, OutputStream out) throws IOException,
			ParseException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		String lineStr;
		TimeZone utcZone = TimeZone.getTimeZone("UTC");
		SimpleDateFormat timeFormat = new SimpleDateFormat("y-M-d H:m:s");
		timeFormat.setTimeZone(utcZone);
		while ((lineStr = reader.readLine()) != null) {
			String[] extractedLine = lineStr.split(",");
			if (extractedLine.length < 2)
				continue;
			writer.write("CPULOAD,");
			writer.write(Long.toString(timeFormat.parse(extractedLine[0])
					.getTime()));
			String[] cpuLoadStr = extractedLine[1].trim().split(" ");
			writer.write(String.format(",%.2f", Float.parseFloat(cpuLoadStr[0])));
			writer.write(String.format(",%.2f", Float.parseFloat(cpuLoadStr[1])));
			writer.write(String.format(",%.2f", Float.parseFloat(cpuLoadStr[2])));
			writer.write(",");
			writer.write(Integer.toString(Integer.parseInt(cpuLoadStr[3])));
			writer.write("\n");
		}
		in.close();
		writer.flush();
		out.close();
	}
}
