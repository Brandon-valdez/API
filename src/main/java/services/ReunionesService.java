package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interfaces.IReunionesService;
import models.Reunione;
import repository.ReunionesRepository;

@Service
public class ReunionesService implements IReunionesService {

  @Autowired
  private ReunionesRepository data;

  @Override
  public void delete(int id) {
    data.deleteById(id);
  }

  @Override
  public List<Reunione> getAll() {
    return data.findAll();
  }

  @Override
  public Optional<Reunione> getById(int id) {
    return data.findById(id);
  }

  @Override
  public List<Reunione> getByUsuario(int idUsuario) {
    return data.findByUsuario(idUsuario);
  }

  @Override
  public int save(Reunione reunion) {
    try {
      if (reunion != null) {
        data.save(reunion);
        return 1;
      } else {
        return 0;

      }
    } catch (Exception e) {
      System.out.println("Error al guardar la reunion: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public int update(Reunione reunion) {
    try {
      if (reunion != null) {
        data.save(reunion);
        return 1;
      } else {
        return 0;
      }
    } catch (Exception e) {
      System.out.println("Error al actualizar la reunion: " + e.getMessage());
      return 0;
    }
  }

}
