package unicap.si.microservicos.api.buscaCep.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PointData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String logadouro;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String localidade;

    @Column(nullable = false)
    private String uf;

}
