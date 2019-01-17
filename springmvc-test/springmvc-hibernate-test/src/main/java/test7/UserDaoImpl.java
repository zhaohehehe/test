package test7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class UserDaoImpl implements UserDao {
	
	private HibernateTemplate  hibernateTemplate;
	
	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public void addSingleUser(User user) {
		hibernateTemplate.save(user);
	}
}
