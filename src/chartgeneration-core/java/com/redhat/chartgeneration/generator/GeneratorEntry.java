package com.redhat.chartgeneration.generator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.config.ReportConfig;
import com.redhat.chartgeneration.configtemplate.ChartConfigTemplate;
import com.redhat.chartgeneration.generator.ReportGenerator;
import com.redhat.chartgeneration.generator.ReportWritter;
import com.redhat.chartgeneration.report.Chart;
import com.redhat.chartgeneration.report.Report;

public class GeneratorEntry {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Usage: \n<config file>");
			return;
		}
		String configFilePath = args[0];

		ReportConfigLoader templateLoader = new ReportConfigLoader();
		ReportConfig config = templateLoader.load(configFilePath);

		List<ChartConfig<Chart>> chartConfigs = new ArrayList<ChartConfig<Chart>>();
		for (ChartConfigTemplate template : config.getConfigTemplate()) {
			chartConfigs.add((ChartConfig<Chart>)template.generateChartConfig());
		}

		InputStream in = config.getInputFile() == null
				|| config.getInputFile().isEmpty() ? System.in
				: new FileInputStream(config.getInputFile());
		OutputStream out = config.getOutputFile() == null
				|| config.getOutputFile().isEmpty() ? System.out
				: new FileOutputStream(config.getOutputFile());

		ReportGenerator generator = new ReportGenerator(chartConfigs);
		Report report = generator.generate(in);
		if (config.getTitle() != null && !config.getTitle().isEmpty())
			report.setTitle(config.getTitle());

		ReportWritter reportWritter = new ReportWritter();
		reportWritter.produce(report, out);
	}

}
