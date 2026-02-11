package com.itau.itau.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itau.itau.model.TransacaoModel;
import com.itau.itau.model.UserModel;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoModel, Long> {

  List<TransacaoModel> findByUsuario(UserModel usuario);

  Long countByUsuarioAndDataHoraAfter(UserModel usuario, LocalDateTime dataHora);

}
