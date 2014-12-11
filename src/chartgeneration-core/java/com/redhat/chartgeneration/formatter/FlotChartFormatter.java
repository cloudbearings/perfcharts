package com.redhat.chartgeneration.formatter;

import java.util.List;

import com.redhat.chartgeneration.graph.GraphLine;
import com.redhat.chartgeneration.graph.GraphReport;
import com.redhat.chartgeneration.graph.LineGraph;
import com.redhat.chartgeneration.graph.LineStop;

public class FlotChartFormatter {
	public String format(GraphReport report) {
		StringBuilder sb = new StringBuilder("{");
		if (report.getTitle() != null)
			sb.append("\"title\":\"")
					.append(report.getTitle().replace("\"", "\\\""))
					.append("\",");
		sb.append("\"charts\":[");
		List<LineGraph> charts = report.getGraphs();
		for (LineGraph graph : charts) {
			sb.append(format(graph)).append(",");
		}
		if (!charts.isEmpty())
			sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		return sb.toString();
	}

	public String format(LineGraph graph) {
		StringBuilder sb = new StringBuilder("\n{\"title\":\"")
				.append(graph.getTitle().replace("\"", "\\\""))
				.append("\",\"xLabel\":\"")
				.append(graph.getXLabel().replace("\"", "\\\""))
				.append("\",\"yLabel\":\"")
				.append(graph.getYLabel().replace("\"", "\\\""))
				.append("\",\"xaxisMode\":\"")
				.append(graph.getXaxisMode().toString())
				.append("\",\"series\":[");

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