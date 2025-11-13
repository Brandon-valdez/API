package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interfaces.IEstadoReunionService;
import models.EstadoReunion;
import repository.EstadoReunionRepository;

@Service
public class EstadoReunionService implements IEstadoReunionService {

  @Autowired
  private EstadoReunionRepository data;

  /**
   * Busca un estado de reunion por su ID.
   * @param id El ID del estado de reunion.
   * @return Un Optional que contiene el estado de reunion si se encuentra, o vacío si no.
   */
  @Override
  public Optional<EstadoReunion> findById(Integer id) {
    return data.findById(id);
  }

  /**
   * Obtiene todos los estados de reunion.
   * @return Una lista de todos los estados de reunion.
   */
  @Override
  public List<EstadoReunion> getAll() {
    return data.findAll();
  }

  /**
   *  Guarda un nuevo estado de reunion.
   * @param estadoReunion El estado de reunion a guardar.
   * @return El ID del estado de reunion guardado, o 0 si hubo un error.
   */
  @Override
  public int save(EstadoReunion estadoReunion) {
    try {
      data.save(estadoReunion);
      return estadoReunion.getId();
    } catch (Exception e) {
      System.out.println("Error al guardar el estado de la reunion: " + e.getMessage());
      return 0;
    }
  }

  /**
   * Actualiza un estado de reunion existente.
   * @param estadoReunion El estado de reunion a actualizar.
   * @return El ID del estado de reunion actualizado, o 0 si hubo un error.
   */
  @Override
  public int update(EstadoReunion estadoReunion) {
    try {
      data.save(estadoReunion);
      return estadoReunion.getId();
    } catch (Exception e) {
      System.out.println("Error al actualizar el estado de la reunion: " + e.getMessage());
      return 0;
    }
  }

  /**
   * Elimina un estado de reunion por su ID.
   * @param id El ID del estado de reunion a eliminar.
   */
  @Override
  public void delete(Integer id) {
    try {
      data.deleteById(id);
    } catch (Exception e) {
      System.out.println("Error al eliminar el estado de la reunion: " + e.getMessage());
    }
  }

}
