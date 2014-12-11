package com.redhat.chartgeneration.graph;

import java.util.List;

import com.redhat.chartgeneration.config.AxisMode;

public class LineGraph {
	private String title;
	private String subtitle;
	private String xLabel;
	private String yLabel;
	private AxisMode xaxisMode;
	private List<GraphLine> lines;

	public LineGraph() {
	 
	}

	public LineGraph(String title, String subtitle, String xLabel, String yLabel,
			List<GraphLine> lines, AxisMode xaxisMode) {
		this.title = title;
		this.subtitle = subtitle;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.lines = lines;
		this.xaxisMode = xaxisMode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

}
