package br.com.conversormoeda.apicoinconverter.dto;

import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO about field for request of the conversion monetary
 *
 * @author mcrj
 */
@Builder
@Data
@ApiModel("RequestCoinDTO")
public class RequestCoinDTO implements Serializable {

    @ApiModelProperty(value = "Integer value user ID")
    @JsonProperty("userId")
    private Integer userId;

    @ApiModelProperty(value = "Coin destiny to converter")
    @JsonProperty("coinDestiny")
    private ECoin coinDestiny;

    @ApiModelProperty(value = "Value for the process in the conversion")
    @JsonProperty("value")
    private BigDecimal value;
}
