package com.mcnc.spring.social.security.dao;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.security.SocialAuthenticationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mcnc.spring.social.security.form.MyUserAccountForm;
import com.mcnc.spring.social.security.mapper.MyUserAccountMapper;
import com.mcnc.spring.social.security.model.MyUserAccount;

@Repository
@Transactional
public class MyUserAccountDAO extends JdbcDaoSupport {

	@Autowired
	public MyUserAccountDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public MyUserAccount findById(String id) {
		String sql = "SELECT id, email, user_name, first_name, last_name, password, role, enabled"
				+ " FROM user_accounts u " + " WHERE id = ? ";
		Object[] params = new Object[] { id };
		MyUserAccountMapper mapper = new MyUserAccountMapper();
		try {
			MyUserAccount userInfo = this.getJdbcTemplate().queryForObject(sql,
					params, mapper);
			return userInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public MyUserAccount findByEmail(String email) {
		String sql = "SELECT id, email, user_name, first_name, last_name, password, role, enabled"
				+ " FROM user_accounts u " + " WHERE email = ? ";
		Object[] params = new Object[] { email };
		MyUserAccountMapper mapper = new MyUserAccountMapper();
		try {
			MyUserAccount userInfo = this.getJdbcTemplate().queryForObject(sql,
					params, mapper);
			return userInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public MyUserAccount findByUserName(String userName) {
		String sql = "SELECT id, email, user_name, first_name, last_name, password, role, enabled"
				+ " FROM user_accounts u " + " WHERE user_name = ? ";
		Object[] params = new Object[] { userName };
		MyUserAccountMapper mapper = new MyUserAccountMapper();
		try {
			MyUserAccount userInfo = this.getJdbcTemplate().queryForObject(sql,
					params, mapper);
			return userInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public MyUserAccount registerNewUserAccount(MyUserAccountForm accountForm) {
		String sql = "INSERT INTO user_accounts"
				+ " (id, email, user_name, first_name, last_name, password, role)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?) ";

		// Random string with 36 characters.
		String id = UUID.randomUUID().toString();

		this.getJdbcTemplate().update(sql, id, accountForm.getEmail(), //
				accountForm.getUserName(), //
				accountForm.getFirstName(), accountForm.getLastName(), //
				accountForm.getPassword(), MyUserAccount.ROLE_USER);
		return findById(id);
	}

	// Auto Create USER_ACCOUNTS.
	public MyUserAccount createUserAccount(Connection<?> connection) {

		ConnectionKey key = connection.getKey();
		// (facebook,12345), (google,123) ...

		System.out.println("key = (" + key.getProviderId() + ", "
				+ key.getProviderUserId() + ")");

		UserProfile userProfile = connection.fetchUserProfile();

		String email = userProfile.getEmail();
		
		if ( email == null ) {
			throw new SocialAuthenticationException("User doesn't provide email to our application.");
		}
		
		MyUserAccount account = this.findByEmail(email);
		if (account != null) {
			return account;
		}

		// Create User_Account.
		String sql = "INSERT INTO user_accounts "
				+ " (id, email, user_name, first_name, last_name, password, role) "
				+ " values (?, ?, ?, ?, ?, ?, ?) ";

		// Random string with 36 characters.
		String id = UUID.randomUUID().toString();

		String userName_prefix = userProfile.getFirstName().trim()
				+ " " + userProfile.getLastName().trim();

		String userName = this.findAvailableUserName(userName_prefix);

		this.getJdbcTemplate().update(sql, id, email, userName, //
				userProfile.getFirstName(), userProfile.getLastName(), //
				"123", MyUserAccount.ROLE_USER);
		return findById(id);
	}

	private String findAvailableUserName(String userName_prefix) {
		MyUserAccount account = this.findByUserName(userName_prefix);
		if (account == null) {
			return userName_prefix;
		}
		int i = 0;
		while (true) {
			String userName = userName_prefix + "_" + i++;
			account = this.findByUserName(userName);
			if (account == null) {
				return userName;
			}
		}
	}
}
