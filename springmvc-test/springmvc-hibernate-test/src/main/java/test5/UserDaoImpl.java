package test5;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String getNameOfSingleUser(int id) {
		String sql = "select user_name from user_info_tbl where id=?";
		return "getNameOfSingleUser="+jdbcTemplate.queryForObject(sql, String.class,id);
	}

}
/*public class UserDaoImpl extends JdbcDaoSupport implements UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String getNameOfSingleUser(int id) {
		String sql = "select user_name from user_info_tbl where id=?";
		return "getNameOfSingleUser="+jdbcTemplate.queryForObject(sql, String.class,id);
	}
	public void setDataSourceOverride(DataSource dataSource){
		super.setDataSource(dataSource);
	}

}*/
