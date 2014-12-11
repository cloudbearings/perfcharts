package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.LineConfigRule;
import com.redhat.chartgeneration.config.LineGraphConfig;
import com.redhat.chartgeneration.graphcalc.CountCalculation;

public class JmeterHitsChartTemplate extends BaseChartTemplateWithInterval {

	@Override
	public LineGraphConfig generateGraphConfig() {
		List<LineConfigRule> rules;
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector yField = new IndexFieldSelector(2);
		//FieldSelector xField = new AddTransformSelector(timestampField,yField);
		rules = new ArrayList<LineConfigRule>();
		rules.add(new LineConfigRule("^HIT-.*", "hits", "HITS",
				getLabelField(), timestampField, yField, new CountCalculation(
						getInterval(), 1000.0 / getInterval())));
		return createConfig("Hits over Time", "time",
				"hits", rules, AxisMode.TIME);
	}

}
