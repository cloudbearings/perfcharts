package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphSeriesConfigRule;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;

public class JmeterThreadChartTemplate extends BaseGraphTemplateWithInterval {
	@Override
	public GraphConfig generateChartConfig() {
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector threadsField = new IndexFieldSelector(2);
		List<GraphSeriesConfigRule> rules = new ArrayList<GraphSeriesConfigRule>();
		rules.add(new GraphSeriesConfigRule("^TX-(.+)-[SF]$", "users (threads)", "VU",
				getLabelField(), timestampField, threadsField, new AverageCalculation(
						getInterval())));
		return createConfig("Concurrent Virtual Users",
				"time", "virtual users (threads)", rules, AxisMode.TIME);

	}
}
