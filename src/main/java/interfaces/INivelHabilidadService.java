package interfaces;

import java.util.List;
import java.util.Optional;

import models.NivelHabilidad;

public interface INivelHabilidadService {
  public List<NivelHabilidad> getAll();
  public Optional<NivelHabilidad> getById(int id);
  public int save(NivelHabilidad nivelHabilidad);
  public int update(NivelHabilidad nivelHabilidad);
  public void delete(int id);
}
