package pedro.neurotech.neurotechapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CurrencyRate {
    //Representação do banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String base;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    @ElementCollection
    Map<String, Double> rates;


}
