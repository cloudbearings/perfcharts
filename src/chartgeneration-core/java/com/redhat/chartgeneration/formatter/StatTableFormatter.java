package com.redhat.chartgeneration.formatter;

import com.redhat.chartgeneration.report.StatTable;

public interface StatTableFormatter {
	public String format(StatTable chart) throws Exception;
}
