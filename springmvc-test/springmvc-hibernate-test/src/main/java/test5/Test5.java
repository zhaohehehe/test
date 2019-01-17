package test5;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * jdbc模板
 * http://www.cnblogs.com/caoyc/p/5630622.html
 * @author zhaohe
 *
 */
public class Test5 {
	public static void main(final String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext-hibernate.xml", Test5.class);
        UserService userServiceImpl = (UserService) ctx.getBean("userServiceImpl");
        int id=1;
        System.out.println(userServiceImpl.getNameOfSingleUser(id));
    }

}
