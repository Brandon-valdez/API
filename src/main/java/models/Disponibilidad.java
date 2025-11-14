package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "disponibilidad")
public class Disponibilidad {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_disponibilidad", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_usuario", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "habilidades", "disponibilidades", "reunionesComoUsuario1", "reunionesComoUsuario2", "opinionesComoAutor", "opinionesComoReceptor", "password"})
  private Usuario idUsuario;

  @Column(name = "dia_semana", nullable = false, length = 20)
  private String diaSemana;

  @Column(name = "hora_inicio", nullable = false)
  private LocalTime horaInicio;

  @Column(name = "hora_fin", nullable = false)
  private LocalTime horaFin;

  @ColumnDefault("1")
  @Column(name = "disponible")
  private Boolean disponible;

}