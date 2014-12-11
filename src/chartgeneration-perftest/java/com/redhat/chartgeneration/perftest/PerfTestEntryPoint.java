package com.redhat.chartgeneration.perftest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PerfTestEntryPoint {
	private final static Map<String, String> parserProgramsMap = new HashMap<String, String>();
	private static String generatorProgram = "chartgeneration.jar";
	private static String defaultPerfConfig = "../shared/jmeter.conf";
	private static String defaultResConfig = "../shared/res.conf";
	private final static String DEFAULT_OUTPUT_DIR_NAME = "output";

	public static void main(String[] args) {
		generatorProgram = System.getProperty("gp");
		parserProgramsMap.put(".jtl", System.getProperty("jtlp"));
		parserProgramsMap.put(".load", System.getProperty("loadp"));
		parserProgramsMap.put(".nmon", System.getProperty("nmonp"));
		defaultPerfConfig = System.getProperty("pc");
		defaultResConfig = System.getProperty("rc");

		if (args.length < 1) {

			System.err.println("Usage: <input_directory> [output_directory]");
			return;
		}

		File inputDirectory = new File(args[0]);
		File outputDirectory = new File(args.length < 2 ? args[0]
				+ File.separator + DEFAULT_OUTPUT_DIR_NAME : args[1]);
		outputDirectory.mkdirs();

		Pattern loadFileNamePattern = Pattern.compile("([A-Za-z\\-]+).+");
		SimpleDateFormat fmt = new SimpleDateFormat("yyMMdd_hhmm");
		fmt.setTimeZone(TimeZone.getTimeZone("UTC"));

		Map<String, List<String>> jobFilesMap = new HashMap<String, List<String>>();
		for (String path : inputDirectory.list()) {
			String fullPath = inputDirectory.getAbsolutePath() + File.separator
					+ path;
			String jobName;
			if (path.endsWith(".jtl")) {
				jobName = "Performance";
			} else if (path.endsWith(".load") || path.endsWith(".nmon")) {
				Matcher m = loadFileNamePattern.matcher(path);
				if (!m.matches()) {
					System.err
							.println("Warning: The format of CPU Load file name \""
									+ path + "\" is incorrect.");
				}
				// for (int i = 0; i <= m.groupCount(); i++) {
				// System.out.println(m.group(i));
				// }
				String hostname = m.group(1);
				// long time = 0;
				// try {
				// time = fmt.parse(m.group(2)).getTime();
				// } catch (ParseException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				jobName = "Resource-" + hostname;
			} else {
				continue;
			}
			List<String> files = jobFilesMap.get(jobName);
			if (files == null)
				jobFilesMap.put(jobName, files = new LinkedList<String>());
			files.add(fullPath);
		}

		try {
			for (String jobName : jobFilesMap.keySet()) {
				String csvFileName = outputDirectory.getAbsolutePath()
						+ File.separator + jobName + ".csv";
				File csvFile = new File(csvFileName);
				String jsonFileName = outputDirectory.getAbsolutePath()
						+ File.separator + jobName + ".json";
				File jsonFile = new File(jsonFileName);
				csvFile.delete();
				// BufferedWriter writer = new BufferedWriter(new
				// OutputStreamWriter(new FileOutputStream(csvFile)));
				for (String inputFileName : jobFilesMap.get(jobName)) {
					String ext = inputFileName.substring(inputFileName
							.lastIndexOf('.'));
					File inputFile = new File(inputFileName);
					ProcessBuilder parserBuilder = new ProcessBuilder("java",
							"-jar", parserProgramsMap.get(ext));
					parserBuilder.redirectInput(inputFile);
					parserBuilder.redirectOutput(Redirect.appendTo(csvFile));
					Process parser = parserBuilder.start();
					pipeStream(parser.getErrorStream(), System.err);
					parser.waitFor();
				}
				ProcessBuilder generatorBuilder = new ProcessBuilder("java",
						"-jar", generatorProgram,
						jobName.startsWith("P") ? defaultPerfConfig
								: defaultResConfig);
				generatorBuilder.redirectInput(csvFile);
				generatorBuilder.redirectOutput(jsonFile);
				Process generator = generatorBuilder.start();
				pipeStream(generator.getErrorStream(), System.err);
				generator.waitFor();
			}
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void pipeStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024000];
		int len;
		while ((len = in.read(buffer, 0, buffer.length)) > 0) {
			out.write(buffer, 0, len);
		}
		out.flush();
	}

}
