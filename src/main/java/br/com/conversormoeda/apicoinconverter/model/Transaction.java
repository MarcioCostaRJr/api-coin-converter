package br.com.conversormoeda.apicoinconverter.model;

import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representation about conversation of transactional monetary
 *
 * @author mcrj
 */
@Builder
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private ECoin originCoin;
    private BigDecimal valueCoin;
    private ECoin destinyCoin;
    private BigDecimal conversionTax;
    private LocalDateTime conversionDateTime;

}
