package com.yijie.service;

import com.yijie.dao.LoginLogDao;
import com.yijie.dao.UserDao;
import com.yijie.domain.LoginLog;
import com.yijie.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	private UserDao userDao;
	private LoginLogDao loginLogDao;


	/**
	 * 是否能够登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean hasMatchUser(String userName, String password) {
		int matchCount =userDao.getMatchCount(userName, password);
		return matchCount > 0;
	}

	/**
	 * 查找用户信息
	 * @param userName
	 * @return
	 */
	public User findUserByUserName(String userName) {
		return userDao.findUserByUserName(userName);
	}

	/**
	 * 积分增加并且更新
	 * @param user
	 */
	@Transactional
    public void loginSuccess(User user) {
		user.setCredits( 5 + user.getCredits());
		LoginLog loginLog = new LoginLog();
		loginLog.setUserId(user.getUserId());
		loginLog.setIp(user.getLastIp());
		loginLog.setLoginDate(user.getLastVisit());
        userDao.updateLoginInfo(user);
        loginLogDao.insertLoginLog(loginLog);
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setLoginLogDao(LoginLogDao loginLogDao) {
		this.loginLogDao = loginLogDao;
	}
}
