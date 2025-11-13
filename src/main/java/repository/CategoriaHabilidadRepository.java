package repository;

import models.CategoriaHabilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaHabilidadRepository extends JpaRepository<CategoriaHabilidad, Integer> {
}
