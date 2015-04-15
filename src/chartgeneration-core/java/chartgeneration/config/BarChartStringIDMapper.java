package chartgeneration.config;

import java.util.Map;

import chartgeneration.chart.Chart2D;

public interface BarChartStringIDMapper {
	Map<String, Integer> map(Chart2D chart, Chart2DConfig config);
}
