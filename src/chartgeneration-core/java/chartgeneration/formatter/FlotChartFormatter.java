package chartgeneration.formatter;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import chartgeneration.chart.Chart2D;
import chartgeneration.chart.Chart2DSeries;
import chartgeneration.chart.Point2D;
import chartgeneration.common.Utilities;
import chartgeneration.config.AxisMode;

/**
 * Provides the support for formatting a {@link Chart2D} to JSON string that
 * specially optimized for JQuery Flot plotting library.
 * 
 * @see {@linkplain http://www.flotcharts.org/}
 * @author Rayson Zhu
 *
 */
public class FlotChartFormatter implements Chart2DFormatter {
	public String format(Chart2D graph) throws Exception {
		JSONObject chartJson = new JSONObject();
		chartJson.put("key", graph.getKey());
		chartJson.put("title", graph.getTitle());
		chartJson.put("subtitle", graph.getSubtitle());
		chartJson.put("xLabel", graph.getXLabel());
		chartJson.put("yLabel", graph.getYLabel());
		chartJson.put("xaxisMode", graph.getXaxisMode().toString());
		if (graph.getXTicks() != null)
			chartJson.put("xaxisTicks", new JSONArray(new JSONTokener(graph
					.getXTicks().format())));
		List<Chart2DSeries> series = graph.getLines();
		if (series != null) {
			Map<String, Integer> xAxisString2IntegerMap = graph
					.getBarChartStringIDMap();
			// Map<Integer, String> xAxisInteger2StringMap = null;
			// if (graph.getXaxisMode() == AxisMode.BAR_STRING) {
			// xAxisString2IntegerMap = new HashMap<String, Integer>();
			// //xAxisInteger2StringMap = new HashMap<Integer, String>();
			// }
			JSONArray seriesArrJson = new JSONArray();
			for (Chart2DSeries s : series) {
				JSONObject seriesJson = new JSONObject();
				seriesJson.put("label", s.getLabel());
				if (s.getUnit() != null) {
					JSONObject unitJson = new JSONObject();
					unitJson.put("show", s.isShowUnit());
					unitJson.put("value", s.getUnit());
					seriesJson.put("_unit", unitJson);
				}
				if (s.isShowBars())
					seriesJson.put("bars", new JSONObject().put("show", true));
				if (!s.isShowLine())
					seriesJson
							.put("lines", new JSONObject().put("show", false));
				JSONArray dataJson = new JSONArray();
				for (Point2D stop : s.getStops()) {
					Object x = stop.getX();
					if (Double.isNaN(stop.getY())
							|| Double.isInfinite(stop.getY())) {
						System.err.println(x + "," + stop.getY());
						continue;
					}
					if (graph.getXaxisMode() == AxisMode.BAR_STRING
							&& xAxisString2IntegerMap != null) {
						Integer _x = xAxisString2IntegerMap.get(x.toString());
						if (_x != null) {
							x = _x;
						} else {
							// x = -1;
							continue;
						}
					}
					dataJson.put(new JSONArray().put(x).put(stop.getY()));
				}
				seriesJson.put("data", dataJson);
				seriesArrJson.put(seriesJson);
			}
			chartJson.put("series", seriesArrJson);
			if (xAxisString2IntegerMap != null) {
				JSONObject stringMapJson = new JSONObject();
				for (String key : xAxisString2IntegerMap.keySet()) {
					Integer val = xAxisString2IntegerMap.get(key);
					stringMapJson.put(val.toString(), key);
				}
				chartJson.put("stringMap", stringMapJson);
				chartJson.put("stringMapSize", stringMapJson.length());
			}
		}

		// StringBuilder sb = new StringBuilder("\n{\"title\":\"")
		// .append(graph.getTitle().replace("\"", "\\\""))
		// .append("\",\"xLabel\":\"")
		// .append(graph.getXLabel().replace("\"", "\\\""))
		// .append("\",\"yLabel\":\"")
		// .append(graph.getYLabel().replace("\"", "\\\""))
		// .append("\",\"xaxisMode\":\"")
		// .append(graph.getXaxisMode().toString()).append("\"");
		// if (graph.getSubtitle() != null) {
		// sb.append(",\"subtitle\":\"")
		// .append(graph.getSubtitle().replace("\"", "\\\""))
		// .append("\"");
		// }
		// if (graph.getKey() != null) {
		// sb.append(",\"key\":\"")
		// .append(graph.getKey().replace("\"", "\\\"")).append("\"");
		// }
		// if (graph.getXTicks() != null) {
		// sb.append(",\"xaxisTicks\":").append(graph.getXTicks().format());
		// }
		// sb.append(",\"series\":[");
		// for (Chart2DSeries line : graph.getLines()) {
		// sb.append("\n{\"label\":\"")
		// .append(line.getLabel().replace("\"", "\\\"")).append("\"");
		// if (line.getUnit() != null) {
		// sb.append(",\"_unit\":{\"value\":\"")
		// .append(line.getUnit().replace("\"", "\\\""))
		// .append("\"");
		// if (line.isShowUnit())
		// sb.append(",\"show\":true");
		// sb.append("}");
		// }
		// if (line.isShowBars()) {
		// sb.append(",\"bars\":{\"show\":true}");
		// }
		// if (!line.isShowLine()) {
		// sb.append(",\"lines\":{\"show\":false}");
		// }
		// sb.append(",\"data\":[");
		// for (Point2D stop : line.getStops()) {
		// formatStop(sb, stop);
		// sb.append(",");
		// }
		// if (!line.getStops().isEmpty())
		// sb.deleteCharAt(sb.length() - 1);
		// sb.append("]},");
		// }
		// if (!graph.getLines().isEmpty())
		// sb.deleteCharAt(sb.length() - 1);
		// sb.append("]}");
		return chartJson.toString();
	}

	private static String formatStop(Point2D stop) {
		StringBuilder sb = new StringBuilder();
		Object x = stop.getX();
		sb.append("[").append(Utilities.commonConvertToJsonValue(x));
		sb.append(",").append(stop.getY()).append("]");
		return sb.toString();
	}
}
