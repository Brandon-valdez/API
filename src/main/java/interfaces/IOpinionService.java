package interfaces;

import java.util.List;
import java.util.Optional;

import models.Opinion;

public interface IOpinionService {
  public List<Opinion> getAll();
  public Optional<Opinion> getById(int id);
  public List<Opinion> getByUsuario(int idUsuario);
  public int save(Opinion opinion);
  public int update(Opinion opinion);
  public void delete(int id);

}
