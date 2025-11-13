package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interfaces.IOpinionService;
import models.Opinion;
import repository.OpinionRepository;

@Service
public class OpinionService implements IOpinionService {

  @Autowired
  private OpinionRepository data;

  @Override
  public void delete(int id) {
    data.deleteById(id);
  }

  @Override
  public List<Opinion> getAll() {
    return data.findAll();
  }

  @Override
  public Optional<Opinion> getById(int id) {
    return data.findById(id);
  }

  @Override
  public List<Opinion> getByUsuario(int idUsuario) {
    return data.findByUsuario(idUsuario);
  }

  @Override
  public int save(Opinion opinion) {
    try {
      if (opinion != null) {
        data.save(opinion);
        return 1;
      } else {
        return 0;
        
      }
    } catch (Exception e) {
      System.out.println("Error al guardar la opinion: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public int update(Opinion opinion) {
    try {
      if (opinion != null) {
        data.save(opinion);
        return 1;
      } else {
        return 0;
      }
    } catch (Exception e) {
      System.out.println("Error al actualizar la opinion: " + e.getMessage());
      return 0;
    }
  }

}
