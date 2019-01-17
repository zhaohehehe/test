package test6;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * hibernate模板:HibernateDaoSupport
 * http://www.cnblogs.com/xdlaoliu/p/5645610.html
 * @author zhaohe
 *
 */
public class Test6 {
	public static void main(final String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext-hibernate.xml", Test6.class);
        UserService userServiceImpl = (UserService) ctx.getBean("userServiceImpl");
        User user=new User("4567","4567");
        userServiceImpl.addSingleUser(user);
    }

}
