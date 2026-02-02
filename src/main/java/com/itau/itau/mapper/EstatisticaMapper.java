package com.itau.itau.mapper;

import org.springframework.stereotype.Component;

import com.itau.itau.dto.EstatisticaDTO;
import com.itau.itau.model.EstatisticaModel;

@Component
public class EstatisticaMapper {

  public EstatisticaDTO mapToDTO(long count, Double avg, Double max, Double min, Double sum) {
    return EstatisticaDTO.builder()
        .count(count)
        .avg(avg)
        .max(max)
        .min(min)
        .sum(sum)
        .build();
  }

  public EstatisticaDTO mapToRequestModel(EstatisticaModel model) {
    return EstatisticaDTO.builder()
        .count(model.getCount())
        .avg(model.getAvg())
        .max(model.getMax())
        .min(model.getMin())
        .sum(model.getSum())
        .build();
  }

}
