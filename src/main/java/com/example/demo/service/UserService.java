package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    User findById(Long id);

    List<User> findAll();

    void saveUser(User user);

    void editUser(User user);

    void deleteById(Long id);

    Set<Role> findAllRoles();
}
