package br.com.conversormoeda.apicoinconverter.validator;

import br.com.conversormoeda.apicoinconverter.dto.MonetaryRateDTO;
import br.com.conversormoeda.apicoinconverter.dto.TransactionFinalDTO;
import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import br.com.conversormoeda.apicoinconverter.model.MonetaryRate;
import br.com.conversormoeda.apicoinconverter.model.Rate;
import br.com.conversormoeda.apicoinconverter.model.Transaction;
import br.com.conversormoeda.apicoinconverter.security.BadRequestException;
import br.com.conversormoeda.apicoinconverter.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Validator for errors of process the transaction
 *
 * @author mcrj
 */
@Component
public class TransactionValidator {

    @SuppressWarnings("unused")
    @Autowired
    private DateUtil dateUtil;

    /**
     * Get DTO data and other parameters to convert into a transaction
     *
     * @param monetaryRateDTO - DTO of Moneraty Rate
     * @param idUser - User ID
     * @param coinDestiny - Coin of Destiny
     * @param value - Value for convert
     * @return {@link Transaction}
     */
    public Transaction obtainDTOFromObj(final MonetaryRateDTO monetaryRateDTO,
                                                        final Integer idUser, final String coinDestiny,
                                                        final BigDecimal value) {

        try {
            final MonetaryRate monetaryRate = converterDTO(monetaryRateDTO);

            return converterMonetaryForTransaction(monetaryRate,
                    idUser, coinDestiny, value);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Illegal Argument Exception in the process");
        } catch (Exception ex) {
            throw new BadRequestException("Occurred an exception");
        }
    }

    /**
     * Get the transaction persisted in the base for to converter into the Transaction final DTO
     *
     * @param transaction - {@link Transaction}
     * @return {@link TransactionFinalDTO}
     */
    public TransactionFinalDTO obtainDTOTransaction(final Transaction transaction) {
        try {
            return converterObjectToDTO(transaction);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Illegal Argument Exception in the process");
        } catch (Exception ex) {
            throw new BadRequestException("Occurred an exception");
        }
    }

    /**
     * Convert DTO Monetary rate for object of representation
     *
     * @param monetaryRateDTO - DTO Monetary rate
     * @return {@link MonetaryRate}
     */
    private MonetaryRate converterDTO(final MonetaryRateDTO monetaryRateDTO){
        if (!monetaryRateDTO.isSuccess()){
            throw new BadRequestException("Occurred an exception about return of API");
        }
        final List<Rate> rateList = monetaryRateDTO.getRates().entrySet().stream()
                .map(rateMap -> Rate.builder()
                        .coin(ECoin.valueOf(rateMap.getKey()))
                        .tax(rateMap.getValue())
                        .build()).collect(Collectors.toList());

        return MonetaryRate.builder()
                .baseCoin(monetaryRateDTO.getBase())
                .date(monetaryRateDTO.getDate())
                .currentTime(dateUtil.instantTo(monetaryRateDTO.getCurrentTime()))
                .rate(rateList)
                .build();
    }

    /**
     * Converter Monetary rate and others parameters for into a transaction
     *
     * @param monetaryRate - Monetary Rate
     * @param idUser - User ID
     * @param coinDestiny - Coin of Destiny
     * @param value - Value for convert
     * @return {@link Transaction}
     */
    private Transaction converterMonetaryForTransaction(final MonetaryRate monetaryRate,
                                                        final Integer idUser, final String coinDestiny,
                                                        final BigDecimal value){
        final ECoin coinDest = ECoin.valueOf(coinDestiny);
        Optional<Rate> optRate = monetaryRate.getRate().stream()
                .filter(rate -> rate.getCoin().equals(coinDest)).findFirst();

        final Rate rateApi = optRate.orElse(null);

        if(Objects.isNull(rateApi)){
            throw new BadRequestException("Occurred an exception about return of API");
        }

        return Transaction.builder()
                .userId(idUser)
                .originCoin(monetaryRate.getBaseCoin())
                .valueCoin(value)
                .destinyCoin(ECoin.valueOf(coinDestiny))
                .conversionTax(rateApi.getTax())
                .conversionDateTime(dateUtil.datesTo(monetaryRate.getDate(), monetaryRate.getCurrentTime()))
                .build();

    }

    /**
     * Converter transaction persisted for into a DTO
     *
     * @param transaction - {@link Transaction}
     * @return {@link TransactionFinalDTO}
     */
    private TransactionFinalDTO converterObjectToDTO(final Transaction transaction){
        final BigDecimal destinyValue = transaction.getValueCoin().multiply(transaction.getConversionTax());

        return TransactionFinalDTO.builder()
                .transactionId(transaction.getId())
                .userId(transaction.getUserId())
                .originCoin(transaction.getOriginCoin())
                .valueOriginCoin(transaction.getValueCoin())
                .destinyCoin(transaction.getDestinyCoin())
                .valueDestinyCoin(destinyValue)
                .conversionTax(transaction.getConversionTax())
                .ConversionDateTime(transaction.getConversionDateTime())
                .build();
    }
}
