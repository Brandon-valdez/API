package controllers;

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

import interfaces.INivelHabilidadService;
import models.NivelHabilidad;

@RestController
@RequestMapping("/api/niveles")
@CrossOrigin(origins = "*")
public class NivelHabilidadController {

  @Autowired
  private INivelHabilidadService service;

  @GetMapping("/all")
  public ResponseEntity<List<NivelHabilidad>> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Integer id) {
    try {
      NivelHabilidad nivel = service.getById(id)
          .orElseThrow(() -> new Exception("Nivel de habilidad no encontrado con ID: " + id));
      return ResponseEntity.ok(nivel);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(@RequestParam("nomNivel") String nomNivel) {
    try {
      NivelHabilidad nivel = new NivelHabilidad();
      nivel.setNomNivel(nomNivel);

      int result = service.save(nivel);
      if (result > 0) {
        return ResponseEntity.ok().body("Nivel de habilidad guardado exitosamente con ID: " + result);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Error al guardar el nivel de habilidad");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error: " + e.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(
      @PathVariable Integer id,
      @RequestParam(value = "nomNivel", required = false) String nomNivel) {
    try {
      NivelHabilidad nivel = service.getById(id)
          .orElseThrow(() -> new Exception("Nivel de habilidad no encontrado con ID: " + id));

      if (nomNivel != null && !nomNivel.isEmpty()) {
        nivel.setNomNivel(nomNivel);
      }

      int result = service.update(nivel);
      if (result > 0) {
        return ResponseEntity.ok().body("Nivel de habilidad actualizado exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Error al actualizar el nivel de habilidad");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    try {
      NivelHabilidad nivel = service.getById(id)
          .orElseThrow(() -> new Exception("Nivel de habilidad no encontrado con ID: " + id));

      service.delete(id);
      return ResponseEntity.ok().body("Nivel de habilidad eliminado exitosamente");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }
}

