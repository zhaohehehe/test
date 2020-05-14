package com.alibaba.nacos.example.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import zhaohe.test.callback.service.NotifyImpl;
import zhaohe.test.service.HelloService;
import zhaohe.test.service.Person;

/**
 * @author xiaojing
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "zhaohe.test")
@ImportResource("classpath:dubbo-consumer.xml")
public class NacosConsumerApplication {

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(NacosConsumerApplication.class, args);
	}

	@RestController
	public class TestController {

		private final RestTemplate restTemplate;

		@Autowired
		public TestController(RestTemplate restTemplate) {
			this.restTemplate = restTemplate;
		}

		@RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
		public String echo(@PathVariable String str) {
			return restTemplate.getForObject("http://service-provider/echo/" + str, String.class);
		}

	}

	@RestController
	public class TestDubboController {

		@Autowired
		HelloService helloService;
		@Autowired
		NotifyImpl demoCallback;

		@RequestMapping(value = "/zookeeper", method = RequestMethod.GET)
		public String zookeeper() throws InterruptedException {
			int requestId = 2;
			Person ret = helloService.hello(requestId);
			System.out.println(ret == null);
			System.out.println(demoCallback.ret.containsKey(requestId));
			for (int i = 0; i < 10; i++) {
				if (!demoCallback.ret.containsKey(requestId)) {
					Thread.sleep(200);
					System.out.println("-------------------------");
				} else {
					break;
				}
			}
			System.out.println(demoCallback.ret.containsKey(requestId));
			return helloService.hello(2).getName();
		}

		@RequestMapping(value = "/nacos", method = RequestMethod.GET)
		public String nacos() throws InterruptedException {
			int requestId = 2;
			Person ret = helloService.hello(requestId);
			System.out.println(ret == null);
			System.out.println(demoCallback.ret.containsKey(requestId));
			for (int i = 0; i < 10; i++) {
				if (!demoCallback.ret.containsKey(requestId)) {
					Thread.sleep(200);
					System.out.println("-------------------------");
				} else {
					break;
				}
			}
			System.out.println(demoCallback.ret.containsKey(requestId));
			return helloService.hello(2).getName();
		}

	}
}
