package com.redhat.chartgeneration.generator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.chart.Chart;
import com.redhat.chartgeneration.chart.Report;
import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.config.ReportConfig;
import com.redhat.chartgeneration.configtemplate.ChartConfigTemplate;
import com.redhat.chartgeneration.generator.ReportGenerator;
import com.redhat.chartgeneration.generator.ReportWritter;

/**
 * Contains the entry point of the generator module.
 * 
 * @author Rayson Zhu
 *
 */
public class GeneratorEntry {
	/**
	 * The entry point
	 * 
	 * @param args
	 *            Usage: &lt;CONFIG_FILE&gt;
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Usage: \n<CONFIG_FILE>");
			return;
		}
		String configFilePath = args[0];

		ReportConfigLoader reportConfigLoader = new ReportConfigLoader();
		ReportConfig config = reportConfigLoader.load(configFilePath);
		
		// get chartConfigs through ConfigTemplates
		List<ChartConfig<Chart>> chartConfigs = new ArrayList<ChartConfig<Chart>>();
		for (ChartConfigTemplate template : config.getConfigTemplate()) {
			@SuppressWarnings("unchecked")
			ChartConfig<Chart> chartConfig = (ChartConfig<Chart>)template.generateChartConfig();
			chartConfigs.add(chartConfig);
		}
		// specify the input stream and output stream
		InputStream in = config.getInputFile() == null
				|| config.getInputFile().isEmpty() ? System.in
				: new FileInputStream(config.getInputFile());
		OutputStream out = config.getOutputFile() == null
				|| config.getOutputFile().isEmpty() ? System.out
				: new FileOutputStream(config.getOutputFile());
		// generate a report through ReportGenerator
		ReportGenerator generator = new ReportGenerator(chartConfigs);
		Report report = generator.generate(in);
		// set the report title
		if (config.getTitle() != null && !config.getTitle().isEmpty())
			report.setTitle(config.getTitle());
		// write to output stream
		ReportWritter reportWritter = new ReportWritter();
		reportWritter.write(report, out);
	}
}
