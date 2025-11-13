package interfaces;

import java.util.List;
import java.util.Optional;

import models.Reunione;

public interface IReunionesService {
  public List<Reunione> getAll();
  public Optional<Reunione> getById(int id);
  public List<Reunione> getByUsuario(int idUsuario);
  public int save(Reunione reunion);
  public int update(Reunione reunion);
  public void delete(int id);
}
