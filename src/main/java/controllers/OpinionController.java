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

import interfaces.IOpinionService;
import models.Opinion;
import models.Usuario;
import repository.UsuarioRepository;

@RestController
@RequestMapping("/api/opiniones")
@CrossOrigin(origins = "*")
public class OpinionController {

  @Autowired
  private IOpinionService service;

  @Autowired
  private UsuarioRepository usuarioRepository;


  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Integer id) {
    try {
      Opinion opinion = service.getById(id)
          .orElseThrow(() -> new Exception("Opinión no encontrada con ID: " + id));
      return ResponseEntity.ok(opinion);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }

  @GetMapping("/byUsuario")
  public ResponseEntity<List<Opinion>> getByUsuario(@RequestParam Integer id) {
    return ResponseEntity.ok(service.getByUsuario(id));
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(
      @RequestParam("idAutor") Integer idAutor,
      @RequestParam("idReceptor") Integer idReceptor,
      @RequestParam("opinion") String opinionText,
      @RequestParam(value = "valorReunion", required = false) Float valorReunion) {
    try {
      // Buscar los usuarios
      Usuario autor = usuarioRepository.findById(idAutor)
          .orElseThrow(() -> new Exception("Usuario autor no encontrado"));
      Usuario receptor = usuarioRepository.findById(idReceptor)
          .orElseThrow(() -> new Exception("Usuario receptor no encontrado"));

      // Crear la opinión
      Opinion opinion = new Opinion();
      opinion.setIdAutor(autor);
      opinion.setIdReceptor(receptor);
      opinion.setOpinion(opinionText);
      opinion.setValorReunion(valorReunion);

      int result = service.save(opinion);
      if (result == 1) {
        return ResponseEntity.ok().body("Opinión guardada exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al guardar la opinión");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(
      @PathVariable Integer id,
      @RequestParam(value = "idAutor", required = false) Integer idAutor,
      @RequestParam(value = "idReceptor", required = false) Integer idReceptor,
      @RequestParam(value = "opinion", required = false) String opinionText,
      @RequestParam(value = "valorReunion", required = false) Float valorReunion) {
    try {
      // Buscar la opinión existente
      Opinion opinion = service.getById(id)
          .orElseThrow(() -> new Exception("Opinión no encontrada"));

      // Actualizar solo los campos proporcionados
      if (idAutor != null) {
        Usuario autor = usuarioRepository.findById(idAutor)
            .orElseThrow(() -> new Exception("Usuario autor no encontrado"));
        opinion.setIdAutor(autor);
      }

      if (idReceptor != null) {
        Usuario receptor = usuarioRepository.findById(idReceptor)
            .orElseThrow(() -> new Exception("Usuario receptor no encontrado"));
        opinion.setIdReceptor(receptor);
      }

      if (opinionText != null) {
        opinion.setOpinion(opinionText);
      }

      if (valorReunion != null) {
        opinion.setValorReunion(valorReunion);
      }

      int result = service.update(opinion);
      if (result == 1) {
        return ResponseEntity.ok().body("Opinión actualizada exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al actualizar la opinión");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    try {
      // Verificar que la opinión existe
      service.getById(id)
          .orElseThrow(() -> new Exception("Opinión no encontrada con ID: " + id));

      service.delete(id);
      return ResponseEntity.ok().body("Opinión eliminada exitosamente");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

}
