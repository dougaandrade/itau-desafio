package com.itau.itau.mapper;

import org.springframework.stereotype.Component;

import com.itau.itau.dto.EstatisticaDTO;
import com.itau.itau.dto.request.EstatisticaRequest;
import com.itau.itau.dto.response.EstatisticaResponse;

@Component
public class EstatisticaMapper {

  public EstatisticaDTO mapToResquest(EstatisticaRequest request) {
    return EstatisticaDTO.builder()
        .count(request.count())
        .avg(request.avg())
        .max(request.max())
        .min(request.min())
        .sum(request.sum())
        .build();
  }

  public EstatisticaDTO mapToResponse(EstatisticaResponse response) {
    return EstatisticaDTO.builder()
        .count(response.getCount())
        .avg(response.getAvg())
        .max(response.getMax())
        .min(response.getMin())
        .sum(response.getSum())
        .build();
  }

}
