package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.ConstantSelector;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphSeriesConfigRule;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;

public class JmeterAverageRTOverallChartTemplate extends BaseGraphTemplate {

	@Override
	public GraphConfig generateChartConfig() {
		List<GraphSeriesConfigRule> rules;
		FieldSelector rtField = new IndexFieldSelector(5);
		rules = new ArrayList<GraphSeriesConfigRule>();
		rules.add(new GraphSeriesConfigRule("^TX-(.+)-S$", "$1", "RT",
				getLabelField(), new ConstantSelector(1), rtField,
				new AverageCalculation(), false, true, false));
		return createConfig("Transactions Average Response Times Overall", "", "response time / ms",
				rules, AxisMode.CATEGORIES);
	}

}
