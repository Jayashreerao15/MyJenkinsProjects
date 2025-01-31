package com.itc.configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.itc.model.MyUsers;

public class UserInfoUserDetails implements UserDetails {

	private String userName;
	private String password;
	private List<GrantedAuthority> authorites;

//	ROLE_ADMIN, ROLE_USER, ROLE_GUEST

		public UserInfoUserDetails(MyUsers myUsers) {
			userName = myUsers.getUserName();
			password = myUsers.getPassword();
			authorites = Arrays.stream(myUsers.getRole().split(",")).map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorites;
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public String getUsername() {
			return userName;
		}
}