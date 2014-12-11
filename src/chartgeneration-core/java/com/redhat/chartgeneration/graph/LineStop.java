package com.redhat.chartgeneration.graph;

public class LineStop {
	private Object x;
	private double y;
	private int weight = 1;

	public LineStop() {

	}

	public LineStop(Object x, double y, int w) {
		this.x = x;
		this.y = y;
		this.weight = w;
	}

	public Object getX() {
		return x;
	}

	public void setX(Object x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int w) {
		this.weight = w;
	}
}
