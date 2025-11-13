package controllers;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import interfaces.IReunionesService;
import models.EstadoReunion;
import models.Reunione;
import models.Usuario;
import repository.EstadoReunionRepository;
import repository.UsuarioRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/reuniones")
@CrossOrigin(origins = "*")
public class ReunionesController {

  @Autowired
  private IReunionesService service;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private EstadoReunionRepository estadoReunionRepository;

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Integer id) {
    try {
      Reunione reunione = service.getById(id)
          .orElseThrow(() -> new Exception("Reunión no encontrada con ID: " + id));
      return ResponseEntity.ok(reunione);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }

  @GetMapping("/byUsuario")
  public ResponseEntity<List<Reunione>> reuniones(@RequestParam Integer id) {
    return ResponseEntity.ok(service.getByUsuario(id));
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(
      @RequestParam("idUsuario1") Integer idUsuario1,
      @RequestParam("idUsuario2") Integer idUsuario2,
      @RequestParam("idEstadoR") Integer idEstadoR,
      @RequestParam("fechaHora") String fechaHora,
      @RequestParam("duracion") String duracion) {
    try {
      // Buscar los usuarios
      Usuario usuario1 = usuarioRepository.findById(idUsuario1)
          .orElseThrow(() -> new Exception("Usuario 1 no encontrado"));
      Usuario usuario2 = usuarioRepository.findById(idUsuario2)
          .orElseThrow(() -> new Exception("Usuario 2 no encontrado"));
      EstadoReunion estadoReunion = estadoReunionRepository.findById(idEstadoR)
          .orElseThrow(() -> new Exception("Estado de reunión no encontrado"));

      // Crear la reunión
      Reunione reunione = new Reunione();
      reunione.setIdUsuario1(usuario1);
      reunione.setIdUsuario2(usuario2);
      reunione.setIdEstadoR(estadoReunion);

      // Convertir fecha-hora de String a Instant (formato esperado: ISO-8601, ej:
      // "2025-11-11T10:30:00Z")
      reunione.setFechaReunion(Instant.parse(fechaHora));

      // Convertir duración de String a LocalTime (formato esperado: "HH:mm:ss")
      reunione.setDuracionReunion(LocalTime.parse(duracion));

      int result = service.save(reunione);
      if (result == 1) {
        return ResponseEntity.ok().body("Reunión guardada exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al guardar la reunión");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(
      @PathVariable Integer id,
      @RequestParam(value = "idUsuario1", required = false) Integer idUsuario1,
      @RequestParam(value = "idUsuario2", required = false) Integer idUsuario2,
      @RequestParam(value = "idEstadoR", required = false) Integer idEstadoR,
      @RequestParam(value = "fechaHora", required = false) String fechaHora,
      @RequestParam(value = "duracion", required = false) String duracion) {
    try {
      // Buscar la reunión existente
      Reunione reunione = service.getById(id)
          .orElseThrow(() -> new Exception("Reunión no encontrada"));

      // Actualizar solo los campos proporcionados
      if (idUsuario1 != null) {
        Usuario usuario1 = usuarioRepository.findById(idUsuario1)
            .orElseThrow(() -> new Exception("Usuario 1 no encontrado"));
        reunione.setIdUsuario1(usuario1);
      }

      if (idUsuario2 != null) {
        Usuario usuario2 = usuarioRepository.findById(idUsuario2)
            .orElseThrow(() -> new Exception("Usuario 2 no encontrado"));
        reunione.setIdUsuario2(usuario2);
      }

      if (idEstadoR != null) {
        EstadoReunion estadoReunion = estadoReunionRepository.findById(idEstadoR)
            .orElseThrow(() -> new Exception("Estado de reunión no encontrado"));
        reunione.setIdEstadoR(estadoReunion);
      }

      if (fechaHora != null) {
        reunione.setFechaReunion(Instant.parse(fechaHora));
      }

      if (duracion != null) {
        reunione.setDuracionReunion(LocalTime.parse(duracion));
      }

      int result = service.update(reunione);
      if (result == 1) {
        return ResponseEntity.ok().body("Reunión actualizada exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al actualizar la reunión");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    try {
      // Verificar que la reunión existe
      service.getById(id)
          .orElseThrow(() -> new Exception("Reunión no encontrada con ID: " + id));

      service.delete(id);
      return ResponseEntity.ok().body("Reunión eliminada exitosamente");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

}