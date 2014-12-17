package com.redhat.chartgeneration.parser;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.TimeZone;

import com.redhat.chartgeneration.config.AppData;

public class PerftestParserEntry {
	private DataParser parser;

	public void parse(InputStream in, OutputStream out) throws Exception {
		parser.parse(in, out);
	}

	private static PerftestParserEntry app = new PerftestParserEntry();

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Usage:\n PARSER_CLASS [TIME_ZONE]");
			return;
		}
		TimeZone.setDefault(TimeZone.getTimeZone(args.length > 1 && !args[1].isEmpty() ? args[1]
				: "UTC"));
		AppData.getInstance().getLogger().info("The time zone for parsing NMON and CPU load logs is " + TimeZone.getDefault().getDisplayName());
		String parserClassName = args[0];
		if (!parserClassName.startsWith("/"))
			parserClassName = PerftestParserEntry.class.getPackage().getName()
					+ "." + parserClassName;
		else
			parserClassName = parserClassName.substring(1);
		InputStream in = System.in;
		OutputStream out = System.out;

		Class<?> parserClass = Class.forName(parserClassName);
		app.parser = parserClass.asSubclass(DataParser.class).newInstance();
		app.parse(in, out);
		in.close();
		out.close();
	}
}
