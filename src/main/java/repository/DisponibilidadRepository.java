package repository;

import models.Disponibilidad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Integer> {

  @Query("select d from Disponibilidad d where d.idUsuario.id = :usuario")
  public List<Disponibilidad> findByUsuario(int usuario);
}
