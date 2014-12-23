package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.calc.CountCalculation;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.Chart2DSeriesConfigRule;
import com.redhat.chartgeneration.config.Chart2DConfig;

public class JmeterHitsChartTemplate extends BaseChart2DTemplateWithInterval {

	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		if (interval < 1)
			interval = 10000;
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector yField = new IndexFieldSelector(2);
		//FieldSelector xField = new AddTransformSelector(timestampField,yField);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^HIT-.*", "hits", "HITS",
				getLabelField(), timestampField, yField, new CountCalculation(
						interval, 1000.0 / interval)));
		return createConfig("Hits over Time", "time",
				"hits", rules, AxisMode.TIME);
	}

}
