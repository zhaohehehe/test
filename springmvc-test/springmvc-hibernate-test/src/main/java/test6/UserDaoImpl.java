package test6;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory)
	{
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void addSingleUser(User user) {
		super.getHibernateTemplate().save(user);
	}
	
	/*//这里的sessionFacotry注入不是给类UserDaoImpl 的，而是给继承HibernateDaoSupport类的sessionFactory
	 <bean id="userDaoImpl" class="test6.UserDaoImpl">
	  <property name="sessionFactory" ref="sessionFactory"></property>
	 </bean>
	 */
}
