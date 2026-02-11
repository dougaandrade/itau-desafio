package com.itau.itau.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itau.itau.model.TransacaoModel;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoModel, Long> {

  List<TransacaoModel> findByUsuarioId(Long userId);

}
