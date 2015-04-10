package chartgeneration.common;

import java.util.regex.Pattern;

public class StringExtractionTransformSelector {
	private Pattern pattern;
	private String replacement;
	public StringExtractionTransformSelector(Pattern pattern, String replacement) {
		super();
		this.pattern = pattern;
		this.replacement = replacement;
	}
	public Pattern getPattern() {
		return pattern;
	}
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	public String getReplacement() {
		return replacement;
	}
	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}
	
}
