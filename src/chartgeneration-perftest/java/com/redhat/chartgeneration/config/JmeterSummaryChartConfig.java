package com.redhat.chartgeneration.config;

import com.redhat.chartgeneration.generator.Generator;
import com.redhat.chartgeneration.generator.JmeterSummaryChartGenerator;

public class JmeterSummaryChartConfig extends StatChartConfig {

	@Override
	public Generator createGenerator() throws Exception {
		return new JmeterSummaryChartGenerator(this);
	}

}
