java.lang.ClassCastException: com.sun.proxy.$Proxy* cannot be cast to***问题解决方案，com.sun.proxy

@Transactional
@Repository
public UserDaoImpl implements UserDao extends BaseDaoImpl{
/*没有实现任何接口*/
}
错误：
获取代理类：
ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
UserDaoImpl userDaoImpl = (UserDaoImpl)ctx.getBean("userDaoImpl");
解决：
ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml"); 
UserDao userDao = (UserDaoImpl)ctx.getBean("userDaoImpl"); 

病因： 
对于Spring AOP 采用两种代理方法，一种是常规JDK，一种是CGLIB，我的UserDao了一个接口IUserDao，当代理对象实现了至少一个接口时，默认使用JDK动态创建代理对象，当代理对象没有实现任何接口时，就会使用CGLIB方法。点此查看详细介绍>>> 
治疗方法 
如果你的代理对象没有实现接口的方法，就将代理对象转换成接口。 
获取代理类的代码该为： 