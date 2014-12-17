package com.redhat.chartgeneration.formatter;

import com.redhat.chartgeneration.report.StatGraph;

public interface StatGraphFormatter {
	public String format(StatGraph chart) throws Exception;
}
