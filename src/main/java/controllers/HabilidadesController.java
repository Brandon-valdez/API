package controllers;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;

import interfaces.IHabilidadesService;
import models.CategoriaHabilidad;
import models.Habilidade;
import models.NivelHabilidad;
import models.Usuario;
import repository.CategoriaHabilidadRepository;
import repository.NivelHabilidadRepository;
import repository.UsuarioRepository;

@RestController
@RequestMapping("/api/habilidades")
@CrossOrigin(origins = "*")
public class HabilidadesController {

  @Value("${file.upload-dir}")
  private String uploadDir;

  @Value("${file.base-url}")
  private String fileBaseUrl;

  @Autowired
  private IHabilidadesService service;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private CategoriaHabilidadRepository categoriaHabilidadRepository;

  @Autowired
  private NivelHabilidadRepository nivelHabilidadRepository;


  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Integer id) {
    try {
      Habilidade habilidad = service.getById(id)
          .orElseThrow(() -> new Exception("Habilidad no encontrada con ID: " + id));
      return ResponseEntity.ok(habilidad);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }

  @GetMapping("/byUsuario")
  public ResponseEntity<List<Habilidade>> getByUsuario(@RequestParam Integer id) {
    return ResponseEntity.ok(service.getByUsuario(id));
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(
      @RequestParam("idUsuario") Integer idUsuario,
      @RequestParam("idCategoriaHabilidad") Integer idCategoriaHabilidad,
      @RequestParam("idNivel") Integer idNivel,
      @RequestParam("nomHabilidad") String nomHabilidad,
      @RequestParam(value = "descripcionBreve", required = false) String descripcionBreve,
      @RequestParam(value = "fotoDiploma", required = false) MultipartFile fotoDiploma) {
    try {
      // Buscar las entidades relacionadas
      Usuario usuario = usuarioRepository.findById(idUsuario)
          .orElseThrow(() -> new Exception("Usuario no encontrado"));
      CategoriaHabilidad categoria = categoriaHabilidadRepository.findById(idCategoriaHabilidad)
          .orElseThrow(() -> new Exception("Categoría de habilidad no encontrada"));
      NivelHabilidad nivel = nivelHabilidadRepository.findById(idNivel)
          .orElseThrow(() -> new Exception("Nivel de habilidad no encontrado"));

      // Crear la habilidad
      Habilidade habilidad = new Habilidade();
      habilidad.setIdUsuario(usuario);
      habilidad.setIdCategoriaHabilidad(categoria);
      habilidad.setIdNivel(nivel);
      habilidad.setNomHabilidad(nomHabilidad);
      habilidad.setDescripcionBreve(descripcionBreve);

      // Si viene un archivo de diploma, guardarlo y asignar la URL
      if (fotoDiploma != null && !fotoDiploma.isEmpty()) {
        String fileName = System.currentTimeMillis() + "_" + fotoDiploma.getOriginalFilename();
        Path path = Paths.get(uploadDir + "/" + fileName);
        if (path.getParent() != null) {
          Files.createDirectories(path.getParent());
        }
        Files.copy(fotoDiploma.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        habilidad.setFotoDiploma((fileBaseUrl.endsWith("/") ? fileBaseUrl : fileBaseUrl + "/") + fileName);
      }

      int result = service.save(habilidad);
      if (result == 1) {
        return ResponseEntity.ok().body("Habilidad guardada exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al guardar la habilidad");
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
      @RequestParam(value = "idCategoriaHabilidad", required = false) Integer idCategoriaHabilidad,
      @RequestParam(value = "idNivel", required = false) Integer idNivel,
      @RequestParam(value = "nomHabilidad", required = false) String nomHabilidad,
      @RequestParam(value = "descripcionBreve", required = false) String descripcionBreve,
      @RequestParam(value = "fotoDiploma", required = false) MultipartFile fotoDiploma) {
    try {
      // Buscar la habilidad existente
      Habilidade habilidad = service.getById(id)
          .orElseThrow(() -> new Exception("Habilidad no encontrada"));

      // Actualizar solo los campos proporcionados
      if (idUsuario != null) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new Exception("Usuario no encontrado"));
        habilidad.setIdUsuario(usuario);
      }

      if (idCategoriaHabilidad != null) {
        CategoriaHabilidad categoria = categoriaHabilidadRepository.findById(idCategoriaHabilidad)
            .orElseThrow(() -> new Exception("Categoría de habilidad no encontrada"));
        habilidad.setIdCategoriaHabilidad(categoria);
      }

      if (idNivel != null) {
        NivelHabilidad nivel = nivelHabilidadRepository.findById(idNivel)
            .orElseThrow(() -> new Exception("Nivel de habilidad no encontrado"));
        habilidad.setIdNivel(nivel);
      }

      if (nomHabilidad != null) {
        habilidad.setNomHabilidad(nomHabilidad);
      }

      if (descripcionBreve != null) {
        habilidad.setDescripcionBreve(descripcionBreve);
      }

      if (fotoDiploma != null && !fotoDiploma.isEmpty()) {
        String fileName = System.currentTimeMillis() + "_" + fotoDiploma.getOriginalFilename();
        Path path = Paths.get(uploadDir + "/" + fileName);
        if (path.getParent() != null) {
          Files.createDirectories(path.getParent());
        }
        Files.copy(fotoDiploma.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        habilidad.setFotoDiploma((fileBaseUrl.endsWith("/") ? fileBaseUrl : fileBaseUrl + "/") + fileName);
      }

      int result = service.update(habilidad);
      if (result == 1) {
        return ResponseEntity.ok().body("Habilidad actualizada exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al actualizar la habilidad");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    try {
      // Verificar que la habilidad existe
      service.getById(id)
          .orElseThrow(() -> new Exception("Habilidad no encontrada con ID: " + id));

      service.delete(id);
      return ResponseEntity.ok().body("Habilidad eliminada exitosamente");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

}
