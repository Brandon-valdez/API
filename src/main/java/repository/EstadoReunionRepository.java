package repository;

import models.EstadoReunion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoReunionRepository extends JpaRepository<EstadoReunion, Integer> {


}
