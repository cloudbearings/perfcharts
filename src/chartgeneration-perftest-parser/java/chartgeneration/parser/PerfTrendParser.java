package chartgeneration.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import chartgeneration.parser.perfsummary.PerfSummaryData;
import chartgeneration.parser.perfsummary.PerfSummaryItem;
import chartgeneration.parser.perfsummary.PerfSummarySupport;

public class PerfTrendParser implements DataParser {
	private final static Logger LOGGER = Logger.getLogger(PerfTrendParser.class
			.getName());

	@Override
	public void parse(InputStream in, OutputStream out) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
		try {

			String line;
			Map<Integer, Set<String>> buildIDPathMap = new TreeMap<Integer, Set<String>>();
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length < 2) {
					LOGGER.warning("Ignore line '" + line + "'.");
					continue;
				}
				int buildId = Integer.parseInt(parts[0]);
				String path = parts[1];
				Set<String> pathSet = buildIDPathMap.get(buildId);
				if (pathSet == null)
					buildIDPathMap
							.put(buildId, pathSet = new HashSet<String>());
				pathSet.add(path);
			}
			int xvalue = 0;
			for (int buildId : buildIDPathMap.keySet()) {
				csvPrinter.printRecord("XTICK", xvalue, buildId);
				Set<String> pathSet = buildIDPathMap.get(buildId);
				for (String path : pathSet) {
					try {
						JSONTokener tokener = new JSONTokener(
								new FileInputStream(path));
						JSONObject report = new JSONObject(tokener);
						JSONArray charts = report.getJSONArray("charts");

						JSONObject chart = PerfSummarySupport
								.findPerfSummaryChart2(charts);
						PerfSummaryData data = null;
						if (chart != null) {
							data = PerfSummarySupport
									.parsePerfSummaryTable2(chart);
						} else {
							chart = PerfSummarySupport
									.findPerfSummaryChart1(charts);
							if (chart != null)
								data = PerfSummarySupport
										.parsePerfSummaryTable1(chart);
						}
						if (data == null) {
							LOGGER.severe("No valid summary chart found from target build.");
							Runtime.getRuntime().exit(1);
						}

						for (String txName : data.getItems().keySet()) {
							PerfSummaryItem item = data.getItems().get(txName);
							double avg = item.getAverage();
							if (Double.isNaN(avg) || Double.isInfinite(avg)) {
								LOGGER.warning("Skip invaild Response Time value (NaN) for transaction \""
										+ txName
										+ "\" (build id=#"
										+ buildId
										+ ").");
								continue;
							}
							csvPrinter.printRecord("TX-" + txName, xvalue, avg);
						}
						double avgTotal = data.getTotal().getAverage();
						if (Double.isNaN(avgTotal)
								|| Double.isInfinite(avgTotal)) {
							LOGGER.warning("Skip invaild Response Time value (NaN) for TOTAL (build id=#"
									+ buildId + ").");
							continue;
						}
						csvPrinter.printRecord("TOTAL", xvalue, avgTotal);
					} catch (JSONException ex) {
						LOGGER.warning("Parsing Error, skip build \"" + path
								+ "\":\n" + ex.toString());
					}
				}
				++xvalue;
			}
			csvPrinter.flush();
		} finally {
			csvPrinter.close();
		}
	}

}
