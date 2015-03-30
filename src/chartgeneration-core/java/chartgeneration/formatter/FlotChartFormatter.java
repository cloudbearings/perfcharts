package chartgeneration.formatter;

import chartgeneration.chart.Chart2D;
import chartgeneration.chart.Chart2DSeries;
import chartgeneration.chart.Point2D;
import chartgeneration.common.Utilities;

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
		StringBuilder sb = new StringBuilder("\n{\"title\":\"")
				.append(graph.getTitle().replace("\"", "\\\""))
				.append("\",\"xLabel\":\"")
				.append(graph.getXLabel().replace("\"", "\\\""))
				.append("\",\"yLabel\":\"")
				.append(graph.getYLabel().replace("\"", "\\\""))
				.append("\",\"xaxisMode\":\"")
				.append(graph.getXaxisMode().toString()).append("\"");
		if (graph.getSubtitle() != null) {
			sb.append(",\"subtitle\":\"")
					.append(graph.getSubtitle().replace("\"", "\\\""))
					.append("\"");
		}
		if (graph.getXTicks() != null) {
			sb.append(",\"xaxisTicks\":").append(graph.getXTicks().format());
		}
		sb.append(",\"series\":[");
		for (Chart2DSeries line : graph.getLines()) {
			sb.append("\n{\"label\":\"")
					.append(line.getLabel().replace("\"", "\\\"")).append("\"");
			if (line.getUnit() != null) {
				sb.append(",\"_unit\":{\"value\":\"")
						.append(line.getUnit().replace("\"", "\\\""))
						.append("\"");
				if (line.isShowUnit())
					sb.append(",\"show\":true");
				sb.append("}");
			}
			if (line.isShowBars()) {
				sb.append(",\"bars\":{\"show\":true}");
			}
			if (!line.isShowLine()) {
				sb.append(",\"lines\":{\"show\":false}");
			}
			sb.append(",\"data\":[");
			for (Point2D stop : line.getStops()) {
				formatStop(sb, stop);
				sb.append(",");
			}
			if (!line.getStops().isEmpty())
				sb.deleteCharAt(sb.length() - 1);
			sb.append("]},");
		}
		if (!graph.getLines().isEmpty())
			sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		return sb.toString();
	}

	private static void formatStop(StringBuilder sb, Point2D stop) {
		Object x = stop.getX();
		sb.append("[").append(Utilities.commonConvertToJsonValue(x));
		sb.append(",").append(stop.getY()).append("]");
	}

}
