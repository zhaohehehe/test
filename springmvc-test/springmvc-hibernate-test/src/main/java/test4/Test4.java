package test4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 数据源配置方式：
 * 1.jdbc(不推荐)
 * @author zhaohe
 *
 */
public class Test4 {
	public static void main(final String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext-hibernate.xml", Test4.class);
        UserService userServiceImpl = (UserService) ctx.getBean("userServiceImpl");
        userServiceImpl.addSingleUser(new User("1234","1234"));
    }

}
