package com.itc.service;

import com.itc.exception.UsernameNotFoundException;
import com.itc.model.MyUsers;

public interface MyUserService {
	MyUsers addUser(MyUsers user);

	String generateTemporaryPassword(String username) throws UsernameNotFoundException;

	void resetPassword(String username, String oldPassword, String newPassword) throws UsernameNotFoundException;
}
