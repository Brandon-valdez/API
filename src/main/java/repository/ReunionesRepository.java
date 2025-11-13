package repository;

import models.Reunione;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReunionesRepository extends JpaRepository<Reunione, Integer> {

  @Query("select r from Reunione r where r.idUsuario1.id = :usuario or r.idUsuario2.id = :usuario")
  public List<Reunione> findByUsuario(int usuario);
}
