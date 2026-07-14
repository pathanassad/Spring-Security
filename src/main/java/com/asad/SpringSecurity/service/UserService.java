package com.asad.SpringSecurity.service;

import com.asad.SpringSecurity.model.Users;
import com.asad.SpringSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);

    }
}
