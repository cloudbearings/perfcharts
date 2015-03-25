package chartgeneration.perftest.generator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chartgeneration.common.AddTransformSelector;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.common.Utilities;
import chartgeneration.generator.Generator;
import chartgeneration.model.DataTable;
import chartgeneration.perftest.chart.JmeterSummaryChart;
import chartgeneration.perftest.config.JmeterSummaryChartConfig;

/**
 * A generator reads data tables and produces {@link JmeterSummaryChart}
 * according to preset configurations.
 * 
 * @author Rayson Zhu
 *
 */
public class JmeterSummaryChartGenerator implements Generator {
	/**
	 * The factory for creating related objects.
	 */
	private JmeterSummaryChartFactory factory;
	/**
	 * the chart configuration
	 */
	private JmeterSummaryChartConfig config;

	/**
	 * constructor
	 */
	public JmeterSummaryChartGenerator() {

	}

	/**
	 * constructor
	 * 
	 * @param factory
	 *            The factory for creating related objects
	 * @param config
	 *            the chart configuration
	 */
	public JmeterSummaryChartGenerator(JmeterSummaryChartFactory factory,
			JmeterSummaryChartConfig config) {
		this.config = config;
		this.factory = factory;
	}

	@Override
	public JmeterSummaryChart generate(DataTable log) throws Exception {
		FieldSelector labelField = new IndexFieldSelector(0);
		final FieldSelector timestampField = new IndexFieldSelector(1);
		final FieldSelector rtField = new IndexFieldSelector(5);
		final FieldSelector xField = new AddTransformSelector(timestampField,
				rtField);
		final FieldSelector errorField = new IndexFieldSelector(3);
		final FieldSelector bytesField = new IndexFieldSelector(6);
		// map table row label to related data rows
		final Map<String, List<List<Object>>> involvedRows = new HashMap<String, List<List<Object>>>();
		Pattern txPattern = Pattern.compile("^TX-(.+)-[SF]$");
		// filter involved rows
		for (List<Object> row : log.getRows()) {
			Matcher m = txPattern.matcher(labelField.select(row).toString());
			if (m.matches()) {
				String key = m.replaceAll("$1");
				List<List<Object>> list = involvedRows.get(key);
				if (list == null)
					involvedRows
							.put(key, list = new LinkedList<List<Object>>());
				list.add(row);
			}
		}
		// generate table column labels
		List<String> columnLabels = new ArrayList<String>();
		columnLabels.add("Transation");
		columnLabels.add("#Samples");
		columnLabels.add("Average");
		columnLabels.add("Min");
		columnLabels.add("Max");
		columnLabels.add("90% Line");
		columnLabels.add("Std. Dev.");
		columnLabels.add("Error%");
		columnLabels.add("Throughput");
		columnLabels.add("KiB/sec");
		columnLabels.add("Avg. Bytes");
		List<List<Object>> tableRows = new ArrayList<List<Object>>();

		// variables for the TOTAL row
		long minTimestampTotal = Long.MAX_VALUE;
		long maxTimestampTotal = Long.MIN_VALUE;
		long sumRTTotal = 0;
		long numRTsuccessTotal = 0;
		long numRTfailureTotal = 0;
		long minRTTotal = Integer.MAX_VALUE;
		long maxRTTotal = Integer.MIN_VALUE;
		double sumRTSquaredTotal = 0;
		long bytesSumTotal = 0;
		List<Long> RTsTotal = new LinkedList<Long>();

		// for each series (table row), do calculations
		for (Map.Entry<String, List<List<Object>>> series : involvedRows
				.entrySet()) {
			List<List<Object>> rows = series.getValue();
			if (rows.isEmpty())
				continue;

			long minTimestamp = Long.MAX_VALUE;
			long maxTimestamp = Long.MIN_VALUE;
			long sumRT = 0;
			long numRTsuccess = 0;
			long numRTfailure = 0;
			long minRT = Integer.MAX_VALUE;
			long maxRT = Integer.MIN_VALUE;
			double sumRTSquared = 0;
			long bytesSum = 0;

			List<Long> RTs = new LinkedList<Long>();

			for (List<Object> row : rows) {
				long rt = ((Number) rtField.select(row)).longValue();
				long ts = ((Number) xField.select(row)).longValue();
				if (ts < minTimestamp)
					minTimestamp = ts;
				if (ts > maxTimestamp)
					maxTimestamp = ts;
				if (((Number) errorField.select(row)).intValue() == 0) {
					RTs.add(rt);
					numRTsuccess++;
					sumRT += rt;
					if (rt < minRT)
						minRT = rt;
					if (rt > maxRT)
						maxRT = rt;
					sumRTSquared += rt * rt;
					bytesSum += ((Number) bytesField.select(row)).longValue();
				} else {
					numRTfailure++;
				}
			}
			// if (RTs.isEmpty()) {
			// continue;
			// }
			long duration = maxTimestamp - minTimestamp + 1;

			List<Object> tableRow = new ArrayList<Object>();
			tableRow.add(series.getKey());
			long samples = numRTsuccess + numRTfailure;
			tableRow.add(samples);

			if (!RTs.isEmpty()) {
				double avgRT = 1.0 * sumRT / numRTsuccess;
				tableRow.add(avgRT);
				tableRow.add(minRT);
				tableRow.add(maxRT);
				tableRow.add(Utilities.fastSelect(RTs,
						(int) Math.round((RTs.size() - 1) * 0.9)).doubleValue());
				// std. dev. = sqrt(average of (x^2) - (average of x)^2)
				tableRow.add(Math.sqrt(sumRTSquared / numRTsuccess - avgRT
						* avgRT));
			} else {
				tableRow.add(Double.NaN);
				tableRow.add(Double.NaN);
				tableRow.add(Double.NaN);
				tableRow.add(Double.NaN);
				tableRow.add(Double.NaN);
			}
			tableRow.add(100.0 * numRTfailure / samples);
			tableRow.add(formatThroughput(1.0 * numRTsuccess / duration));
			tableRow.add(bytesSum / 1.024 / duration);
			tableRow.add(1.0 * bytesSum / numRTsuccess);
			tableRows.add(tableRow);

			if (minTimestampTotal > minTimestamp)
				minTimestampTotal = minTimestamp;
			if (maxTimestampTotal < maxTimestamp)
				maxTimestampTotal = maxTimestamp;
			if (minRTTotal > minRT)
				minRTTotal = minRT;
			if (maxRTTotal < maxRT)
				maxRTTotal = maxRT;

			numRTsuccessTotal += numRTsuccess;
			numRTfailureTotal += numRTfailure;
			sumRTTotal += sumRT;
			sumRTSquaredTotal += sumRTSquared;
			bytesSumTotal += bytesSum;
			RTsTotal.addAll(RTs);
		}

		// sort rows by transaction name
		tableRows.sort(new Comparator<List<Object>>() {
			@Override
			public int compare(List<Object> o1, List<Object> o2) {
				if (o1.isEmpty() || o2.isEmpty())
					return 0;
				return o1.get(0).toString().compareTo(o2.get(0).toString());
			}
		});

		// generate the TOTAL row
		List<Object> totalRow = new ArrayList<Object>();
		totalRow.add("TOTAL");
		long samplesTotal = numRTsuccessTotal + numRTfailureTotal;
		totalRow.add(samplesTotal);
		double avgRTTotal = 1.0 * sumRTTotal / numRTsuccessTotal;
		totalRow.add(avgRTTotal);
		totalRow.add(minRTTotal);
		totalRow.add(maxRTTotal);
		totalRow.add(RTsTotal.isEmpty() ? Double.NaN : Utilities.fastSelect(
				RTsTotal, (int) Math.round((RTsTotal.size() - 1) * 0.9))
				.doubleValue());
		totalRow.add(Math.sqrt(sumRTSquaredTotal / numRTsuccessTotal
				- avgRTTotal * avgRTTotal));
		totalRow.add(100.0 * numRTfailureTotal / samplesTotal);
		long durationTotal = maxTimestampTotal - minTimestampTotal + 1;
		totalRow.add(formatThroughput(1.0 * samplesTotal / durationTotal));
		totalRow.add(bytesSumTotal / 1.024 / durationTotal);
		totalRow.add(1.0 * bytesSumTotal / numRTsuccessTotal);
		tableRows.add(totalRow);
		// complete the chart
		JmeterSummaryChart chart = new JmeterSummaryChart(config.getTitle(),
				config.getSubtitle(), columnLabels, tableRows);
		chart.setFormatter(factory.createFormatter());
		return chart;
	}

	/**
	 * format the throughput data
	 * 
	 * @param throughput
	 *            throughput
	 * @return a string
	 */
	private String formatThroughput(double throughput) {
		if (throughput >= 1.0)
			return String.format("%.2f/ms", throughput);
		throughput *= 1000;
		if (throughput >= 1.0)
			return String.format("%.2f/s", throughput);
		throughput *= 60;
		if (throughput >= 1.0)
			return String.format("%.2f/min", throughput);
		throughput *= 60;
		return String.format("%.2f/h", throughput);
	}

	/**
	 * Get the configuration of this chart.
	 * 
	 * @return the configuration
	 */
	public JmeterSummaryChartConfig getConfig() {
		return config;
	}

	/**
	 * Set the configuration of this chart.
	 * 
	 * @param config
	 *            the configuration
	 */
	public void setConfig(JmeterSummaryChartConfig config) {
		this.config = config;
	}

}
