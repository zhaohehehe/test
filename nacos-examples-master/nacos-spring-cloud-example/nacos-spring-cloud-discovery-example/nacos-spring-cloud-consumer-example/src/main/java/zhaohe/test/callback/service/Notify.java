package zhaohe.test.callback.service;

import zhaohe.test.service.Person;

public interface Notify {
	public void onreturn(Person msg, Integer id);

	public void onthrow(Throwable ex, Integer id);
}
