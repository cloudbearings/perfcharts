package com.redhat.chartgeneration.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.config.Chart2DSeriesConfig;
import com.redhat.chartgeneration.config.Chart2DSeriesConfigRule;
import com.redhat.chartgeneration.model.DataTable;

/**
 * The {@link Chart2DSeriesConfigBuilder} creates a list of
 * {@link Chart2DSeriesConfig}s by given {@link Chart2DSeriesConfigRule}.
 * 
 * @author Rayson Zhu
 *
 */
public class Chart2DSeriesConfigBuilder {
	/**
	 * create a list of {@link Chart2DSeriesConfig}s by given
	 * {@link Chart2DSeriesConfigRule}.
	 * 
	 * @param rule
	 *            a rule
	 * @param dataTable
	 *            a data table
	 * @return a list of {@link Chart2DSeriesConfig}s
	 */
	public List<Chart2DSeriesConfig> build(final Chart2DSeriesConfigRule rule,
			DataTable dataTable) {
		FieldSelector labelField = rule.getLabelField();
		String labelFormat = rule.getSeriesLabelFormat();
		List<List<Object>> rows = dataTable.getRows();
		Pattern pattern = Pattern.compile(rule.getLabelPattern());
		// define the map from series label to its involved row labels.
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		// traverse all data rows, add labels of rows that matches the patterns
		// specified by rules to @map.
		for (List<Object> row : rows) {
			String rowLabel = labelField.select(row).toString();
			Matcher matcher = pattern.matcher(rowLabel);
			if (matcher.matches()) {
				String seriesLabel = matcher.replaceAll(labelFormat);
				Set<String> labels = map.get(seriesLabel);
				if (labels == null)
					map.put(seriesLabel, labels = new HashSet<String>());
				labels.add(rowLabel);
			}
		}
		
		// generate seriesConfigs for each series
		final List<Chart2DSeriesConfig> seriesConfigs = new ArrayList<Chart2DSeriesConfig>(
				map.size());
		for (String seriesLabels : map.keySet()) {
			Set<String> involvedRowLabels = map.get(seriesLabels);
			seriesConfigs.add(new Chart2DSeriesConfig(seriesLabels, rule
					.getUnit(), rule.getLabelField(), rule.getXField(), rule
					.getYField(), rule.getCalculation(), involvedRowLabels,
					rule.isShowLine(), rule.isShowBar(), rule.isShowUnit()));
		}
		return seriesConfigs;
	}
}
