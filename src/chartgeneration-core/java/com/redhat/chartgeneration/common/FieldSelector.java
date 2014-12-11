package com.redhat.chartgeneration.common;

import java.util.List;

public interface FieldSelector {
	public Object select(List<?> row);
}
