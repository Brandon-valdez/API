package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@Entity
@Table(name = "habilidades")
public class Habilidade {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_habilidad", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_usuario", nullable = false)
  @JsonManagedReference
  private Usuario idUsuario;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_categoria_habilidad", nullable = false)
  private CategoriaHabilidad idCategoriaHabilidad;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_nivel", nullable = false)
  private NivelHabilidad idNivel;

  @Column(name = "nom_habilidad", nullable = false, length = 150)
  private String nomHabilidad;

  @Lob
  @Column(name = "descripcion_breve")
  private String descripcionBreve;

  @Column(name = "foto_diploma", length = 255)
  private String fotoDiploma;

}