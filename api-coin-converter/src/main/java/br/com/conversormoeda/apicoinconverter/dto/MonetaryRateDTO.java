package br.com.conversormoeda.apicoinconverter.dto;

import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * DTO of the monetary rate
 *
 * @author mcrj
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonetaryRateDTO implements Serializable {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("base")
    private ECoin base;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("timestamp")
    private Long currentTime;

    @JsonProperty("rates")
    private Map<String, BigDecimal> rates;
}
