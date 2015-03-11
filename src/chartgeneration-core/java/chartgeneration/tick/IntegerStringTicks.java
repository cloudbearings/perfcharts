package chartgeneration.tick;

import java.util.Map;

public class IntegerStringTicks implements Ticks {
	private Map<Integer, String> map;

	public IntegerStringTicks(Map<Integer, String> map) {
		this.map = map;
	}

	public String format() {
		StringBuilder sb = new StringBuilder("[");
		for (int key : map.keySet()) {
			sb.append("[").append(key).append(",\"")
					.append(map.get(key).replace("\"", "\\\"")).append("\"],");
		}
		if (!map.isEmpty())
			sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}
}
