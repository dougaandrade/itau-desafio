package com.itau.itau.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleEnum {
  ROLE_USER("U"),
  ROLE_ADMIN("A");

  private final String value;

  RoleEnum(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static RoleEnum fromValue(String value) {
    if (value == null) {
      return ROLE_USER;
    }

    // Aceita "U" ou "ROLE_USER"
    for (RoleEnum role : RoleEnum.values()) {
      if (role.value.equalsIgnoreCase(value) || role.name().equalsIgnoreCase(value)) {
        return role;
      }
    }

    return ROLE_USER; // Default
  }

}