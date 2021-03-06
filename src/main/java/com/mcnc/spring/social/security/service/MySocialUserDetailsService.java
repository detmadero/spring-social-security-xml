package com.mcnc.spring.social.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import com.mcnc.spring.social.security.dao.MyUserAccountDAO;
import com.mcnc.spring.social.security.model.MyUserAccount;
import com.mcnc.spring.social.security.user.MySocialUserDetails;

public class MySocialUserDetailsService implements SocialUserDetailsService {
	
	@Autowired
	private MyUserAccountDAO myUserAccountDAO;

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		
		MyUserAccount account = myUserAccountDAO.findById(userId);

		MySocialUserDetails userDetails = new MySocialUserDetails(account);

		return userDetails;
	}

}
