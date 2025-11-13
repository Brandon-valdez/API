package interfaces;

import java.util.List;
import java.util.Optional;

import models.Habilidade;


public interface IHabilidadesService {
  public List<Habilidade> getAll();
  public Optional<Habilidade> getById(int id);
  public List<Habilidade> getByUsuario(int idUsuario);
  public int save(Habilidade habilidade);
  public int update(Habilidade habilidade);
  public void delete(int id);
}
