package com.redhat.chartgeneration.generator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.redhat.chartgeneration.report.StatChart;
import com.redhat.chartgeneration.report.StatReport;

public class ReportWritter {

	public ReportWritter() {
	}

	/**
	 * !!! this code is only for test purpose
	 * 
	 * @param report
	 * @throws Exception
	 */
	public void produce(StatReport report, OutputStream out) throws Exception {
		StringBuilder sb = new StringBuilder("{");
		int validChartCount = 0;
		sb.append("\"charts\":[");
		for (StatChart chart : report.getCharts()) {
			String s = chart.format();
			if (s != null) {
				sb.append(s);
				sb.append(",");
				++validChartCount;
			}
		}
		if (validChartCount > 0)
			sb.deleteCharAt(sb.length() - 1);
		sb.append("]}");
		String s = sb.toString();
		writeToStream(s, out);
	}

	private void writeToStream(String s, OutputStream out) {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(out);
			writer.write(s);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
