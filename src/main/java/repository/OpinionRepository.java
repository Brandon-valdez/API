package repository;

import models.Opinion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Integer> {

  @Query("select op from Opinion op where op.idAutor.id = :usuario or op.idReceptor.id = :usuario")
  public List<Opinion> findByUsuario(int usuario);
}
