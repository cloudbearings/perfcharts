package com.redhat.chartgeneration.generator;

import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.formatter.GraphFormatter;
import com.redhat.chartgeneration.report.Graph;

public interface GraphFactory extends ChartFactory<Graph> {
	@Override
	public GraphGenerator createGenerator(ChartConfig<Graph> config) throws Exception;
	@Override
	public GraphFormatter createFormatter() throws Exception;
}
