package br.com.conversormoeda.apicoinconverter.model;

import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Representation of monetary rate
 *
 * @author mcrj
 */
@Builder
@Data
@ToString
@EqualsAndHashCode
public class MonetaryRate {

    private ECoin baseCoin;
    private LocalDate date;
    private LocalTime currentTime;
    private List<Rate> rate;
}
