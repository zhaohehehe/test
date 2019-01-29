使用说明：

    1.application.properties配置数据库，这里配置的是mysql数据库，初始脚本如下;
		CREATE TABLE `city` (
		  `id` varchar(255) NOT NULL,
		  `province_id` varchar(255) DEFAULT NULL,
		  `city_name` varchar(255) DEFAULT NULL,
		  `description` varchar(255) DEFAULT NULL,
		  PRIMARY KEY (`id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		INSERT INTO `city` (`id`, `province_id`, `city_name`, `description`) VALUES ('12', '23', 'beijing', 'biewjing');
	2.启动main函数访问controller即可。
	
PS：

	该demo中整合了druid监控，如果不需要，可以注释掉DruidDataSourceConfig.java和
	DruidMonitorConfig.java以及properties文件中的整合druid部分注释掉。
	如果需要，则启动应用之后访问：http://127.0.0.1:8080/druid即可登录到druid监控。
