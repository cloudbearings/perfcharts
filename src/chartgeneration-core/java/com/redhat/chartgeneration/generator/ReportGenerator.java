package com.redhat.chartgeneration.generator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.report.StatChart;
import com.redhat.chartgeneration.report.StatReport;

public class ReportGenerator {
	private List<ChartConfig> chartConfigs;
	//private GraphGenerator generator = new GraphGenerator();

	public ReportGenerator() {

	}

	public ReportGenerator(List<ChartConfig> lineGraphConfigs) {
		this.chartConfigs = lineGraphConfigs;
	}

	public StatReport generate(InputStream in)
			throws Exception {
		List<StatChart> charts = new ArrayList<StatChart>();
		StatReport report = new StatReport(null, charts);

		LogReader reader = new LogReader();
		PerfLog log = new PerfLog();
		log.setRows(reader.read(in));

		for (ChartConfig cfg : chartConfigs) {
			charts.add(cfg.createGenerator().generate(log));
		}

		return report;
	}

	public List<ChartConfig> getChartConfigs() {
		return chartConfigs;
	}

	public void setChartConfigs(List<ChartConfig> lineGraphConfigs) {
		this.chartConfigs = lineGraphConfigs;
	}
}
