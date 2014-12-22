package com.redhat.chartgeneration.generator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.model.PerfLog;
import com.redhat.chartgeneration.report.Chart;
import com.redhat.chartgeneration.report.Report;

public class ReportGenerator {
	private List<ChartConfig<Chart>> chartConfigs;
	//private GraphGenerator generator = new GraphGenerator();

	public ReportGenerator() {

	}

	public ReportGenerator(List<ChartConfig<Chart>> lineGraphConfigs) {
		this.chartConfigs = lineGraphConfigs;
	}

	public Report generate(InputStream in)
			throws Exception {
		List<Chart> charts = new ArrayList<Chart>();
		Report report = new Report(null, charts);

		LogReader reader = new LogReader();
		PerfLog log = new PerfLog();
		log.setRows(reader.read(in));

		for (ChartConfig<Chart> cfg : chartConfigs) {
			ChartFactory<Chart> factory = cfg.createChartFactory();
			Generator generator = factory.createGenerator(cfg);
			charts.add(generator.generate(log));
		}

		return report;
	}

	public List<ChartConfig<Chart>> getChartConfigs() {
		return chartConfigs;
	}

	public void setChartConfigs(List<ChartConfig<Chart>> lineGraphConfigs) {
		this.chartConfigs = lineGraphConfigs;
	}
}
