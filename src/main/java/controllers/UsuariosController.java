package controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import interfaces.IUsuarioService;
import models.Usuario;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuariosController {

  @Value("${file.upload-dir}")
  private String uploadDir;

  @Value("${file.base-url}")
  private String fileBaseUrl;

  @Autowired
  private IUsuarioService service;

  @GetMapping("/all")
  public ResponseEntity<List<Usuario>> getAll() {
    return ResponseEntity.ok(service.getall());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Integer id) {
    try {
      Usuario usuario = service.getbyid(id)
          .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + id));
      return ResponseEntity.ok(usuario);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }

  @GetMapping("/email")
  public ResponseEntity<?> getByEmail(@RequestParam String email) {
    try {
      Usuario usuario = service.getbyEmail(email)
          .orElseThrow(() -> new Exception("Usuario no encontrado con email: " + email));
      return ResponseEntity.ok(usuario);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Error: " + e.getMessage());
    }
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(
          @RequestHeader(value = "Authorization", required = false) String authHeader,
          @RequestParam("nombre") String nombre,
          @RequestParam("apellido") String apellido,
          @RequestParam("email") String email,
          @RequestParam("password") String password,
          @RequestParam(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
          @RequestParam(value = "biografia", required = false) String biografia) {
    try {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.replace("Bearer ", "");
        // TODO: verificar el token si usas Firebase Admin SDK
      }

      // Crear el usuario
      Usuario usuario = new Usuario();
      usuario.setNombre(nombre);
      usuario.setApellido(apellido);
      usuario.setEmail(email);
      usuario.setPassword(password);
      usuario.setBiografia(biografia);
      usuario.setFechaRegistro(Instant.now());

      // Si viene un archivo de foto, guardarlo y asignar la URL
      if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
        String fileName = System.currentTimeMillis() + "_" + fotoPerfil.getOriginalFilename();
        Path path = Paths.get(uploadDir + "/" + fileName);
        if (path.getParent() != null) {
          Files.createDirectories(path.getParent());
        }
        Files.copy(fotoPerfil.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        usuario.setFotoPerfil((fileBaseUrl.endsWith("/") ? fileBaseUrl : fileBaseUrl + "/") + fileName);
      }

      int result = service.save(usuario);
      if (result > 0) {
        return ResponseEntity.ok().body("Usuario guardado exitosamente con ID: " + result);
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al guardar el usuario");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(
      @PathVariable Integer id,
      @RequestParam(value = "nombre", required = false) String nombre,
      @RequestParam(value = "apellido", required = false) String apellido,
      @RequestParam(value = "email", required = false) String email,
      @RequestParam(value = "password", required = false) String password,
      @RequestParam(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
      @RequestParam(value = "biografia", required = false) String biografia) {
    try {
      // Buscar el usuario existente
      Usuario usuario = service.getbyid(id)
          .orElseThrow(() -> new Exception("Usuario no encontrado"));

      // Actualizar solo los campos proporcionados
      if (nombre != null) {
        usuario.setNombre(nombre);
      }

      if (apellido != null) {
        usuario.setApellido(apellido);
      }

      if (email != null) {
        usuario.setEmail(email);
      }

      if (password != null) {
        usuario.setPassword(password);
      }

      if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
        String fileName = System.currentTimeMillis() + "_" + fotoPerfil.getOriginalFilename();
        Path path = Paths.get(uploadDir + "/" + fileName);
        if (path.getParent() != null) {
          Files.createDirectories(path.getParent());
        }
        Files.copy(fotoPerfil.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        usuario.setFotoPerfil((fileBaseUrl.endsWith("/") ? fileBaseUrl : fileBaseUrl + "/") + fileName);
      }

      if (biografia != null) {
        usuario.setBiografia(biografia);
      }

      int result = service.update(usuario);
      if (result > 0) {
        return ResponseEntity.ok().body("Usuario actualizado exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error al actualizar el usuario");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    try {
      // Verificar que el usuario existe
      service.getbyid(id)
          .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + id));

      service.delete(id);
      return ResponseEntity.ok().body("Usuario eliminado exitosamente");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error: " + e.getMessage());
    }
  }

}
