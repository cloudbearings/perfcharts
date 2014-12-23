package com.redhat.chartgeneration.formatter;

import com.redhat.chartgeneration.report.Graph;

public interface GraphFormatter extends ChartFormatter<Graph> {
	public String format(Graph chart) throws Exception;
}
