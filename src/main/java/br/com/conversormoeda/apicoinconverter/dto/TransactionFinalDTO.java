package br.com.conversormoeda.apicoinconverter.dto;

import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("TransactionDTO")
public class TransactionFinalDTO implements Serializable {

    @ApiModelProperty(value = "Integer value transaction ID")
    @JsonProperty("transactionId")
    private Integer transactionId;

    @ApiModelProperty(value = "Integer value user ID")
    @JsonProperty("userId")
    private Integer userId;

    @ApiModelProperty(value = "String value origin coin")
    @JsonProperty("originCoin")
    private ECoin originCoin;

    @ApiModelProperty(value = "BigDecimal value origin coin")
    @JsonProperty("valueOrigin")
    private BigDecimal valueOriginCoin;

    @ApiModelProperty(value = "String value destiny coin")
    @JsonProperty("destinyCoin")
    private ECoin destinyCoin;

    @ApiModelProperty(value = "BigDecimal value destiny coin")
    @JsonProperty("valueDestiny")
    private BigDecimal valueDestinyCoin;

    @ApiModelProperty(value = "BigDecimal value conversion tax")
    @JsonProperty("convertedTax")
    private BigDecimal conversionTax;

    @ApiModelProperty(value = "DateTime value conversion date")
    @JsonProperty("dateTime")
    private LocalDateTime ConversionDateTime;
}
