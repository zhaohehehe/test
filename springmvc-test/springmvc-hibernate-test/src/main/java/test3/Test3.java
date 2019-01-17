package test3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 使用事物注解
 * @author zhaohe
 *
 */
public class Test3 {
	public static void main(final String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext-hibernate.xml", Test3.class);
        UserService userServiceImpl = (UserService) ctx.getBean("userServiceImpl");
        userServiceImpl.addSingleUser(new User("1234","1234"));
    }

}
