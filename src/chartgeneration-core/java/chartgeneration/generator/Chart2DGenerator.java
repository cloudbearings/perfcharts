package chartgeneration.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import chartgeneration.calc.Chart2DCalculation;
import chartgeneration.chart.Chart2D;
import chartgeneration.chart.Chart2DSeries;
import chartgeneration.chart.Point2D;
import chartgeneration.common.AppData;
import chartgeneration.common.FieldSelector;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.model.DataTable;
import chartgeneration.tick.TickGenerator;

/**
 * A generator reads data tables and produces {@link Chart2D} according to
 * preset configurations.
 * 
 * @author Rayson Zhu
 *
 */
public class Chart2DGenerator implements Generator {
	/**
	 * the chart configuration
	 */
	private Chart2DConfig chart2dConfig;
	/**
	 * the default points per chart for automatic interval selection
	 */
	private final static int DEFAULT_POINTS = 300;
	/**
	 * The factory for creating related objects.
	 */
	private Chart2DFactory factory;

	/**
	 * constructor
	 */
	public Chart2DGenerator() {

	}

	/**
	 * constructor
	 * 
	 * @param factory
	 *            The factory for creating related objects
	 * @param chart2dConfig
	 *            the chart configuration
	 */
	public Chart2DGenerator(Chart2DFactory factory, Chart2DConfig chart2dConfig) {
		this.chart2dConfig = chart2dConfig;
		this.factory = factory;
	}

	public Chart2D generate(final DataTable dataTable) throws Exception {
		Logger logger = AppData.getInstance().getLogger();

		final List<List<Object>> dataRows = dataTable.getRows();

		final Chart2DSeriesConfigBuilder seriesConfigBuilder = new Chart2DSeriesConfigBuilder();
		List<Chart2DSeriesConfigRule> rules = chart2dConfig.getRules();
		final List<Chart2DSeriesConfig> seriesConfigs = new ArrayList<Chart2DSeriesConfig>();

		// traverse the rules and collect all generated SeriesConfigRules for
		// this chart
		for (Chart2DSeriesConfigRule rule : rules)
			seriesConfigs.addAll(seriesConfigBuilder.build(rule, dataTable));

		final List<Chart2DSeries> series = new ArrayList<Chart2DSeries>(
				seriesConfigs.size());
		for (final Chart2DSeriesConfig seriesConfig : seriesConfigs) {
			LinkedList<List<Object>> involvedRows = new LinkedList<List<Object>>();
			// collect involved rows
			for (List<Object> row : dataRows) {
				if (seriesConfig.getInvolvedRowLabels().contains(
						seriesConfig.getLabelField().select(row)))
					involvedRows.add(row);

			}
			if (involvedRows.isEmpty())
				continue;
			// sort involvedRows by x-value
			Collections.sort(involvedRows, new Comparator<List<Object>>() {
				@Override
				public int compare(List<Object> o1, List<Object> o2) {
					@SuppressWarnings("unchecked")
					Comparable<Object> a = (Comparable<Object>) seriesConfig
							.getXField().select(o1);
					@SuppressWarnings("unchecked")
					Comparable<Object> b = (Comparable<Object>) seriesConfig
							.getXField().select(o2);
					return a.compareTo(b);
				}
			});
			Chart2DCalculation calc = seriesConfig.getCalculation();
			
			int interval = chart2dConfig.getInterval();
			// If user didn't specify the interval, produce one.
			if (interval == 0) {
				FieldSelector xField = seriesConfig.getXField();
				long minX = ((Number) xField.select(involvedRows.getFirst()))
						.longValue();
				long maxX = ((Number) xField.select(involvedRows.getLast()))
						.longValue();
				interval = (int) (maxX - minX) / DEFAULT_POINTS;
				if (interval < 1)
					interval = 1;
				calc.setInterval(interval);
				logger.info("use automatic interval value " + interval
						+ " for series '" + seriesConfig.getLabel() + "' in chart '"
						+ chart2dConfig.getTitle() + "'");
			}
			// execute the calculation defined by Chart2DSeriesConfig, and
			// collect generated points
			List<Point2D> lineStops = calc.produce(involvedRows,
					seriesConfig.getXField(), seriesConfig.getYField());
			Chart2DSeries line = new Chart2DSeries(seriesConfig.getLabel(),
					seriesConfig.getUnit(), lineStops, seriesConfig.isShowLine(),
					seriesConfig.isShowBar(), seriesConfig.isShowUnit());
			series.add(line);
		}
		Chart2D graph = new Chart2D(chart2dConfig.getTitle(),
				chart2dConfig.getSubtitle(), chart2dConfig.getXLabel(),
				chart2dConfig.getYLabel(), series, chart2dConfig.getXaxisMode());
		// set up appropriate formatter
		graph.setFormatter(factory.createFormatter());
		TickGenerator xTickGenerator = chart2dConfig.getXTickGenerator();
		if (xTickGenerator != null) {
			graph.setXTicks(xTickGenerator.generate(dataRows));
		}
		return graph;
	}

	/**
	 * Get the configuration of this chart.
	 * 
	 * @return the configuration
	 */
	public Chart2DConfig getChart2DConfig() {
		return chart2dConfig;
	}

	/**
	 * Set the configuration of this chart.
	 * 
	 * @param chart2DConfig
	 *            the configuration
	 */
	public void setChart2DConfig(Chart2DConfig chart2DConfig) {
		this.chart2dConfig = chart2DConfig;
	}

}
