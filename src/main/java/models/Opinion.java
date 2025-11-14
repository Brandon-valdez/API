package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@Entity
@Table(name = "opinion")
public class Opinion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_opinion", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "id_autor", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "habilidades", "disponibilidades", "reunionesComoUsuario1", "reunionesComoUsuario2", "opinionesComoAutor", "opinionesComoReceptor", "password"})
  private Usuario idAutor;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "id_receptor", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "habilidades", "disponibilidades", "reunionesComoUsuario1", "reunionesComoUsuario2", "opinionesComoAutor", "opinionesComoReceptor", "password"})
  private Usuario idReceptor;

  @Lob
  @Column(name = "opinion", nullable = false)
  private String opinion;

  @Column(name = "valor_reunion")
  private Float valorReunion;

}