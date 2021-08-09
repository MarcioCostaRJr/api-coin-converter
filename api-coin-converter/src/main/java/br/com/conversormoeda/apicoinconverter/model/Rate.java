package br.com.conversormoeda.apicoinconverter.model;

import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Representation of obtaining fees
 *
 * @author mcrj
 */
@Builder
@Data
public class Rate {

    private ECoin coin;
    private BigDecimal tax;
}
