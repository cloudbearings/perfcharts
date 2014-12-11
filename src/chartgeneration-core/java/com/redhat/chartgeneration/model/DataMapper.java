package com.redhat.chartgeneration.model;

import java.util.List;

public interface DataMapper<T> {
	public T map(List<String> item);
}
