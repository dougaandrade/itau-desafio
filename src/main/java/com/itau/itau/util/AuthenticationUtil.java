package com.itau.itau.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.itau.itau.exception.UserNotFoundException;
import com.itau.itau.model.UserModel;
import com.itau.itau.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthenticationUtil {

  private final UserRepository userRepository;

  public UserModel getAuthenticatedUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }
}
