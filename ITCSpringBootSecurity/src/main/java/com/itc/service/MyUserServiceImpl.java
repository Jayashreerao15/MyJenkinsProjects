package com.itc.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itc.exception.UsernameNotFoundException;
import com.itc.model.MyUsers;
import com.itc.repository.MyUserRepo;

@Service
public class MyUserServiceImpl implements MyUserService {

    @Autowired
    MyUserRepo repo;
    
    @Autowired
    PasswordEncoder encoder;
    
    @Override
    public MyUsers addUser(MyUsers user) {
        user.setPassword(encoder.encode(user.getPassword()));
        MyUsers saved = repo.save(user);
        return saved;
    }

    @Override
    public String generateTemporaryPassword(String username) throws UsernameNotFoundException {
        MyUsers user = repo.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        
        String tempPassword = generateRandomPassword();
        user.setPassword(encoder.encode(tempPassword));
        repo.save(user);
        
        return tempPassword;
    }

    @Override
    public void resetPassword(String username, String oldPassword, String newPassword) throws UsernameNotFoundException {
        MyUsers user = repo.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        
        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new UsernameNotFoundException("Old password is incorrect");
        }

        user.setPassword(encoder.encode(newPassword));
        repo.save(user);
    }

    private String generateRandomPassword() {
        int length = 10;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}