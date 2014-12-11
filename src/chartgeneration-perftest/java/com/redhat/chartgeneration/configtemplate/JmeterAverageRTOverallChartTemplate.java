package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.ConstantSelector;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.LineConfigRule;
import com.redhat.chartgeneration.config.LineGraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;

public class JmeterAverageRTOverallChartTemplate extends BaseChartTemplate {

	@Override
	public LineGraphConfig generateGraphConfig() {
		List<LineConfigRule> rules;
		FieldSelector rtField = new IndexFieldSelector(5);
		rules = new ArrayList<LineConfigRule>();
		rules.add(new LineConfigRule("^TX-(.+)-[SF]$", "$1", "RT",
				getLabelField(), new ConstantSelector(1), rtField,
				new AverageCalculation(), false, true, false));
		return createConfig("Transactions Average Response Time Overall", "", "response time / ms",
				rules, AxisMode.CATEGORIES);
	}

}
