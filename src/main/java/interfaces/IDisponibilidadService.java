package interfaces;

import java.util.List;
import java.util.Optional;

import models.Disponibilidad;

public interface IDisponibilidadService {
  public List<Disponibilidad> getAll();
  public Optional<Disponibilidad> getById(int id);
  public List<Disponibilidad> getByUsuario(int idUsuario);
  public int save(Disponibilidad disponibilidad);
  public int update(Disponibilidad disponibilidad);
  public void delete(int id);

}
