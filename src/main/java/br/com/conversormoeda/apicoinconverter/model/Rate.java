package br.com.conversormoeda.apicoinconverter.model;

import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Representation of obtaining fees
 *
 * @author mcrj
 */
@Builder
@Data
@ToString
@EqualsAndHashCode
public class Rate {

    private ECoin coin;
    private BigDecimal tax;
}
