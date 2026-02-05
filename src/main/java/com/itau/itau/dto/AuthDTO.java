package com.itau.itau.dto;

import com.itau.itau.enums.RoleEnum;

public record AuthDTO(String username, String password, RoleEnum role) {

}
