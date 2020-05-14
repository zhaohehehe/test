package com.alibaba.nacos.example.spring.cloud;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ConfigurableApplicationContext;
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
		ConfigurableApplicationContext context = SpringApplication.run(NacosConsumerApplication.class, args);
		HelloService helloService = (HelloService) context.getBean("helloService");
		Person ret = helloService.hello(2);
		RpcContext rpc = RpcContext.getContext();

		RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class)
				.getAdaptiveExtension();
		Registry registry = registryFactory.getRegistry(URL.valueOf("zookeeper://10.20.153.10:2181"));
		registry.register(URL.valueOf(
				"override://0.0.0.0/com.foo.BarService?category=configurators&dynamic=false&application=foo&mock=force:return+null"));

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

		/**
		 * 静态服务：人为编码注册或者反注册服务，消费者正常使用即可。
		 */

		/**
		 * 回声测试
		 * 
		 * @return
		 * @throws InterruptedException
		 */
		@RequestMapping(value = "/echo", method = RequestMethod.GET)
		public boolean echo() throws InterruptedException {
			EchoService echoService = (EchoService) helloService; // 强制转型为EchoService
			// 回声测试可用性
			String status = (String) echoService.$echo("OK");
			return "OK".equalsIgnoreCase(status);
		}

		/**
		 * 测试事件通知
		 * 
		 * @return
		 * @throws InterruptedException
		 */
		@RequestMapping(value = "/eventNotify", method = RequestMethod.GET)
		public String eventNotify() throws InterruptedException {
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
