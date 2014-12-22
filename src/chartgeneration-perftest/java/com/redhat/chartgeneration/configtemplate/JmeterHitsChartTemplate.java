package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphSeriesConfigRule;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.graphcalc.CountCalculation;

public class JmeterHitsChartTemplate extends BaseGraphTemplateWithInterval {

	@Override
	public GraphConfig generateChartConfig() {
		int interval = getInterval();
		if (interval < 1)
			interval = 10000;
		List<GraphSeriesConfigRule> rules;
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector yField = new IndexFieldSelector(2);
		//FieldSelector xField = new AddTransformSelector(timestampField,yField);
		rules = new ArrayList<GraphSeriesConfigRule>();
		rules.add(new GraphSeriesConfigRule("^HIT-.*", "hits", "HITS",
				getLabelField(), timestampField, yField, new CountCalculation(
						interval, 1000.0 / interval)));
		return createConfig("Hits over Time", "time",
				"hits", rules, AxisMode.TIME);
	}

}
