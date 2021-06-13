package br.com.conversormoeda.apicoinconverter.service;

import br.com.conversormoeda.apicoinconverter.dto.MonetaryRateDTO;
import br.com.conversormoeda.apicoinconverter.dto.TransactionFinalDTO;
import br.com.conversormoeda.apicoinconverter.model.Transaction;
import br.com.conversormoeda.apicoinconverter.repository.ITransactionRepository;
import br.com.conversormoeda.apicoinconverter.security.BadRequestException;
import br.com.conversormoeda.apicoinconverter.util.DateUtil;
import br.com.conversormoeda.apicoinconverter.validator.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * Service for to direction information about transaction
 *
 * @author mcrj
 */
@Service
public class TransactionService implements ITransactionService{

    private static final String API_KEY = "21738ea68dddb7bf9847bf64728a8e10";
    private static final String API_PARAM_DEFAULT = "&base=EUR&symbols=BRL,USD,EUR,JPY";
    private static final String API_URL = "http://api.exchangeratesapi.io/v1/latest?access_key=".concat(API_KEY)
            .concat(API_PARAM_DEFAULT);

    @SuppressWarnings("unused")
    @Autowired
    private ITransactionRepository ITransactionRepository;

    @SuppressWarnings("unused")
    @Autowired
    private TransactionValidator transactionValidator;

    @SuppressWarnings("unused")
    @Autowired
    private DateUtil dateUtil;

    @Override
    public TransactionFinalDTO processTransactionDTO(final Integer idUser, final String coinDestiny, final BigDecimal value) {
        ResponseEntity<MonetaryRateDTO> responseEntity = this.obtainResponseMonetaryRate();

        if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return null;
        } else {
            final Transaction transactionConverted =
                    transactionValidator.obtainDTOFromObj(responseEntity.getBody(), idUser, coinDestiny, value);
            try {
                final Transaction savedTransaction = this.save(transactionConverted);

                return transactionValidator.obtainDTOTransaction(savedTransaction);

            } catch (DataIntegrityViolationException ex) {
                throw new BadRequestException("Data Integrity Violated");
            } catch (Exception ex) {
                throw new BadRequestException("Ocorred an exception");
            }
        }
    }

    /**
     * Save the transaction converted of DTO
     *
     * @param transaction - {@link Transaction}
     * @return {@link Transaction}
     */
    private Transaction save(final Transaction transaction){
        return this.ITransactionRepository.save(transaction);
    }

    /**
     * Get all the information obtained from API
     *
     * @return {@link ResponseEntity<MonetaryRateDTO>}
     */
    private ResponseEntity<MonetaryRateDTO> obtainResponseMonetaryRate() {
        try {
            return new RestTemplate().getForEntity(API_URL, MonetaryRateDTO.class);
        } catch (Exception ex) {
            throw new BadRequestException("Service is down");
        }
    }
}
