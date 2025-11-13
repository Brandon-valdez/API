package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "estado_reunion")
public class EstadoReunion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_estado", nullable = false)
  private Integer id;

  @Column(name = "tipo_estado", nullable = false, length = 50)
  private String tipoEstado;

  @OneToMany(mappedBy = "idEstadoR")
  private Set<Reunione> reuniones = new LinkedHashSet<>();

}