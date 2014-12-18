package com.redhat.chartgeneration.parser;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public interface DataParser {
	public void parse(InputStream in, OutputStream out) throws Exception;
}
