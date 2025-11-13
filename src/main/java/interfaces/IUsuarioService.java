package interfaces;

import models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
  public List<Usuario> getall();
  public Optional<Usuario> getbyid(int id);
  public Optional<Usuario> getbyEmail(String email);
  public int save(Usuario usuario);
  public int update(Usuario usuario);
  public void delete(int id);
}
