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
import com.redhat.chartgeneration.config.GraphSeriesConfig;
import com.redhat.chartgeneration.config.GraphSeriesConfigRule;
import com.redhat.chartgeneration.model.PerfLog;

public class GraphSeriesConfigBuilder {

	public List<GraphSeriesConfig> build(final GraphSeriesConfigRule rule, PerfLog log) {
		FieldSelector labelField = rule.getLabelField();
		Pattern pattern = Pattern.compile(rule.getLabelPattern());
		String labelFormat = rule.getSeriesLabelFormat();
		List<List<Object>> rows = log.getRows();

		Map<String, Set<String>> map = new HashMap<String, Set<String>>();

		for (List<Object> row : rows) {
			String label = labelField.select(row).toString();
			Matcher matcher = pattern.matcher(label);
			if (matcher.matches()) {
				String lineLabel = matcher.replaceAll(labelFormat);
				Set<String> labels = map.get(lineLabel);
				if (labels == null)
					map.put(lineLabel, labels = new HashSet<String>());
				labels.add(label);
			}
		}

		final List<GraphSeriesConfig> lineConfigs = new ArrayList<GraphSeriesConfig>(
				map.size());
		for (String lineLabels : map.keySet()) {
			Set<String> seriesLabels = map.get(lineLabels);
			lineConfigs.add(new GraphSeriesConfig(lineLabels, rule.getUnit(), rule
					.getLabelField(), rule.getXField(), rule.getYField(), rule
					.getCalculation(), seriesLabels, rule.isShowLines(), rule
					.isShowBars(), rule.isShowUnit()));
		}
		
		return lineConfigs;
	}
}
