package com.redhat.chartgeneration.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.config.LineConfigGenerator;
import com.redhat.chartgeneration.config.GraphLineConfig;
import com.redhat.chartgeneration.config.GraphLineConfigRule;
import com.redhat.chartgeneration.report.GraphLine;
import com.redhat.chartgeneration.report.StatGraph;
import com.redhat.chartgeneration.report.LineStop;

public class GraphGenerator implements Generator {
	private GraphConfig lineGraphConfig;

//	public LineGraph generate(InputStream in) throws IOException {
//		LogReader reader = new LogReader();
//		final PerfLog log = new PerfLog();
//		final List<List<Object>> rows = reader
//				.read(in/* , log.getFieldTypes() */);
//		log.setRows(rows);
//		return generate(log);
//	}
	public GraphGenerator(GraphConfig lineGraphConfig) {
		this.lineGraphConfig = lineGraphConfig;
	}

	public StatGraph generate(final PerfLog log) throws IOException {
		final List<List<Object>> rows = log.getRows();

		final LineConfigGenerator cfgGenerator = new LineConfigGenerator();
		List<GraphLineConfigRule> rules = lineGraphConfig.getRules();
		final List<GraphLineConfig> lineConfigs = new ArrayList<GraphLineConfig>();
		for (GraphLineConfigRule rule : rules) {
			lineConfigs.addAll(cfgGenerator.generate(rule, log));
		}

		final List<GraphLine> resultLines = new ArrayList<GraphLine>(
				lineConfigs.size());
		for (final GraphLineConfig config : lineConfigs) {
			final List<List<Object>> involvedRows = new LinkedList<List<Object>>();
			for (List<Object> row : rows) {
				if (config.getInvolvedRowLabels().contains(
						config.getLabelField().select(row))) {
					involvedRows.add(row);
				}
			}
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

			List<LineStop> lineStops = config.getCalculation().produce(
					involvedRows, config.getXField(), config.getYField());
			// if (!lineStops.isEmpty()) {
			GraphLine line = new GraphLine(config.getLabel(), config.getUnit(), lineStops,
					config.isShowLines(), config.isShowBars(), config.isShowUnit());
			resultLines.add(line);
			// }
		}
		return new StatGraph(lineGraphConfig.getTitle(), lineGraphConfig.getSubtitle(),
				lineGraphConfig.getXLabel(), lineGraphConfig.getYLabel(),
				resultLines, lineGraphConfig.getXaxisMode());
	}

	public GraphConfig getLineGraphConfig() {
		return lineGraphConfig;
	}

	public void setLineGraphConfig(GraphConfig lineGraphConfig) {
		this.lineGraphConfig = lineGraphConfig;
	}

}
