package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interfaces.INivelHabilidadService;
import models.NivelHabilidad;
import repository.NivelHabilidadRepository;

@Service
public class NivelHabilidades implements INivelHabilidadService {

  @Autowired
  private NivelHabilidadRepository data;

  @Override
  public void delete(int id) {
    data.deleteById(id);
    
  }

  @Override
  public List<NivelHabilidad> getAll() {
    return data.findAll();
  }

  @Override
  public Optional<NivelHabilidad> getById(int id) {
    return data.findById(id);
  }

  @Override
  public int save(NivelHabilidad nivelHabilidad) {
    data.save(nivelHabilidad);
    return nivelHabilidad.getId();
  }

  @Override
  public int update(NivelHabilidad nivelHabilidad) {
    data.save(nivelHabilidad);
    return nivelHabilidad.getId();
  }
}
