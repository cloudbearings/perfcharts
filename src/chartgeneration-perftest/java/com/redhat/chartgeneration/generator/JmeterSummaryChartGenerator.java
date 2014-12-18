package com.redhat.chartgeneration.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.redhat.chartgeneration.common.AddTransformSelector;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.common.Utilities;
import com.redhat.chartgeneration.config.JmeterSummaryChartConfig;
import com.redhat.chartgeneration.report.JmeterSummaryChart;

public class JmeterSummaryChartGenerator implements Generator {

	private JmeterSummaryChartConfig config;

	public JmeterSummaryChartGenerator() {

	}

	public JmeterSummaryChartGenerator(JmeterSummaryChartConfig config) {
		this.config = config;
	}

	@Override
	public JmeterSummaryChart generate(PerfLog log) throws Exception {

		// JmeterSummaryChart chart = new JmeterSummaryChart(config.getTitle(),
		// config.getSubtitle(), columnLabels, series);
		// List<List<Object>> involvedRows = new LinkedList<List<Object>>();
		FieldSelector labelField = new IndexFieldSelector(0);
		final FieldSelector timestampField = new IndexFieldSelector(1);
		final FieldSelector rtField = new IndexFieldSelector(5);
		final FieldSelector xField = new AddTransformSelector(timestampField,
				rtField);
		final FieldSelector errorField = new IndexFieldSelector(3);
		final FieldSelector bytesField = new IndexFieldSelector(6);
		// final FieldSelector timestampField = new IndexFieldSelector(1);
		final Map<String, List<List<Object>>> involvedRows = new HashMap<String, List<List<Object>>>();
		Pattern txPattern = Pattern.compile("^TX-(.+)-[SF]$");

		// long minTime = ((Number) xField.select(rows.get(0))).longValue();
		// long maxTime = ((Number) xField.select(rows.get(rows.size() - 1)))
		// .longValue();

		for (List<Object> row : log.getRows()) {
			Matcher m = txPattern.matcher(labelField.select(row).toString());
			if (m.matches()) {
//				@SuppressWarnings("unchecked")
//				Comparable<Object> startX = (Comparable<Object>) config
//						.getStartX();
//				@SuppressWarnings("unchecked")
//				Comparable<Object> endX = (Comparable<Object>) config.getEndX();
//				Object x = xField.select(row);
//				if (startX != null && startX.compareTo(x) > 0)
//					continue;
//				if (endX != null && endX.compareTo(x) < 0)
//					continue;
				String key = m.replaceAll("$1");
				List<List<Object>> list = involvedRows.get(key);
				if (list == null)
					involvedRows
							.put(key, list = new LinkedList<List<Object>>());
				list.add(row);
				// long ts = ((Number) xField.select(row)).longValue();
				// if (ts < minTimestamp)
				// minTimestamp = ts;
				// if (ts > maxTimestamp)
				// maxTimestamp = ts;
			}
		}

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
			long duration = maxTimestamp - minTimestamp + 1;

			List<Object> tableRow = new ArrayList<Object>();
			tableRow.add(series.getKey());
			long samples = numRTsuccess + numRTfailure;
			tableRow.add(samples);
			double avgRT = 1.0 * sumRT / numRTsuccess;
			tableRow.add(avgRT);
			tableRow.add(minRT);
			tableRow.add(maxRT);
			tableRow.add(Utilities.fastSelect(RTs,
					(int) Math.round((RTs.size() - 1) * 0.9)).doubleValue());
			tableRow.add(Math.sqrt(sumRTSquared / numRTsuccess - avgRT * avgRT));
			tableRow.add(100.0 * numRTfailure / samples);
			tableRow.add(formatThroughput(1.0 * samples / duration));
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
		List<Object> totalRow = new ArrayList<Object>();
		totalRow.add("TOTAL");
		long samplesTotal = numRTsuccessTotal + numRTfailureTotal;
		totalRow.add(samplesTotal);
		double avgRTTotal = 1.0 * sumRTTotal / numRTsuccessTotal;
		totalRow.add(avgRTTotal);
		totalRow.add(minRTTotal);
		totalRow.add(maxRTTotal);
		totalRow.add(Utilities.fastSelect(RTsTotal,
				(int) Math.round((RTsTotal.size() - 1) * 0.9)).doubleValue());
		totalRow.add(Math.sqrt(sumRTSquaredTotal / numRTsuccessTotal
				- avgRTTotal * avgRTTotal));
		totalRow.add(100.0 * numRTfailureTotal / samplesTotal);
		long durationTotal = maxTimestampTotal - minTimestampTotal + 1;
		totalRow.add(formatThroughput(1.0 * samplesTotal / durationTotal));
		totalRow.add(bytesSumTotal / 1.024 / durationTotal);
		totalRow.add(1.0 * bytesSumTotal / numRTsuccessTotal);
		tableRows.add(totalRow);

		JmeterSummaryChart chart = new JmeterSummaryChart(config.getTitle(),
				config.getSubtitle(), columnLabels, tableRows);
		return chart;
	}

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

	public JmeterSummaryChartConfig getConfig() {
		return config;
	}

	public void setConfig(JmeterSummaryChartConfig config) {
		this.config = config;
	}

}
