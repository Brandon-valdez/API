package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interfaces.ICategoriaHabilidadService;
import models.CategoriaHabilidad;
import repository.CategoriaHabilidadRepository;

@Service
public class CategoriaHabilidadService implements ICategoriaHabilidadService {

  @Autowired
  private CategoriaHabilidadRepository data;

  @Override
  public void delete(int id) {
    data.deleteById(id);
  }

  @Override
  public List<CategoriaHabilidad> getAll() {
    return data.findAll();
  }

  @Override
  public Optional<CategoriaHabilidad> getById(int id) {
    return data.findById(id);
  }

  @Override
  public int save(CategoriaHabilidad categoriaHabilidad) {
    try {
      data.save(categoriaHabilidad);
      return categoriaHabilidad.getId();
    } catch (Exception e) {
      System.out.println("Error al guardar la categoria de habilidad: " + e.getMessage());
      return 0;
    }
  }

  @Override
  public int update(CategoriaHabilidad categoriaHabilidad) {
    try {
      data.save(categoriaHabilidad);
      return categoriaHabilidad.getId();
    } catch (Exception e) {
      System.out.println("Error al actualizar la categoria de habilidad: " + e.getMessage());
      return 0;
    }
  }
}
