package interfaces;

import java.util.List;
import java.util.Optional;

import models.CategoriaHabilidad;

public interface ICategoriaHabilidadService {
  public List<CategoriaHabilidad> getAll();
  public Optional<CategoriaHabilidad> getById(int id);
  public int save(CategoriaHabilidad categoriaHabilidad);
  public int update(CategoriaHabilidad categoriaHabilidad);
  public void delete(int id);

}
