package com.redhat.chartgeneration.generator;

import com.redhat.chartgeneration.model.PerfLog;
import com.redhat.chartgeneration.report.Chart;

public interface Generator {
	public Chart generate(PerfLog log) throws Exception;
}
