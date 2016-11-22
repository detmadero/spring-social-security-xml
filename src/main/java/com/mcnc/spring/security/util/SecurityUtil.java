package com.mcnc.spring.security.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mcnc.spring.security.model.MyUserAccount;
import com.mcnc.spring.security.user.MyUserDetails;

public class SecurityUtil {

	// Auto login.
	public static void logInUser(MyUserAccount user) {

		MyUserDetails userDetails = new MyUserDetails(user);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
