package com.redhat.chartgeneration.formatter;

import com.redhat.chartgeneration.report.Chart;

public interface ChartFormatter<T extends Chart> {
	public String format(T chart) throws Exception;
}
