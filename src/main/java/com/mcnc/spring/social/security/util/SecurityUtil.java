package com.mcnc.spring.social.security.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mcnc.spring.social.security.model.MyUserAccount;
import com.mcnc.spring.social.security.user.MyUserDetails;

public class SecurityUtil {

	// Auto login.
	public static void logInUser(MyUserAccount myUserAccount) {

		MyUserDetails myUserDetails = new MyUserDetails(myUserAccount);

		Authentication authentication = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
