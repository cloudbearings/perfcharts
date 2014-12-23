package com.redhat.chartgeneration.report;

import java.util.List;

import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.formatter.GraphFormatter;

public class Graph extends Chart {
	private String xLabel;
	private String yLabel;
	private AxisMode xaxisMode;
	private List<GraphSeries> lines;
	private GraphFormatter formatter;
	public GraphFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(GraphFormatter formatter) {
		this.formatter = formatter;
	}

	public Graph() {
	}

	public Graph(String title, String subtitle, String xLabel,
			String yLabel, List<GraphSeries> lines, AxisMode xaxisMode) {
		super(title, subtitle);
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.lines = lines;
		this.xaxisMode = xaxisMode;
	}

	public String getXLabel() {
		return xLabel;
	}

	public void setXLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public String getYLabel() {
		return yLabel;
	}

	public void setYLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public List<GraphSeries> getLines() {
		return lines;
	}

	public void setLines(List<GraphSeries> lines) {
		this.lines = lines;
	}

	public AxisMode getXaxisMode() {
		return xaxisMode;
	}

	public void setXaxisMode(AxisMode xaxisMode) {
		this.xaxisMode = xaxisMode;
	}

	@Override
	public String format() throws Exception {
		return formatter.format(this);
	}
}
