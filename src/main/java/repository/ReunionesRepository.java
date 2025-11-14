package repository;

import models.Reunione;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReunionesRepository extends JpaRepository<Reunione, Integer> {

  @Query("select r from Reunione r join fetch r.idUsuario1 u1 join fetch r.idUsuario2 u2 join fetch r.idEstadoR e where u1.id = :usuario or u2.id = :usuario")
  public List<Reunione> findByUsuario(int usuario);
}
