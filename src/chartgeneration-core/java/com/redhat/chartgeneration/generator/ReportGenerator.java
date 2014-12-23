package com.redhat.chartgeneration.generator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.chart.Chart;
import com.redhat.chartgeneration.chart.Report;
import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.model.DataTable;

/**
 * This object generates a report that contains many charts by a collection of
 * {@link ChartConfig}s.
 * 
 * @author Rayson Zhu
 *
 */
public class ReportGenerator {
	/**
	 * a collection of {@link ChartConfig}s.
	 */
	private List<ChartConfig<Chart>> chartConfigs;

	/**
	 * constructor
	 */
	public ReportGenerator() {

	}

	/**
	 * constructor
	 * 
	 * @param chartConfigs
	 *            a collection of {@link ChartConfig}s.
	 */
	public ReportGenerator(List<ChartConfig<Chart>> chartConfigs) {
		this.chartConfigs = chartConfigs;
	}

	/**
	 * Generates a report that contains many charts from specified input stream.
	 * 
	 * @param in
	 *            a input stream
	 * @return a report
	 * @throws Exception
	 */
	public Report generate(InputStream in) throws Exception {
		List<Chart> charts = new ArrayList<Chart>();
		Report report = new Report(null, charts);

		// load data table
		DateTableLoader loader = new DateTableLoader();
		DataTable dataTable = loader.load(in);

		for (ChartConfig<Chart> cfg : chartConfigs) {
			// for each ChartConfig, create associate factory and generate
			// corresponding chart
			ChartFactory<Chart> factory = cfg.createChartFactory();
			Generator generator = factory.createGenerator(cfg);
			charts.add(generator.generate(dataTable));
		}
		return report;
	}

	/**
	 * Get a collection of {@link ChartConfig}s.
	 * 
	 * @return a collection of {@link ChartConfig}s.
	 */
	public List<ChartConfig<Chart>> getChartConfigs() {
		return chartConfigs;
	}

	/**
	 * Set a collection of {@link ChartConfig}s.
	 * 
	 * @param lineGraphConfigs
	 *            a collection of {@link ChartConfig}s.
	 */
	public void setChartConfigs(List<ChartConfig<Chart>> lineGraphConfigs) {
		this.chartConfigs = lineGraphConfigs;
	}
}
