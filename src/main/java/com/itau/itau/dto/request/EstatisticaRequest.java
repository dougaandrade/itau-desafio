package com.itau.itau.dto.request;

import lombok.Builder;

@Builder
public record EstatisticaRequest(
    Long count,
    Double avg,
    Double max,
    Double min,
    Double sum) {

}
