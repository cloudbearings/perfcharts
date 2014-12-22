package com.redhat.chartgeneration.parser;

import java.io.InputStream;
import java.io.OutputStream;

public interface DataParser {
	public void parse(InputStream in, OutputStream out) throws Exception;
}
