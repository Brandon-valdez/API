package repository;

import models.Habilidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabilidadesRepository extends JpaRepository<Habilidade, Integer> {

  @Query("select ab from Habilidade ab join fetch ab.idUsuario u join fetch ab.idCategoriaHabilidad c join fetch ab.idNivel n where u.id = :usuario")
  List<Habilidade> findByUsuario(@Param("usuario") Integer usuario);
}
