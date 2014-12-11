package com.redhat.chartgeneration.parser;

import java.io.InputStream;
import java.io.OutputStream;

public class PerftestParserEntry {
	private DataParser parser;

	public void parse(InputStream in, OutputStream out) throws Exception {
		parser.parse(in, out);
	}

	private static PerftestParserEntry app = new PerftestParserEntry();

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Usage:\n PARSER_CLASS");
			return;
		}
		String parserClassName = args[0];
		if (!parserClassName.startsWith("/"))
			parserClassName = PerftestParserEntry.class.getPackage().getName()
					+ "." + parserClassName;
		InputStream in = System.in;
		OutputStream out = System.out;
		try {
			Class<?> parserClass = Class
					.forName(parserClassName);
			app.parser = parserClass.asSubclass(DataParser.class).newInstance();
			app.parse(in, out);
			in.close();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
