package interfaces;

import models.EstadoReunion;

import java.util.List;
import java.util.Optional;

public interface IEstadoReunionService {
  public List<EstadoReunion> getAll();
  public Optional<EstadoReunion> findById(Integer id);
  public int save(EstadoReunion estadoReunion);
  public int update(EstadoReunion estadoReunion);
  public void delete(Integer id);

}
