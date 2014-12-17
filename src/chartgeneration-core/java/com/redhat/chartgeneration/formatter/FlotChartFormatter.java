package com.redhat.chartgeneration.formatter;

import com.redhat.chartgeneration.report.GraphLine;
import com.redhat.chartgeneration.report.LineStop;
import com.redhat.chartgeneration.report.StatGraph;

public class FlotChartFormatter implements StatGraphFormatter {
	// public String format(StatReport report) {
	// StringBuilder sb = new StringBuilder("{");
	// // if (report.getTitle() != null)
	// // sb.append("\"title\":\"")
	// // .append(report.getTitle().replace("\"", "\\\""))
	// // .append("\",");
	// sb.append("\"charts\":[");
	// List<LineGraph> charts = report.getGraphs();
	// for (LineGraph graph : charts) {
	// sb.append(format(graph)).append(",");
	// }
	// if (!charts.isEmpty())
	// sb.deleteCharAt(sb.length() - 1);
	// sb.append("]}");
	// return sb.toString();
	// }

	public String format(StatGraph graph) throws Exception {
		StringBuilder sb = new StringBuilder("\n{\"title\":\"")
				.append(graph.getTitle().replace("\"", "\\\""))
				.append("\",\"xLabel\":\"")
				.append(graph.getXLabel().replace("\"", "\\\""))
				.append("\",\"yLabel\":\"")
				.append(graph.getYLabel().replace("\"", "\\\""))
				.append("\",\"xaxisMode\":\"")
				.append(graph.getXaxisMode().toString());
		if (graph.getSubtitle() != null) {
			sb.append("\",\"subtitle\":\"").append(
					graph.getSubtitle().replace("\"", "\\\""));
		}
		sb.append("\",\"series\":[");
		for (GraphLine line : graph.getLines()) {
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
				sb.append(",\"bars\":{\"show\":true,\"align\":\"center\",\"barWidth\":0.8}");
			}
			if (!line.isShowLines()) {
				sb.append(",\"lines\":{\"show\":false}");
			}
			sb.append(",\"data\":[");
			for (LineStop stop : line.getStops()) {
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

	private static void formatStop(StringBuilder sb, LineStop stop) {
		Object x = stop.getX();
		sb.append("[");
		if (Number.class.isAssignableFrom(x.getClass()))
			sb.append(x);
		else
			sb.append("\"").append(x.toString().replace("\"", "\"\""))
					.append("\"");
		sb.append(",").append(stop.getY())/* .append(",").append(stop.getWeight()) */
		.append("]");
	}

}
