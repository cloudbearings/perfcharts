package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.EmptyCalculation;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.tick.LongStringTickGenerator;
public class JmeterRTTrendChartTemplate extends BaseChart2DTemplate {

	@Override
	public Chart2DConfig generateChartConfig() {
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector xField = new IndexFieldSelector(1);
		FieldSelector rtField = new IndexFieldSelector(2);
		FieldSelector buildIDField = rtField;
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^TX-(.+)$", "$1", "ms",
				getLabelField(), xField, rtField, new EmptyCalculation(), true,
				false, false));
		Chart2DConfig cfg = createConfig("Response Time Trend", "Build No.", "Response Time", rules,
				AxisMode.INTEGER);
		cfg.setXTickGenerator(new LongStringTickGenerator("^XTICK$", getLabelField(), xField, buildIDField));
		return cfg;
	}

}
