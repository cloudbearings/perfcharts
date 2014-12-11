package com.redhat.chartgeneration.generator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.redhat.chartgeneration.config.LineConfig;
import com.redhat.chartgeneration.config.LineConfigGenerator;
import com.redhat.chartgeneration.config.LineConfigRule;
import com.redhat.chartgeneration.config.LineGraphConfig;
import com.redhat.chartgeneration.graph.GraphLine;
import com.redhat.chartgeneration.graph.LineGraph;
import com.redhat.chartgeneration.graph.LineStop;

public class GraphGenerator {
	private LineGraphConfig lineGraphConfig;

	public LineGraph generate(InputStream in) throws IOException {
		LogReader reader = new LogReader();
		final PerfLog log = new PerfLog();
		final List<List<Object>> rows = reader
				.read(in/* , log.getFieldTypes() */);
		log.setRows(rows);
		return generate(log);
	}

	public LineGraph generate(final PerfLog log) throws IOException {
		final List<List<Object>> rows = log.getRows();

		final LineConfigGenerator cfgGenerator = new LineConfigGenerator();
		List<LineConfigRule> rules = lineGraphConfig.getRules();
		final List<LineConfig> lineConfigs = new ArrayList<LineConfig>();
		for (LineConfigRule rule : rules) {
			lineConfigs.addAll(cfgGenerator.generate(rule, log));
		}

		final List<GraphLine> resultLines = new ArrayList<GraphLine>(
				lineConfigs.size());
		for (final LineConfig config : lineConfigs) {
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
		return new LineGraph(lineGraphConfig.getTitle(), lineGraphConfig.getSubtitle(),
				lineGraphConfig.getXLabel(), lineGraphConfig.getYLabel(),
				resultLines, lineGraphConfig.getXaxisMode());
	}

	public LineGraphConfig getLineGraphConfig() {
		return lineGraphConfig;
	}

	public void setLineGraphConfig(LineGraphConfig lineGraphConfig) {
		this.lineGraphConfig = lineGraphConfig;
	}

}
