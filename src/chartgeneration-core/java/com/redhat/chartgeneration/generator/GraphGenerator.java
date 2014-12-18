package com.redhat.chartgeneration.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.config.AppData;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.config.GraphLineConfig;
import com.redhat.chartgeneration.config.GraphLineConfigRule;
import com.redhat.chartgeneration.config.LineConfigGenerator;
import com.redhat.chartgeneration.graphcalc.GraphCalculation;
import com.redhat.chartgeneration.report.GraphLine;
import com.redhat.chartgeneration.report.LineStop;
import com.redhat.chartgeneration.report.StatGraph;

public class GraphGenerator implements Generator {
	private GraphConfig graphConfig;
	private final static int DEFAULT_POINTS_PER_GRAPH = 100;

	public GraphGenerator(GraphConfig lineGraphConfig) {
		this.graphConfig = lineGraphConfig;
	}

	public StatGraph generate(final PerfLog log) throws IOException {
		Logger logger = AppData.getInstance().getLogger();

		final List<List<Object>> rows = log.getRows();

		final LineConfigGenerator cfgGenerator = new LineConfigGenerator();
		List<GraphLineConfigRule> rules = graphConfig.getRules();
		final List<GraphLineConfig> lineConfigs = new ArrayList<GraphLineConfig>();
		for (GraphLineConfigRule rule : rules) {
			lineConfigs.addAll(cfgGenerator.generate(rule, log));
		}

		final List<GraphLine> resultLines = new ArrayList<GraphLine>(
				lineConfigs.size());
		for (final GraphLineConfig config : lineConfigs) {
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
			List<LineStop> lineStops = calc.produce(involvedRows,
					config.getXField(), config.getYField());
			GraphLine line = new GraphLine(config.getLabel(), config.getUnit(),
					lineStops, config.isShowLines(), config.isShowBars(),
					config.isShowUnit());
			resultLines.add(line);
		}
		return new StatGraph(graphConfig.getTitle(), graphConfig.getSubtitle(),
				graphConfig.getXLabel(), graphConfig.getYLabel(), resultLines,
				graphConfig.getXaxisMode());
	}

	public GraphConfig getLineGraphConfig() {
		return graphConfig;
	}

	public void setLineGraphConfig(GraphConfig lineGraphConfig) {
		this.graphConfig = lineGraphConfig;
	}

}
