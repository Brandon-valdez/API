package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interfaces.IHabilidadesService;
import models.Habilidade;
import repository.HabilidadesRepository;

@Service
public class HabilidadesService implements IHabilidadesService{

  @Autowired
  private HabilidadesRepository data;

  @Override
  public void delete(int id) {
    data.deleteById(id);
    
  }

  @Override
  public List<Habilidade> getAll() {
    return data.findAll();
  }

  @Override
  public Optional<Habilidade> getById(int id) {
    return data.findById(id);
  }

  @Override
  public List<Habilidade> getByUsuario(int idUsuario) {
    return data.findByUsuario(idUsuario);
  }

  @Override
  public int save(Habilidade habilidade) {
    try {
      if (habilidade != null) {
        data.save(habilidade);
        return 1;
      } else {
        return 0;
        
      }
    } catch (Exception e) {
      System.out.println("Error al guardar la habilidade: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public int update(Habilidade habilidade) {
    try {
      if (habilidade != null) {
        data.save(habilidade);
        return 1;
      } else {
        return 0;
      }
    } catch (Exception e) {
      System.out.println("Error al actualizar la habilidade: " + e.getMessage());
      return 0;
    }
  }
}


