package com.redhat.chartgeneration.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.redhat.chartgeneration.common.AppData;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.config.GraphSeriesConfig;
import com.redhat.chartgeneration.config.GraphSeriesConfigRule;
import com.redhat.chartgeneration.graphcalc.GraphCalculation;
import com.redhat.chartgeneration.model.PerfLog;
import com.redhat.chartgeneration.report.Graph;
import com.redhat.chartgeneration.report.GraphPoint;
import com.redhat.chartgeneration.report.GraphSeries;

public class GraphGenerator implements Generator {
	private GraphConfig graphConfig;
	private final static int DEFAULT_POINTS_PER_GRAPH = 100;
	private GraphFactory factory;

	public GraphGenerator() {

	}

	public GraphGenerator(GraphFactory factory, GraphConfig lineGraphConfig) {
		this.graphConfig = lineGraphConfig;
		this.factory = factory;
	}

	public Graph generate(final PerfLog log) throws Exception {
		Logger logger = AppData.getInstance().getLogger();

		final List<List<Object>> rows = log.getRows();

		final GraphSeriesConfigBuilder cfgGenerator = new GraphSeriesConfigBuilder();
		List<GraphSeriesConfigRule> rules = graphConfig.getRules();
		final List<GraphSeriesConfig> lineConfigs = new ArrayList<GraphSeriesConfig>();
		for (GraphSeriesConfigRule rule : rules) {
			lineConfigs.addAll(cfgGenerator.build(rule, log));
		}

		final List<GraphSeries> resultLines = new ArrayList<GraphSeries>(
				lineConfigs.size());
		for (final GraphSeriesConfig config : lineConfigs) {
			final LinkedList<List<Object>> involvedRows = new LinkedList<List<Object>>();
			for (List<Object> row : rows) {
				if (config.getInvolvedRowLabels().contains(
						config.getLabelField().select(row))) {
					involvedRows.add(row);
				}
			}
			if (involvedRows.isEmpty())
				continue;
			Collections.sort(involvedRows, new Comparator<List<Object>>() {
				@Override
				public int compare(List<Object> o1, List<Object> o2) {
					@SuppressWarnings("unchecked")
					Comparable<Object> a = (Comparable<Object>) config
							.getXField().select(o1);
					@SuppressWarnings("unchecked")
					Comparable<Object> b = (Comparable<Object>) config
							.getXField().select(o2);
					return a.compareTo(b);
				}
			});
			GraphCalculation calc = config.getCalculation();
			int interval = calc.getInterval();
			if (interval == 0) {
				FieldSelector xField = config.getxField();
				long minX = ((Number) xField.select(involvedRows.getFirst()))
						.longValue();
				long maxX = ((Number) xField.select(involvedRows.getLast()))
						.longValue();
				interval = (int) (maxX - minX) / DEFAULT_POINTS_PER_GRAPH;
				if (interval < 1)
					interval = 1;
				calc.setInterval(interval);
				logger.info("use automatic interval value " + interval
						+ " for series '" + config.getLabel() + "' in chart '"
						+ graphConfig.getTitle() + "'");
			}
			List<GraphPoint> lineStops = calc.produce(involvedRows,
					config.getXField(), config.getYField());
			GraphSeries line = new GraphSeries(config.getLabel(),
					config.getUnit(), lineStops, config.isShowLines(),
					config.isShowBars(), config.isShowUnit());
			resultLines.add(line);
		}
		Graph graph = new Graph(graphConfig.getTitle(), graphConfig.getSubtitle(),
				graphConfig.getXLabel(), graphConfig.getYLabel(), resultLines,
				graphConfig.getXaxisMode());
		graph.setFormatter(factory.createFormatter());
		return graph;
	}

	public GraphConfig getLineGraphConfig() {
		return graphConfig;
	}

	public void setLineGraphConfig(GraphConfig lineGraphConfig) {
		this.graphConfig = lineGraphConfig;
	}

}
