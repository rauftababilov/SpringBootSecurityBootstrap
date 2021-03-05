package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void editUser(User user) {
        if (user.getPassword().isEmpty()) {
            user.setPassword(userRepository.findByEmail(user.getEmail()).getPassword());
        } else {
            user.setPassword(user.getPassword());
        }
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Set<Role> findAllRoles() {
//        return (Set<Role>) roleRepository.findAll();
        Iterable<Role> iterable = roleRepository.findAll();
        Set<Role> set = new HashSet<>();
        iterable.forEach(role -> set.add(role));
        return set;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }
}
