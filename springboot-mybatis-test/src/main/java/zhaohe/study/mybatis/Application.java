package zhaohe.study.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

//@MapperScan可以代替@Mapper,不需要为每个Mapper接口都写@Mapper注解. 可以写多个@MapperScan({"com.**.mapper1","com.**.mapper2"})
@MapperScan("zhaohe.**.mapper")
@ComponentScan(basePackages = "zhaohe.study")
@ServletComponentScan(basePackages = "zhaohe.study.**.controller")
// @EnableAspectJAutoProxy
// @PropertySource("classpath:udf.properties")
@SpringBootApplication
public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		// 程序启动入口
		// 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
		SpringApplication.run(Application.class, args);
		logger.info("spring boot start");
	}

}
