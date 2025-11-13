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

import interfaces.ICategoriaHabilidadService;
import models.CategoriaHabilidad;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaHabilidadController {

  @Autowired
  private ICategoriaHabilidadService service;

  @GetMapping("/all")
  public ResponseEntity<List<CategoriaHabilidad>> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Integer id) {
    try {
      CategoriaHabilidad categoria = service.getById(id)
          .orElseThrow(() -> new Exception("Categoría de habilidad no encontrada con ID: " + id));
      return ResponseEntity.ok(categoria);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(@RequestParam("nombreCategoria") String nombreCategoria) {
    try {
      CategoriaHabilidad categoria = new CategoriaHabilidad();
      categoria.setNombreCategoria(nombreCategoria);

      int result = service.save(categoria);
      if (result > 0) {
        return ResponseEntity.ok().body("Categoría de habilidad guardada exitosamente con ID: " + result);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Error al guardar la categoría de habilidad");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error: " + e.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(
      @PathVariable Integer id,
      @RequestParam(value = "nombreCategoria", required = false) String nombreCategoria) {
    try {
      CategoriaHabilidad categoria = service.getById(id)
          .orElseThrow(() -> new Exception("Categoría de habilidad no encontrada con ID: " + id));

      if (nombreCategoria != null && !nombreCategoria.isEmpty()) {
        categoria.setNombreCategoria(nombreCategoria);
      }

      int result = service.update(categoria);
      if (result > 0) {
        return ResponseEntity.ok().body("Categoría de habilidad actualizada exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Error al actualizar la categoría de habilidad");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    try {
      CategoriaHabilidad categoria = service.getById(id)
          .orElseThrow(() -> new Exception("Categoría de habilidad no encontrada con ID: " + id));

      service.delete(id);
      return ResponseEntity.ok().body("Categoría de habilidad eliminada exitosamente");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }
}

