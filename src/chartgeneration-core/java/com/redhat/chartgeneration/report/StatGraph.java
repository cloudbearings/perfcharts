package com.redhat.chartgeneration.report;

import java.util.List;

import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.formatter.FlotChartFormatter;
import com.redhat.chartgeneration.formatter.StatGraphFormatter;

public class StatGraph extends StatChart {
	private String xLabel;
	private String yLabel;
	private AxisMode xaxisMode;
	private List<GraphLine> lines;
	private StatGraphFormatter formatter = new FlotChartFormatter();

	public StatGraph() {
	}

	public StatGraph(String title, String subtitle, String xLabel,
			String yLabel, List<GraphLine> lines, AxisMode xaxisMode) {
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

	public List<GraphLine> getLines() {
		return lines;
	}

	public void setLines(List<GraphLine> lines) {
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

	public StatGraphFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(StatGraphFormatter formatter) {
		this.formatter = formatter;
	}

	

}
