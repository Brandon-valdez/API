package controllers;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import interfaces.IDisponibilidadService;
import models.Disponibilidad;
import models.Usuario;
import repository.UsuarioRepository;

@RestController
@RequestMapping("/api/disponibilidad")
@CrossOrigin(origins = "*")
public class DisponibilidadController {

  @Autowired
  private IDisponibilidadService service;

  @Autowired
  private UsuarioRepository usuarioRepository;


  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Integer id) {
    try {
      Disponibilidad disponibilidad = service.getById(id)
          .orElseThrow(() -> new Exception("Disponibilidad no encontrada con ID: " + id));
      return ResponseEntity.ok(disponibilidad);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }

  @GetMapping("/byUsuario")
  public ResponseEntity<List<Disponibilidad>> getByUsuario(@RequestParam Integer id) {
    return ResponseEntity.ok(service.getByUsuario(id));
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(
      @RequestParam("idUsuario") Integer idUsuario,
      @RequestParam("diaSemana") String diaSemana,
      @RequestParam("horaInicio") String horaInicio,
      @RequestParam("horaFin") String horaFin,
      @RequestParam(value = "disponible", required = false, defaultValue = "true") Boolean disponible) {
    try {
      // Buscar el usuario
      Usuario usuario = usuarioRepository.findById(idUsuario)
          .orElseThrow(() -> new Exception("Usuario no encontrado"));

      // Crear la disponibilidad
      Disponibilidad disponibilidad = new Disponibilidad();
      disponibilidad.setIdUsuario(usuario);
      disponibilidad.setDiaSemana(diaSemana);
      disponibilidad.setHoraInicio(LocalTime.parse(horaInicio));
      disponibilidad.setHoraFin(LocalTime.parse(horaFin));
      disponibilidad.setDisponible(disponible);

      int result = service.save(disponibilidad);
      if (result == 1) {
        return ResponseEntity.ok().body("Disponibilidad guardada exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al guardar la disponibilidad");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(
      @PathVariable Integer id,
      @RequestParam(value = "idUsuario", required = false) Integer idUsuario,
      @RequestParam(value = "diaSemana", required = false) String diaSemana,
      @RequestParam(value = "horaInicio", required = false) String horaInicio,
      @RequestParam(value = "horaFin", required = false) String horaFin,
      @RequestParam(value = "disponible", required = false) Boolean disponible) {
    try {
      // Buscar la disponibilidad existente
      Disponibilidad disponibilidad = service.getById(id)
          .orElseThrow(() -> new Exception("Disponibilidad no encontrada"));

      // Actualizar solo los campos proporcionados
      if (idUsuario != null) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new Exception("Usuario no encontrado"));
        disponibilidad.setIdUsuario(usuario);
      }

      if (diaSemana != null) {
        disponibilidad.setDiaSemana(diaSemana);
      }

      if (horaInicio != null) {
        disponibilidad.setHoraInicio(LocalTime.parse(horaInicio));
      }

      if (horaFin != null) {
        disponibilidad.setHoraFin(LocalTime.parse(horaFin));
      }

      if (disponible != null) {
        disponibilidad.setDisponible(disponible);
      }

      int result = service.update(disponibilidad);
      if (result == 1) {
        return ResponseEntity.ok().body("Disponibilidad actualizada exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al actualizar la disponibilidad");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    try {
      // Verificar que la disponibilidad existe
      service.getById(id)
          .orElseThrow(() -> new Exception("Disponibilidad no encontrada con ID: " + id));

      service.delete(id);
      return ResponseEntity.ok().body("Disponibilidad eliminada exitosamente");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

}
