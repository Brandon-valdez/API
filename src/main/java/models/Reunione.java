package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.Instant;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "reuniones")
public class Reunione {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_reunion", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_usuario1", nullable = false)
  @JsonManagedReference
  private Usuario idUsuario1;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_usuario2", nullable = false)
  @JsonManagedReference
  private Usuario idUsuario2;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_estadoR", nullable = false)
  private EstadoReunion idEstadoR;

  @Column(name = "fecha_reunion", nullable = false)
  private Instant fechaReunion;

  @Column(name = "duracion_reunion")
  private LocalTime duracionReunion;

}