package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interfaces.IUsuarioService;
import models.Usuario;
import repository.UsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService {

  @Autowired
  private UsuarioRepository data;

  @Override
  public List<Usuario> getall() {
    return data.findAll();
  }

  @Override
  public Optional<Usuario> getbyid(int id) {
    return data.findById(id);
    
  }

  @Override
  public Optional<Usuario> getbyEmail(String email) {
    return Optional.ofNullable(data.findByEmail(email));
  }

  @Override
  public void delete(int id) {
    data.deleteById(id);
    
  }

  @Override
  public int save(Usuario usuario) {
    try {
      data.save(usuario);
      return usuario.getId();
    } catch (Exception e) {
      System.out.println("Error al guardar el usuario: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public int update(Usuario usuario) {
    try {
      data.save(usuario);
      return usuario.getId();
    } catch (Exception e) {
      System.out.println("Error al actualizar el usuario: " + e.getMessage());
      return 0;
    }
  }

}
