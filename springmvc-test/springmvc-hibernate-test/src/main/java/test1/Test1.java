package test1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 使用注解
 * 事物多种配置方式,其中 TransactionProxyFactoryBean出错
 * 扫描包多种方式
 * 所有配置参考:http://www.cnblogs.com/chenshaogang/p/5717267.html
 * @author zhaohe
 *
 */
public class Test1 {
	public static void main(final String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext-hibernate.xml", Test1.class);
        UserService userServiceImpl = (UserService) ctx.getBean("userServiceImpl");
        userServiceImpl.addSingleUser(new User("1234","1234"));
    }

}
