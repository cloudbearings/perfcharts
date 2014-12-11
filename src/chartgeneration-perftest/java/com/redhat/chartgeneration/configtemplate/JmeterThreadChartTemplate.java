package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.LineConfigRule;
import com.redhat.chartgeneration.config.LineGraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;

public class JmeterThreadChartTemplate extends BaseChartTemplateWithInterval {
	@Override
	public LineGraphConfig generateGraphConfig() {
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector threadsField = new IndexFieldSelector(2);
		List<LineConfigRule> rules = new ArrayList<LineConfigRule>();
		rules.add(new LineConfigRule("^TX-(.+)-[SF]$", "users (threads)", "VU",
				getLabelField(), timestampField, threadsField, new AverageCalculation(
						getInterval())));
		return createConfig("Concurrent Virtual Users",
				"time", "virtual users (threads)", rules, AxisMode.TIME);

	}
}
