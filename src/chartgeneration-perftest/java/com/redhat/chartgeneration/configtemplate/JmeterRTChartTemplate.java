package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.AddTransformSelector;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphLineConfigRule;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;

public class JmeterRTChartTemplate extends BaseChartTemplateWithInterval {

	@Override
	public GraphConfig generateChartConfig() {
		List<GraphLineConfigRule> rules;
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector rtField = new IndexFieldSelector(5);
		FieldSelector xField = new AddTransformSelector(timestampField, rtField);
		rules = new ArrayList<GraphLineConfigRule>();
		rules.add(new GraphLineConfigRule("^TX-(.+)-S$", "$1", "RT",  getLabelField(),
				xField, rtField, new AverageCalculation(getInterval())));
		return createConfig("Response Times over Time",
				"time", "response time / ms", rules, AxisMode.TIME);
	}

}
