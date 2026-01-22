package com.itau.itau.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itau.itau.request.TransacaoRequest;

@Repository
public class TransacaoRepository {

  List<TransacaoRequest> transacaoList = new ArrayList<>();

  public void saveData(TransacaoRequest transacaoRequest) {
    transacaoList.add(transacaoRequest);

  }

  public void deleteData() {
    transacaoList.clear();
  }

  public void clearData() {
  }

}
