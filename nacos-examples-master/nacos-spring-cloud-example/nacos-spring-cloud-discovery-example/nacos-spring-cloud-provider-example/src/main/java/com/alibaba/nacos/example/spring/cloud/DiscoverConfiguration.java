package com.alibaba.nacos.example.spring.cloud;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;


//@Configuration
public class DiscoverConfiguration {

	@Value("${spring.cloud.nacos.discovery.server-addr: }")
	String adderss;
	
	@Value("${spring.cloud.nacos.discovery.namespace: }")
	String namespace;

	@Bean
	public NamingService transferService() throws NacosException {
        Properties properties = new Properties();
        properties.setProperty("serverAddr", adderss);
        properties.setProperty("namespace", namespace);
        return NacosFactory.createNamingService(properties);
	}

}
