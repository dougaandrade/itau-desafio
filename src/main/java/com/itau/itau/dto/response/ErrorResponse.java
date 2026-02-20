package com.itau.itau.dto.response;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, Integer status, String error, String message, String path) {
}
