package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "nivel_habilidad")
public class NivelHabilidad {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_nivel", nullable = false)
  private Integer id;

  @Column(name = "nom_nivel", nullable = false, length = 100)
  private String nomNivel;

  @OneToMany(mappedBy = "idNivel")
  @JsonIgnore
  private Set<Habilidade> habilidades = new LinkedHashSet<>();

}