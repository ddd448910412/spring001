package com.yijie.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.yijie.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;


@Repository
public class UserDao {
	private JdbcTemplate jdbcTemplate;

	//查看用户名和账号是否正确
	private  final static String MATCH_COUNT_SQL = " SELECT count(*) FROM t_user  " +
			" WHERE user_name =? and password=? ";
	//根据id更新信息
	private  final static String UPDATE_LOGIN_INFO_SQL = " UPDATE t_user SET " +
			" last_visit=?,last_ip=?,credits=?  WHERE user_id =?";

    /**
     * 查看用户名和账号是否正确
     */
	public int getMatchCount(String userName, String password) {

		return jdbcTemplate.queryForObject(MATCH_COUNT_SQL, new Object[]{userName, password}, Integer.class);
	}

	/**
	 * 根据用户名返回用户信息
	 * @param userName
	 * @return
	 */
	public User findUserByUserName(final String userName) {
		String sqlStr = " SELECT user_id,user_name,credits "
				+ " FROM t_user WHERE user_name =? ";
		final User user = new User();
		jdbcTemplate.query(sqlStr, new Object[] { userName },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						user.setUserId(rs.getInt("user_id"));
						user.setUserName(userName);
						user.setCredits(rs.getInt("credits"));
					}
				});
		return user;
	}

	/**
	 * 根据id更新用户信息
	 * @param user
	 */
	public void updateLoginInfo(User user) {
		jdbcTemplate.update(UPDATE_LOGIN_INFO_SQL, new Object[] { user.getLastVisit(),
				user.getLastIp(),user.getCredits(),user.getUserId()});
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
