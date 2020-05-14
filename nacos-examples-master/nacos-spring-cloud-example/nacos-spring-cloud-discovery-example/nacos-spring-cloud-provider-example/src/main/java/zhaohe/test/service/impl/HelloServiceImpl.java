package zhaohe.test.service.impl;

import org.springframework.stereotype.Service;

import zhaohe.test.service.HelloService;
import zhaohe.test.service.Person;

@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public Person hello(Integer id) {
		System.out.println("=============================================");
		System.out.println("=============================================");
		return new Person(id, "test");
	}

}
