package com.redhat.chartgeneration.generator;

import com.redhat.chartgeneration.report.StatChart;

public interface Generator {
	public StatChart generate(PerfLog log) throws Exception;
}
