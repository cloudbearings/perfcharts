package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.calc.AverageCalculation;
import com.redhat.chartgeneration.common.ConstantSelector;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.Chart2DSeriesConfigRule;
import com.redhat.chartgeneration.config.Chart2DConfig;

public class JmeterAverageRTOverallChartTemplate extends BaseChart2DTemplate {

	@Override
	public Chart2DConfig generateChartConfig() {
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector rtField = new IndexFieldSelector(5);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^TX-(.+)-S$", "$1", "RT",
				getLabelField(), new ConstantSelector(1), rtField,
				new AverageCalculation(), false, true, false));
		return createConfig("Transactions Average Response Times Overall", "", "response time / ms",
				rules, AxisMode.CATEGORIES);
	}

}
