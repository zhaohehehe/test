package zhaohe.test.service;

import net.sf.json.JSONObject;

public interface HelloService {
	Person hello(Integer id);

	JSONObject hi(Integer id);
}
