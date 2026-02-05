package com.itau.itau.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.itau.dto.AuthDTO;
import com.itau.itau.dto.response.LoginResponse;
import com.itau.itau.service.TokenService;
import com.itau.itau.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserService userService;

  @PostMapping()
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthDTO auth) {
    try {
      var usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(auth.username(), auth.password());
      var authentication = this.authenticationManager.authenticate(usernamePasswordAuthToken);
      var token = tokenService.generateToken(authentication);
      return ResponseEntity.ok(new LoginResponse(token));

    } catch (RuntimeException e) {
      log.error("Erro ao autenticar usuário: {}", e.getMessage());
      return ResponseEntity.status(401).build();
    }

  }

  @PostMapping("/create_user")
  public ResponseEntity<LoginResponse> createUser(@RequestBody @Valid AuthDTO auth) {
    try {
      log.info("Requisição para criação de usuário: {}", auth.username());

      var role = auth.role() != null ? auth.role() : com.itau.itau.enums.RoleEnum.ROLE_USER;
      userService.createUser(auth.username(), auth.password(), role);

      var usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(auth.username(), auth.password());
      var authentication = this.authenticationManager.authenticate(usernamePasswordAuthToken);
      var token = tokenService.generateToken(authentication);

      log.info("Usuário {} criado com sucesso", auth.username());
      return ResponseEntity.ok(new LoginResponse(token));
    } catch (RuntimeException e) {
      log.error("Erro ao criar usuário: {}", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

}
