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

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class PerfTrendParser implements DataParser {
	private final static Logger LOGGER = Logger.getLogger(PerfTrendParser.class.getName());
	@Override
	public void parse(InputStream in, OutputStream out) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
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
				buildIDPathMap.put(buildId, pathSet = new HashSet<String>());
			pathSet.add(path);
		}
		int xvalue = 0;
		for (int buildId : buildIDPathMap.keySet()) {
			writer.write("\"XTICK\",");
			writer.write(Integer.toString(xvalue));
			writer.write(",#");
			writer.write(Integer.toString(buildId));
			writer.write("\n");
			Set<String> pathSet = buildIDPathMap.get(buildId);
			for (String path : pathSet) {
				JSONTokener tokener = new JSONTokener(new FileInputStream(path));
				JSONObject report = (JSONObject) tokener.nextValue();
				JSONArray charts = report.getJSONArray("charts");
				for (int i = 0; i < charts.length(); ++i) {
					JSONObject chart = (JSONObject) charts.get(i);
					if (!chart.has("chartType")
							|| !"JmeterSummaryChart".equals(chart
									.getString("chartType")))
						continue;
					JSONArray columnLabels = chart.getJSONArray("columnLabels");
					int txIndex = findKey(columnLabels, "Transation");
					int averageRTIndex = findKey(columnLabels, "Average");
					JSONArray rows = chart.getJSONArray("rows");
					for (int j = 0; j < rows.length() - 1; ++j) {
						JSONArray row = (JSONArray) rows.get(j);
						String txName = row.getString(txIndex);
						double txAvgRT = row.getDouble(averageRTIndex);
						// LOGGER.info("read JmeterSummaryChart entry from perf report: "
						// + txName + ", " + txAvgRT);
						writer.write("\"TX-");
						writer.write(txName.replace("\"", "\"\""));
						writer.write("\",");
						writer.write(Integer.toString(xvalue));
						writer.write(",");
						writer.write(Double.toString(txAvgRT));
						writer.write("\n");
					}
					if (rows.length() > 0) {
						JSONArray row = (JSONArray) rows.get(rows.length() - 1);
						//String txName = row.getString(txIndex);
						double txAvgRT = row.getDouble(averageRTIndex);
						writer.write("\"TOTAL\",");
						writer.write(Integer.toString(xvalue));
						writer.write(",");
						writer.write(Double.toString(txAvgRT));
						writer.write("\n");
					}
					break;
				}
			}
			++xvalue;
		}
		writer.flush();
	}

	private static int findKey(JSONArray arr, Object target) {
		for (int i = 0; i < arr.length(); ++i)
			if (arr.get(i) == target || target != null
					&& target.equals(arr.get(i)))
				return i;
		return -1;
	}

}
