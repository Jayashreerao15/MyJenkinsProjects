package com.itc.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itc.exception.UsernameNotFoundException;
import com.itc.model.AuthRequest;
import com.itc.model.MyUsers;
import com.itc.service.JwtService;
import com.itc.service.MyUserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	MyUserService serv;

	@Autowired
	JwtService jwtService;

	@PostMapping("/new")
	public ResponseEntity<?> registerUser(@RequestBody MyUsers users) {
		MyUsers user = serv.addUser(users);
		return new ResponseEntity<MyUsers>(user, HttpStatus.OK);
	}

	@PostMapping("/authenticate")
	public String authenticate(@RequestBody AuthRequest authRequest) throws UsernameNotFoundException {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		if (authenticate.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("invalid Credentials");
		}
	}

	@PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        try {
            String tempPassword = serv.generateTemporaryPassword(username);
            return new ResponseEntity<>("Temporary password: " + tempPassword, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        String confirmPassword = request.get("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            return new ResponseEntity<>("New passwords do not match", HttpStatus.BAD_REQUEST);
        }

        try {
            serv.resetPassword(username, oldPassword, newPassword);
            return new ResponseEntity<>("Password successfully reset", HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}