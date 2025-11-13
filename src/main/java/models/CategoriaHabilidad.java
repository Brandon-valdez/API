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
@Table(name = "categoria_habilidad")
public class CategoriaHabilidad {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_categoria", nullable = false)
  private Integer id;

  @Column(name = "nombre_categoria", nullable = false, length = 100)
  private String nombreCategoria;

  @OneToMany(mappedBy = "idCategoriaHabilidad")
  @JsonIgnore
  private Set<Habilidade> habilidades = new LinkedHashSet<>();

}