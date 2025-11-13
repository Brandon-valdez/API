package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario", nullable = false)
  private Integer id;

  @Column(name = "nombre", nullable = false, length = 100)
  private String nombre;

  @Column(name = "apellido", nullable = false, length = 100)
  private String apellido;

  @Column(name = "email", nullable = false, length = 150, unique = true)
  private String email;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Column(name = "foto_perfil", length = 255)
  private String fotoPerfil;

  @Lob
  @Column(name = "biografia")
  private String biografia;

  @ColumnDefault("current_timestamp()")
  @Column(name = "fecha_registro")
  private Instant fechaRegistro;

  @OneToMany(mappedBy = "idUsuario")
  @JsonBackReference
  private Set<Habilidade> habilidades = new LinkedHashSet<>();

  @OneToMany(mappedBy = "idUsuario")
  @JsonBackReference
  private Set<Disponibilidad> disponibilidades = new LinkedHashSet<>();

  @OneToMany(mappedBy = "idUsuario1")
  @JsonBackReference
  private Set<Reunione> reunionesComoUsuario1 = new LinkedHashSet<>();

  @OneToMany(mappedBy = "idUsuario2")
  @JsonBackReference
  private Set<Reunione> reunionesComoUsuario2 = new LinkedHashSet<>();

  @OneToMany(mappedBy = "idAutor")
  @JsonBackReference
  private Set<Opinion> opinionesComoAutor = new LinkedHashSet<>();

  @OneToMany(mappedBy = "idReceptor")
  @JsonBackReference
  private Set<Opinion> opinionesComoReceptor = new LinkedHashSet<>();

}