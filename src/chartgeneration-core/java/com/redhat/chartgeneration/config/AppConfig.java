package com.redhat.chartgeneration.config;

import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.configtemplate.ChartConfigTemplate;

public class AppConfig {
	private String version;
	private String title;
	private String author;
	private FieldSelector labelField;
	private String inputFile;
	private String outputFile;
	List<ChartConfigTemplate> configTemplate;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public FieldSelector getLabelField() {
		return labelField;
	}
	public void setLabelField(FieldSelector labelField) {
		this.labelField = labelField;
	}
	public String getInputFile() {
		return inputFile;
	}
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	public String getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}
	public List<ChartConfigTemplate> getConfigTemplate() {
		return configTemplate;
	}
	public void setConfigTemplate(List<ChartConfigTemplate> configTemplate) {
		this.configTemplate = configTemplate;
	}
	
}
