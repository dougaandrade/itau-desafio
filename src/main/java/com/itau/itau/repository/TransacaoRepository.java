package com.itau.itau.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itau.itau.model.TransacaoModel;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoModel, Long> {

}
