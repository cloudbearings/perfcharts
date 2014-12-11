package com.redhat.chartgeneration.generator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.redhat.chartgeneration.config.LineGraphConfig;
import com.redhat.chartgeneration.graph.GraphReport;
import com.redhat.chartgeneration.graph.LineGraph;

public class ReportGenerator {
	private List<LineGraphConfig> lineGraphConfigs;
	private GraphGenerator generator = new GraphGenerator();

	public ReportGenerator() {

	}

	public ReportGenerator(List<LineGraphConfig> lineGraphConfigs) {
		this.lineGraphConfigs = lineGraphConfigs;
	}

	public GraphReport generate(InputStream in)
			throws IOException {
		List<LineGraph> graphs = new ArrayList<LineGraph>();
		GraphReport report = new GraphReport(null, graphs);

		LogReader reader = new LogReader();
		PerfLog log = new PerfLog();
		log.setRows(reader.read(in));

		for (Iterator<LineGraphConfig> it = lineGraphConfigs.iterator(); it
				.hasNext();) {
			LineGraphConfig cfg = it.next();
			generator.setLineGraphConfig(cfg);
			graphs.add(generator.generate(log));
		}

		return report;
	}

	public List<LineGraphConfig> getLineGraphConfigs() {
		return lineGraphConfigs;
	}

	public void setLineGraphConfigs(List<LineGraphConfig> lineGraphConfigs) {
		this.lineGraphConfigs = lineGraphConfigs;
	}
}
