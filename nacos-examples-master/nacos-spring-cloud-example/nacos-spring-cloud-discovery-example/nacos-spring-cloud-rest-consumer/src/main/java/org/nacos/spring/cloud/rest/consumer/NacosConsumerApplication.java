package org.nacos.spring.cloud.rest.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.bonc.dataplatform.buw.util.nacos.NacosUtil;

/**
 * @author xiaojing
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConsumerApplication {

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(NacosConsumerApplication.class, args);
	}

	@RestController
	public class TestController {

		@Autowired
		RestTemplate restTemplate;

		@Autowired
		private LoadBalancerClient loadBalancerClient;

		@Value("${spring.cloud.nacos.discovery.server-addr}")
		private String nacosAddr;

		/*
		 * @Autowired public TestController(RestTemplate restTemplate) {
		 * this.restTemplate = restTemplate; }
		 */

		@RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
		public String echo(@PathVariable String str) {
			return restTemplate.getForObject("http://service-provider/echo/" + str, String.class);
		}

		@RequestMapping(value = "/echo2/{str}", method = RequestMethod.GET)
		public String echo2(@PathVariable String str) {
			// 使用 LoadBalanceClient 和 RestTemolate 结合的方式来访问
			ServiceInstance serviceInstance = loadBalancerClient.choose("service-provider");
			String url = String.format("http://%s:%s/%s/%s/%s", serviceInstance.getHost(), serviceInstance.getPort(),
					"service-provider", "echo", str);
			System.out.println("request url:" + url);
			return new RestTemplate().getForObject(url, String.class);
		}

		@RequestMapping(value = "/echo3/{str}", method = RequestMethod.GET)
		public String echo3() throws NacosException {
			String namespace = "proxy-test";
			NamingService namingService = NacosUtil.holdAnamingService(nacosAddr, namespace);
			List<Instance> instances = namingService.getAllInstances("service-provider");
			return namespace;
		}

		@RequestMapping(value = "/echo/test", method = RequestMethod.GET)
		public Object echoTest(@RequestParam(required = true, value = "proxyCode") String namespace,
				@RequestParam(required = true) String clusterAppName) throws NacosException {
			NamingService namingService = NacosUtil.holdAnamingService(nacosAddr, namespace);
			List<Instance> instances = namingService.selectInstances(clusterAppName, true);
			boolean isHasEcho = false;
			if (instances != null && !instances.isEmpty()) {
				for (Instance instance : instances) {
					if (instance.isEphemeral()) {
						String url = String.format("http://%s:%d/%s/%s", instance.getIp(), instance.getPort(),
								clusterAppName, "echo");
						System.out.println("request url:" + url);
						try {
							String response = new RestTemplate().getForObject(url, String.class);
							isHasEcho = "SUCCESS".equalsIgnoreCase(response);
						} catch (Exception e) {
							System.out.println("request url fail:" + e.getMessage());
						}
						if (isHasEcho) {
							break;
						}
					}
				}
			}
			return isHasEcho;
		}

	}
}
