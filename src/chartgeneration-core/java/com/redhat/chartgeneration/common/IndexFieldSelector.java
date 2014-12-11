package com.redhat.chartgeneration.common;

import java.util.List;

public class IndexFieldSelector implements FieldSelector {
	private int index;

	public IndexFieldSelector() {

	}

	public IndexFieldSelector(int index) {
		this.index = index;
	}

	public Object select(List<?> row) {
		return row.get(index);
	}
}
