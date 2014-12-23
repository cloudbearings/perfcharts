package com.redhat.chartgeneration.perftest.formatter;

import java.util.List;

import com.redhat.chartgeneration.common.Utilities;
import com.redhat.chartgeneration.perftest.chart.JmeterSummaryChart;

public class JmeterSummaryChartFormatterImpl implements
		JmeterSummaryChartFormatter {
	public String format(JmeterSummaryChart chart) throws Exception {
		StringBuffer sb = new StringBuffer(
				"{\"chartType\":\"JmeterSummaryChart\"");
		if (chart.getTitle() != null)
			sb.append(",\"title\":\"")
					.append(chart.getTitle().replace("\"", "\\\""))
					.append("\"");
		if (chart.getSubtitle() != null)
			sb.append(",\"subtitle\":\"")
					.append(chart.getSubtitle().replace("\"", "\\\""))
					.append("\"");
		if (chart.getColumnLabels() != null) {
			sb.append(",\"columnLabels\":[");
			for (String columnLabel : chart.getColumnLabels()) {
				sb.append("\"").append(columnLabel.replace("\"", "\\\""))
						.append("\",");
			}
			if (!chart.getColumnLabels().isEmpty())
				sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
		}
		if (chart.getSeries() != null) {
			sb.append(",\"rows\":[");
			for (List<Object> tableRow : chart.getSeries()) {
				sb.append("[");
				for (Object tableCell : tableRow) {
					sb.append(Utilities.commonConvertToJsonValue(tableCell))
							.append(",");
				}
				if (!tableRow.isEmpty())
					sb.deleteCharAt(sb.length() - 1);
				sb.append("],");
			}
			if (!chart.getSeries().isEmpty())
				sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}
}
