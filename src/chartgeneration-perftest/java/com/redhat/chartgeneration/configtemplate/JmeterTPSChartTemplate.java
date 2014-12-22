package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.AddTransformSelector;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphSeriesConfigRule;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.graphcalc.CountCalculation;

public class JmeterTPSChartTemplate extends BaseGraphTemplateWithInterval {

	@Override
	public GraphConfig generateChartConfig() {
		int interval = getInterval();
		if (interval < 1)
			interval = 10000;
		List<GraphSeriesConfigRule> rules;
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector rtField = new IndexFieldSelector(5);
		FieldSelector xField = new AddTransformSelector(timestampField, rtField);
		rules = new ArrayList<GraphSeriesConfigRule>();
		rules.add(new GraphSeriesConfigRule("^TX-(.+)-S$", "$1-Success", "TPS", getLabelField(),
				xField, null, new CountCalculation(interval, 1000.0 / interval), true, false, false));
		rules.add(new GraphSeriesConfigRule("^TX-(.+)-F$", "$1-Failure", "TPS", getLabelField(),
				xField, null, new CountCalculation(interval, 1000.0 / interval), true, false, false));
		return createConfig("TPS over Time", "time", "TPS", rules, AxisMode.TIME);
	}

}
