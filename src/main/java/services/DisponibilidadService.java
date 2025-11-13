package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interfaces.IDisponibilidadService;
import models.Disponibilidad;
import repository.DisponibilidadRepository;

@Service
public class DisponibilidadService implements IDisponibilidadService{

  @Autowired
  private DisponibilidadRepository data;

  @Override
  public void delete(int id) {
    data.deleteById(id);
  }

  @Override
  public List<Disponibilidad> getAll() {
    return data.findAll();
  }

  @Override
  public Optional<Disponibilidad> getById(int id) {
    return data.findById(id);
  }

  @Override
  public List<Disponibilidad> getByUsuario(int idUsuario) {
    return data.findByUsuario(idUsuario);
  }

  @Override
  public int save(Disponibilidad disponibilidad) {
    try {
      if (disponibilidad != null) {
        data.save(disponibilidad);
        return 1;
      } else {
        return 0;
      }
    } catch (Exception e) {
      System.out.println("Error al guardar la disponibilidad: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public int update(Disponibilidad disponibilidad) {
    try {
      if (disponibilidad != null) {
        data.save(disponibilidad);
        return 1;
      } else {
        return 0;
      }
    } catch (Exception e) {
      System.out.println("Error al actualizar la disponibilidad: " + e.getMessage());
      return 0;
    }
  }
}

