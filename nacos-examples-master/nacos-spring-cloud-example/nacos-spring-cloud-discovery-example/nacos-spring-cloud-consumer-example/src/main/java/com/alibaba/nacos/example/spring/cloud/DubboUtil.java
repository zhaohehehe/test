package com.alibaba.nacos.example.spring.cloud;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.ReferenceConfigBase;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.common.Constants;

public class DubboUtil {
	static Logger logger = LoggerFactory.getLogger(DubboUtil.class);

	public static final String DUBBO_PROTOCOL = "dubbo";
	public static final String NACOS_PROTOCOL = "nacos";
	public static final String ZOOKEEPER_PROTOCOL = "zookeeper";

	private DubboUtil() {
	}

	private static final ApplicationConfig APPLICATION = new ApplicationConfig("test");
	private static final RegistryConfig REGISTRY = new RegistryConfig();
	private static final ConsumerConfig CONSUMER = new ConsumerConfig();

	static {
		try {
			ApplicationConfig application = (ApplicationConfig) SpringContextUtil
					.getBean("org.apache.dubbo.config.ApplicationConfig");
			APPLICATION.setName(application.getName());
		} catch (Exception e) {
			// 找不到org.apache.dubbo.config.ApplicationConfig
		}
		try {
			ConsumerConfig consumer = (ConsumerConfig) SpringContextUtil
					.getBean("org.apache.dubbo.config.ConsumerConfig");
			CONSUMER.setTimeout(consumer.getTimeout());
			CONSUMER.setRetries(consumer.getRetries());
		} catch (Exception e) {
			CONSUMER.setTimeout(60000);
			CONSUMER.setRetries(0);
		}
		CONSUMER.setCheck(false);
		try {
			RegistryConfig registry = (RegistryConfig) SpringContextUtil
					.getBean("org.apache.dubbo.config.RegistryConfig");
			REGISTRY.setProtocol(registry.getProtocol());
		} catch (Exception e) {
			// 找不到org.apache.dubbo.config.RegistryConfig
		}
		initDefaultConfig();
	}

	private static void initDefaultConfig() {
		try {
			Environment environment = SpringContextUtil.getBean(Environment.class);
			if (environment != null) {
				String protocol = environment.getProperty("test.registry.name");
				String applicationName = environment.getProperty("spring.application.name");
				if (StringUtils.isNotBlank(protocol)) {
					REGISTRY.setProtocol(protocol);
				}
				if (StringUtils.isNotBlank(applicationName)) {
					APPLICATION.setName(applicationName);
				}
			}
		} catch (Exception e) {
			// 找不到org.springframework.core.env.Environment
		}

	}

	public static <T> T getReference(String registryAddress, String registryGroup, String serviceGroup, Class<T> clazz,
			Integer retries) {
		return getReference(registryAddress, registryGroup, serviceGroup, "5.0",
				org.apache.dubbo.rpc.Constants.SCOPE_REMOTE, clazz, retries);
	}

	public static <T> T getReference(String registryAddress, String registryGroup, String serviceGroup,
			Class<T> clazz) {
		return getReference(registryAddress, registryGroup, serviceGroup, "5.0",
				org.apache.dubbo.rpc.Constants.SCOPE_REMOTE, clazz, null);
	}

	public static <T> T getReference(String registryAddress, String registryGroup, String serviceGroup, String scope,
			Class<T> clazz) {
		return getReference(registryAddress, registryGroup, serviceGroup, "5.0", scope, clazz, null);
	}

	public static <T> T getReferenceByUrl(String registryAddress, String url, String registryGroup, String serviceGroup,
			Class<T> clazz) {
		return getReferenceByUrl(registryAddress, url, registryGroup, serviceGroup, "5.0", clazz);
	}

	public static <T> T getReference(String registryAddress, String registryGroup, String serviceGroup, String version,
			String scope, Class<T> clazz, Integer retries) {
		Assert.isTrue(StringUtils.isNotBlank(registryAddress), "未指定dubbo服务Registry Address!!!");
		Assert.isTrue(StringUtils.isNotBlank(version), "未指定dubbo服务version!!!");
		boolean isZookeeperRegistry = false;
		if (registryAddress.startsWith(NACOS_PROTOCOL)) {
			// NACOS_PROTOCOL
		} else if (registryAddress.startsWith(ZOOKEEPER_PROTOCOL)) {
			isZookeeperRegistry = true;
		} else {
			Assert.isTrue(StringUtils.isNotBlank(REGISTRY.getProtocol()),
					"RegistryAddress未指定Registry Protocol前缀，而且从Environment中也未获取到Registry Protocol,请确认!!!");
			isZookeeperRegistry = ZOOKEEPER_PROTOCOL.equals(REGISTRY.getProtocol());
			if (isZookeeperRegistry) {
				registryAddress = ZOOKEEPER_PROTOCOL + "://" + registryAddress;
			} else {
				registryAddress = NACOS_PROTOCOL + "://" + registryAddress;
			}
		}
		if (StringUtils.isBlank(registryGroup)) {
			if (isZookeeperRegistry) {
				registryGroup = DUBBO_PROTOCOL;
			} else {
				registryGroup = Constants.DEFAULT_NAMESPACE_ID;
			}
		}
		if (StringUtils.isBlank(serviceGroup)) {
			if (isZookeeperRegistry) {
				serviceGroup = "";
			} else {
				serviceGroup = Constants.DEFAULT_NAMESPACE_ID;
			}
		}
		return getReference(isZookeeperRegistry, registryAddress, registryGroup, serviceGroup, version, scope, clazz,
				null, retries);
	}

	public static <T> Map<String, Object> testDubbo(String registryAddress, String registryGroup, String serviceGroup,
			Class<T> clazz) {
		boolean isZookeeperRegistry = false;
		if (registryAddress.startsWith(NACOS_PROTOCOL)) {
			// NACOS_PROTOCOL
		} else if (registryAddress.startsWith(ZOOKEEPER_PROTOCOL)) {
			isZookeeperRegistry = true;
		} else {
			Assert.isTrue(StringUtils.isNotBlank(REGISTRY.getProtocol()),
					"RegistryAddress未指定Registry Protocol前缀，而且从Environment中也未获取到Registry Protocol,请确认!!!");
			isZookeeperRegistry = ZOOKEEPER_PROTOCOL.equals(REGISTRY.getProtocol());
			if (isZookeeperRegistry) {
				registryAddress = ZOOKEEPER_PROTOCOL + "://" + registryAddress;
			} else {
				registryAddress = NACOS_PROTOCOL + "://" + registryAddress;
			}
		}
		if (StringUtils.isBlank(registryGroup)) {
			if (isZookeeperRegistry) {
				registryGroup = DUBBO_PROTOCOL;
			} else {
				registryGroup = Constants.DEFAULT_NAMESPACE_ID;
			}
		}
		if (StringUtils.isBlank(serviceGroup)) {
			if (isZookeeperRegistry) {
				serviceGroup = "";
			} else {
				serviceGroup = Constants.DEFAULT_NAMESPACE_ID;
			}
		}
		RegistryConfig registry = new RegistryConfig();
		registry.setRegister(true);
		registry.setAddress(registryAddress);
		registry.setProtocol(isZookeeperRegistry ? ZOOKEEPER_PROTOCOL : NACOS_PROTOCOL);
		if (isZookeeperRegistry) {
			registry.setGroup(registryGroup);
		} else {
			if (registry.getParameters() == null) {
				registry.setParameters(new HashMap<>());
			}
			registry.getParameters().put(PropertyKeyConst.NAMESPACE, registryGroup);
		}
		ReferenceConfig<T> reference = new ReferenceConfig<>();
		reference.setApplication(APPLICATION);
		reference.setRegistry(registry);
		reference.setProtocol(DUBBO_PROTOCOL);
		reference.setConsumer(CONSUMER);
		reference.setInterface(clazz);
		reference.setGroup(serviceGroup);
		reference.setVersion("5.0");
		reference.setScope(org.apache.dubbo.rpc.Constants.SCOPE_REMOTE);
		Map<String, Object> result = new ConcurrentHashMap<>();
		result.put("cacheName", registryGroup);
		result.put("registryGroup", registryGroup);
		result.put("serviceGroup", serviceGroup);
		result.put("reference", reference);
		return result;
	}

	public static <T> T getReferenceByUrl(String registryAddress, String url, String registryGroup, String serviceGroup,
			String version, Class<T> clazz) {
		Assert.isTrue(StringUtils.isNotBlank(registryAddress), "未指定dubbo服务Registry Address!!!");
		Assert.isTrue(StringUtils.isNotBlank(url), "未指定dubbo服务url!!!");
		Assert.isTrue(StringUtils.isNotBlank(version), "未指定dubbo服务version!!!");
		if (!url.startsWith(DUBBO_PROTOCOL)) {
			url = DUBBO_PROTOCOL + "://" + url;
		}
		boolean isZookeeperRegistry = false;
		if (registryAddress.startsWith(NACOS_PROTOCOL)) {
			// NACOS_PROTOCOL
		} else if (registryAddress.startsWith(ZOOKEEPER_PROTOCOL)) {
			isZookeeperRegistry = true;
		} else {
			Assert.isTrue(StringUtils.isNotBlank(REGISTRY.getProtocol()),
					"RegistryAddress未指定Registry Protocol前缀，而且从Environment中也未获取到Registry Protocol,请确认!!!");
			isZookeeperRegistry = ZOOKEEPER_PROTOCOL.equals(REGISTRY.getProtocol());
			if (isZookeeperRegistry) {
				registryAddress = ZOOKEEPER_PROTOCOL + "://" + registryAddress;
			} else {
				registryAddress = NACOS_PROTOCOL + "://" + registryAddress;
			}
		}
		if (StringUtils.isBlank(registryGroup)) {
			if (isZookeeperRegistry) {
				registryGroup = DUBBO_PROTOCOL;
			} else {
				registryGroup = Constants.DEFAULT_NAMESPACE_ID;
			}
		}
		if (StringUtils.isBlank(serviceGroup)) {
			if (isZookeeperRegistry) {
				serviceGroup = "";
			} else {
				serviceGroup = Constants.DEFAULT_NAMESPACE_ID;
			}
		}
		return getReference(isZookeeperRegistry, registryAddress, registryGroup, serviceGroup, version, null, clazz,
				url, null);
	}

	private static <T> T getReference(boolean isZookeeperRegistry, String registryAddress, String registryGroup,
			String serviceGroup, String version, String scope, Class<T> clazz, String url, Integer retries) {
		Assert.isTrue(StringUtils.isNotBlank(registryAddress), "未指定dubbo服务Registry Address!!!");
		Assert.isTrue(StringUtils.isNotBlank(registryGroup), "未指定dubbo服务registry Group!!!");
		Assert.isTrue(serviceGroup != null, "Service Group is null!!!");
		Assert.isTrue(StringUtils.isNotBlank(version), "未指定dubbo服务version!!!");
		RegistryConfig registry = new RegistryConfig();
		registry.setRegister(true);
		registry.setAddress(registryAddress);
		registry.setProtocol(isZookeeperRegistry ? ZOOKEEPER_PROTOCOL : NACOS_PROTOCOL);
		if (isZookeeperRegistry) {
			registry.setGroup(registryGroup);
		} else {
			if (registry.getParameters() == null) {
				registry.setParameters(new HashMap<>());
			}
			registry.getParameters().put(PropertyKeyConst.NAMESPACE, registryGroup);
			/**
			 * @date 2020-04-21
			 * 
			 *       <pre>
			 * dubbo 2.7.3升级 dubbo 2.7.6，同时添加version和group属性。
			 *       </pre>
			 * 
			 * @date 2020-05-25
			 * 
			 *       <pre>
			 * 原自定义缓存未添加同步代码，并发场景org.apache.dubbo.rpc.model.ConsumerModel.initMethodModels方法出现NullPointerException。
			 * 	去掉自定义缓存，改为使用dubbo ReferenceConfigCache。
			 *       </pre>
			 * 
			 * @see <code>
			 * 		org.apache.dubbo.registry.support.AbstractRegistryFactory.REGISTRIES类型为Map<RegistryAddress,Registry>
			 * 		</code>
			 * 
			 *      <pre>
			 * REGISTRIES的key不包含namespace,但是包含group和version。 如果项目不重启，那么Registry不会有变化。所以这里添加
			 * version的作用就是用于区分同一个nacos server下的不同namespace。同理：在nacos环境下，ReferenceConfigCache同样添加namespace。
			 * dubbo文档：http://dubbo.apache.org/zh-cn/docs/user/demos/reference-config-cache.html。
			 *      </pre>
			 * 
			 * @by zhaohe
			 */
		}
		ReferenceConfig<T> reference = new ReferenceConfig<>();
		reference.setApplication(APPLICATION);
		reference.setRegistry(registry);
		reference.setProtocol(DUBBO_PROTOCOL);
		reference.setConsumer(CONSUMER);
		reference.setInterface(clazz);
		reference.setGroup(serviceGroup);
		reference.setVersion(version);
		if (retries != null) {
			reference.setRetries(retries);
		}
		if (org.apache.dubbo.rpc.Constants.SCOPE_REMOTE.equalsIgnoreCase(scope)
				|| org.apache.dubbo.rpc.Constants.SCOPE_LOCAL.equalsIgnoreCase(scope)) {
			reference.setScope(scope);
		}
		String cacheName = registryGroup;
		if (StringUtils.isNotBlank(url)) {
			reference.setUrl(url);
			cacheName += "::" + url;
		}
		// return printTest(reference, cacheName, registry, clazz, serviceGroup,
		// version, scope);
		return ReferenceConfigCache.getCache(cacheName).get(reference);
	}

	public static <T> T printTest(ReferenceConfig<T> reference, String cacheName, RegistryConfig registry,
			Class<T> clazz, String serviceGroup, String version, String scope) {
		System.out.println("===================dubbo reference ============================");
		System.out.println("	============setApplication : " + APPLICATION);
		System.out.println("	===============setRegistry : " + registry);
		System.out.println("	===============setProtocol : " + DUBBO_PROTOCOL);
		System.out.println("	===============setConsumerTimeout : " + CONSUMER.getTimeout());
		System.out.println("	===============setConsumerRetries : " + CONSUMER.getRetries());
		System.out.println("	===============setInterface : " + clazz);
		System.out.println("	===============setGroup : " + serviceGroup);
		System.out.println("	===============setVersion : " + version);
		System.out.println("	===============setScope : " + scope);

		System.out.println("===================dubbo reference cache========================");
		ReferenceConfigCache referenceConfigCache = ReferenceConfigCache.getCache(cacheName);
		System.out.println("	============referenceConfigCache : " + referenceConfigCache);
		System.out.println("===================dubbo reference cache getProxies========================");
		for (Entry<Class<?>, ConcurrentMap<String, Object>> entry : referenceConfigCache.getProxies().entrySet()) {
			System.out.println("	============proxy key : " + entry.getKey());
			for (Entry<String, Object> entry2 : entry.getValue().entrySet()) {
				System.out.println("	============invoker key : " + entry2.getKey());
				System.out.println("	============invoker directory (" + entry2.getValue().getClass() + "): "
						+ entry2.getValue());
			}
		}

		System.out.println("===================dubbo reference cache getReferredReferences========================");
		for (Entry<String, ReferenceConfigBase<?>> entry : referenceConfigCache.getReferredReferences().entrySet()) {
			System.out.println("	============invoker key : " + entry.getKey());
			ReferenceConfigBase<?> base = entry.getValue();
			for (URL uRL : base.getExportedUrls()) {
				System.out.println("		============getExportedUrl : " + uRL);
			}
			System.out.println("	============getMetaData : " + base.getMetaData());
			System.out.println("	============getUniqueServiceName : " + base.getUniqueServiceName());
			System.out.println("	============getTimeout : " + base.getTimeout());
			System.out.println("	============getUrl : " + base.getUrl());
			Object target = base.getServiceMetadata().getTarget();
			System.out.println(
					"	============getServiceMetadata getAttachments: " + base.getServiceMetadata().getAttachments());
			System.out.println("	============getServiceMetadata getTarget: " + target);
			System.out.println("	============getRouter : " + base.getRouter());
			System.out.println("	============getRegistryIds : " + base.getRegistryIds());
			System.out.println("	============getLoadbalance : " + base.getLoadbalance());
			System.out.println("	============getProxy : " + base.getProxy());

		}
		T t = referenceConfigCache.get(reference);
		System.out.println("	============get Cache Proxy : " + t);
		return t;

	}

}
