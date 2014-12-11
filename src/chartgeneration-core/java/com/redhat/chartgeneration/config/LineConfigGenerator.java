package com.redhat.chartgeneration.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.generator.PerfLog;

public class LineConfigGenerator {

	public List<LineConfig> generate(final LineConfigRule rule, PerfLog log) {
		FieldSelector labelField = rule.getLabelField();
		Pattern pattern = Pattern.compile(rule.getLabelPattern());
		String labelFormat = rule.getSeriesLabelFormat();
		List<List<Object>> rows = log.getRows();

		Map<String, Set<String>> map = new HashMap<String, Set<String>>();

		for (Iterator<List<Object>> it = rows.iterator(); it.hasNext();) {
			List<Object> row = it.next();
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

		final List<LineConfig> lineConfigs = new ArrayList<LineConfig>(
				map.size());
		for (String lineLabels : map.keySet()) {
			Set<String> seriesLabels = map.get(lineLabels);
			lineConfigs.add(new LineConfig(lineLabels, rule.getUnit(), rule
					.getLabelField(), rule.getXField(), rule.getYField(), rule
					.getCalculation(), seriesLabels, rule.isShowLines(), rule
					.isShowBars(), rule.isShowUnit()));
		}
		
		return lineConfigs;
	}
}
