package com.redhat.chartgeneration.generator;

import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.formatter.FlotChartFormatter;
import com.redhat.chartgeneration.formatter.GraphFormatter;
import com.redhat.chartgeneration.report.Graph;

public class GraphFactoryImpl implements GraphFactory {

	@Override
	public GraphGenerator createGenerator(ChartConfig<Graph> config) throws Exception {
		GraphConfig cfg = (GraphConfig)config;
		return new GraphGenerator(this, cfg);
	}

	@Override
	public GraphFormatter createFormatter() throws Exception {
		return new FlotChartFormatter();
	}

}
