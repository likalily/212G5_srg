package com.reserva.hotel.scc.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartoRepository extends CrudRepository<Quarto, Long> {
	public Quarto findByIdQuarto(@Param("idQuarto") Long idQuarto);
}