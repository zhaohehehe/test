package zhaohe.test.callback.service;

import java.util.HashMap;
import java.util.Map;

import zhaohe.test.service.Person;

public class NotifyImpl implements Notify {
	public Map<Integer, Person> ret = new HashMap<>();
	public Map<Integer, Throwable> errors = new HashMap<>();

	@Override
	public void onreturn(Person msg, Integer id) {
		System.out.println("onreturn:" + msg);
		ret.put(id, msg);

	}

	@Override
	public void onthrow(Throwable ex, Integer id) {
		System.out.println("onthrow:" + ex.getMessage());
		errors.put(id, ex);

	}

}
