package src.net.bittreasury.fts.web.action;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import net.sf.json.JSONObject;

public class JsonTest {
	
	@Test
	public void test() {
		String string = "true";
		Map<String, Object> map = new HashMap<>();
		map.put("judge", string);
		JSONObject jsonObject = JSONObject.fromObject(map);
		String json = jsonObject.toString();
		System.out.println(json);
	}
}
