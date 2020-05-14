package com.alibaba.nacos.example.spring.cloud;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.common.Constants;

public class DubboUtil {

	private DubboUtil() {
	}
	public static final String DUBBO_PROTOCOL = "dubbo";
	public static final String NACOS_PROTOCOL = "nacos";
	public static final String ZOOKEEPER_PROTOCOL = "zookeeper";

	private static final ApplicationConfig APPLICATION = new ApplicationConfig("dig");
	private static final RegistryConfig REGISTRY = new RegistryConfig();
	private static final ConsumerConfig CONSUMER = new ConsumerConfig();
	private static final HashMap<String, Object> INTERFACE_CACHE = new HashMap<>();

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
				String protocol = environment.getProperty("bonc.registry.name");
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

	public static <T> T getReference(String registryAddress, String registryGroup, String serviceGroup,
			Class<T> clazz) {
		return getReference(registryAddress, registryGroup, serviceGroup, "1.0", clazz);
	}

	public static <T> T getReferenceByUrl(String registryAddress, String url, String registryGroup, String serviceGroup,
			Class<T> clazz) {
		return getReferenceByUrl(registryAddress, url, registryGroup, serviceGroup, "1.0", clazz);
	}

	public static <T> T getReference(String registryAddress, String registryGroup, String serviceGroup, String version,
			Class<T> clazz) {
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
		T cache = getCache(registryAddress, registryGroup, serviceGroup, version, clazz);
		if (cache != null) {
			return cache;
		}
		T t = getReference(isZookeeperRegistry, registryAddress, registryGroup, serviceGroup, version, clazz, null);
		setCache(registryAddress, registryGroup, serviceGroup, version, clazz, t);
		return t;
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
		String cacheUrl = registryGroup + "::" + url + "/" + serviceGroup + "/" + clazz.getName() + ":" + version;
		T cache = getCache(registryAddress, cacheUrl, clazz);
		if (cache != null) {
			return cache;
		}
		T t = getReference(isZookeeperRegistry, registryAddress, registryGroup, serviceGroup, version, clazz, url);
		setCache(registryAddress, cacheUrl, clazz, t);
		return t;
	}

	private static <T> T getReference(boolean isZookeeperRegistry, String registryAddress, String registryGroup,
			String serviceGroup, String version, Class<T> clazz, String url) {
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
			 * @description dubbo 2.7.3
			 * @see org.apache.dubbo.registry.support.AbstractRegistryFactory
			 *      REGISTRIES(Map<RegistryAddress,
			 *      Registry>)的key不包含namespace,但是包含group和version。
			 *      如果项目不重启，那么Registry不会有变化。所以这里添加version的作用就是用于区分同一个nacos
			 *      server下的不同namespace。(可能不是good practice) getRegistry(URL url)
			 * @date 2020-04-13
			 * @date 2020-04-21 升级dubbo2.7.6,同时添加version和group属性
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
		if (StringUtils.isNotBlank(url)) {
			reference.setUrl(url);
		}
		return reference.get();
	}

	public static <T> boolean containsInterface(String registryAddress, String group, Class<T> clazz) {
		String key = registryAddress + ";" + group + ";" + clazz.getName();
		return INTERFACE_CACHE.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	private static <T> T getCache(String registryAddress, String registryGroup, String serviceGroup, String version,
			Class<T> clazz) {
		String key = registryAddress + ";" + registryGroup + ";" + serviceGroup + ";" + version + ";" + clazz.getName();
		return (T) INTERFACE_CACHE.get(key);
	}

	private static <T> void setCache(String registryAddress, String registryGroup, String serviceGroup, String version,
			Class<T> clazz, T interface1) {

		String key = registryAddress + ";" + registryGroup + ";" + serviceGroup + ";" + version + ";" + clazz.getName();
		INTERFACE_CACHE.put(key, interface1);
	}

	@SuppressWarnings("unchecked")
	private static <T> T getCache(String registryAddress, String url, Class<T> clazz) {
		String key = registryAddress + ";" + url + ";" + clazz.getName();
		return (T) INTERFACE_CACHE.get(key);
	}

	private static <T> void setCache(String registryAddress, String url, Class<T> clazz, T interface1) {

		String key = registryAddress + ";" + url + ";" + clazz.getName();
		INTERFACE_CACHE.put(key, interface1);
	}

}
