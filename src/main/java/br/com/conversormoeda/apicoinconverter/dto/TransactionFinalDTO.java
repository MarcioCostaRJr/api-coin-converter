package br.com.conversormoeda.apicoinconverter.dto;

import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO about conversation of transactional monetary complete
 *
 * @author mcrj
 */
@Builder
@Data
@ToString
@EqualsAndHashCode
public class TransactionFinalDTO implements Serializable {

    @JsonProperty("transactionId")
    private Integer transactionId;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("originCoin")
    private ECoin originCoin;

    @JsonProperty("valueOrigin")
    private BigDecimal valueOriginCoin;

    @JsonProperty("destinyCoin")
    private ECoin destinyCoin;

    @JsonProperty("valueDestiny")
    private BigDecimal valueDestinyCoin;

    @JsonProperty("convertedTax")
    private BigDecimal conversionTax;

    @JsonProperty("dateTime")
    private LocalDateTime ConversionDateTime;
}
