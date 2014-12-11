package com.redhat.chartgeneration.report;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.redhat.chartgeneration.formatter.FlotChartFormatter;
import com.redhat.chartgeneration.graph.GraphReport;

public class ReportWritter {

	private FlotChartFormatter formatter;

	public ReportWritter() {
	}

	public ReportWritter(FlotChartFormatter formatter) {
		this.formatter = formatter;
	}

	/**
	 * !!! this code is only for test purpose
	 * 
	 * @param report
	 */
	public void produce(GraphReport report, OutputStream out) {
		String s = formatter.format(report);
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

	public FlotChartFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(FlotChartFormatter formatter) {
		this.formatter = formatter;
	}
}
