package com.mcnc.spring.social.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mcnc.spring.social.security.dao.MyUserAccountDAO;
import com.mcnc.spring.social.security.model.MyUserAccount;
import com.mcnc.spring.social.security.user.MyUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private MyUserAccountDAO myUserAccountDAO;

	// (This method is used by Spring Security API).
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		MyUserAccount myUserAccount = myUserAccountDAO.findByEmail(email);

		if (myUserAccount == null) {
			throw new UsernameNotFoundException("No user found with email: " + email);
		}
		// Note: SocialUserDetails extends UserDetails.
		UserDetails principal = new MyUserDetails(myUserAccount);

		return principal;
	}

}
