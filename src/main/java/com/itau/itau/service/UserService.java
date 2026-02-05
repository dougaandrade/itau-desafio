package com.itau.itau.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itau.itau.enums.RoleEnum;
import com.itau.itau.model.UserModel;
import com.itau.itau.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
  }

  public UserModel createUser(String username, String password, RoleEnum role) {
    if (userRepository.existsByUsername(username)) {
      throw new RuntimeException("Usuário já existe: " + username);
    }

    var user = new UserModel();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setRole(role.name());

    return userRepository.save(user);
  }

}
