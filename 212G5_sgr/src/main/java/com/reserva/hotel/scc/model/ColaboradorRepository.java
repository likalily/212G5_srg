package com.reserva.hotel.scc.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ColaboradorRepository extends CrudRepository<Colaborador, Long> {
public Colaborador findByCpf(@Param("cpf") String cpf);
}