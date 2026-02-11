package com.itau.itau.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.itau.itau.exception.UserNotFoundException;
import com.itau.itau.model.UserModel;
import com.itau.itau.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationUtil {

  private final UserRepository userRepository;

  public UserModel getAuthenticatedUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByUsername(username)
        .orElseThrow(() -> {
          log.warn("Authenticated user not found in database: {}", username);
          return new UserNotFoundException(username);
        });
  }
}
