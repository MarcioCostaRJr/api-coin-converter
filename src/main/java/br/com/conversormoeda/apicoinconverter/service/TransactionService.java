package br.com.conversormoeda.apicoinconverter.service;

import br.com.conversormoeda.apicoinconverter.dto.MonetaryRateDTO;
import br.com.conversormoeda.apicoinconverter.dto.TransactionFinalDTO;
import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import br.com.conversormoeda.apicoinconverter.model.Transaction;
import br.com.conversormoeda.apicoinconverter.repository.ITransactionRepository;
import br.com.conversormoeda.apicoinconverter.util.DateUtil;
import br.com.conversormoeda.apicoinconverter.validator.TransactionValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

/**
 * Service for to direction information about transaction
 *
 * @author mcrj
 */
@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService{

    private static final String NAME_CLASS = Transaction.class.getSimpleName();
    private static final String API_KEY = "21738ea68dddb7bf9847bf64728a8e10";
    private static final String API_PARAM_DEFAULT = "&base=EUR&symbols=BRL,USD,EUR,JPY";
    private static final String API_URL = "http://api.exchangeratesapi.io/v1/latest?access_key=".concat(API_KEY)
            .concat(API_PARAM_DEFAULT);

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private ITransactionRepository transactionRepository;

    private TransactionValidator transactionValidator;

    private RestTemplate restTemplate;

    private DateUtil dateUtil;


    @Override
    public TransactionFinalDTO processTransactionDTO(final Integer idUser, final ECoin coinDestiny,
                                                     final BigDecimal value) {
        logger.info("Process to generate transaction by API started");
        ResponseEntity<MonetaryRateDTO> responseEntity = this.obtainResponseMonetaryRate();

        if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return null;
        } else {
            final Transaction transactionConverted =
                    transactionValidator.obtainDTOFromObj(responseEntity.getBody(), idUser, coinDestiny, value);

            final Transaction savedTransaction = this.save(transactionConverted);

            return transactionValidator.obtainDTOTransaction(savedTransaction);
        }
    }

    @Override
    public Collection<TransactionFinalDTO> getAllTransactionByUserId(Integer idUser) {
        logger.info("Process to find all transactions by user");
        final Collection<Transaction> transaction = this.transactionRepository.findByUserId(idUser);

        if (transaction.isEmpty()) {
            logger.error(NAME_CLASS.concat(" - ").concat("Occurred a problem into return of data"));
            throw new ResponseStatusException(NO_CONTENT, "Occurred a problem into return of data");
        } else {
            return transaction.stream()
                    .map(it -> transactionValidator.obtainDTOTransaction(it))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Save the transaction converted of DTO
     *
     * @param transaction - {@link Transaction}
     * @return {@link Transaction}
     */
    private Transaction save(final Transaction transaction){
        logger.info("Process to save the transaction");
        try {
            return this.transactionRepository.save(transaction);
        } catch (DataIntegrityViolationException ex) {
            logger.error(NAME_CLASS.concat(" - ").concat(ex.toString()));
            throw new ResponseStatusException(FORBIDDEN, "Data Integrity Violated", ex);
        } catch (Exception ex) {
            logger.error(NAME_CLASS.concat(" - ").concat(ex.toString()));
            throw new ResponseStatusException(BAD_REQUEST, "Occurred an exception", ex);
        }
    }

    /**
     * Get all the information obtained from API
     *
     * @return {@link ResponseEntity<MonetaryRateDTO>}
     */
    private ResponseEntity<MonetaryRateDTO> obtainResponseMonetaryRate() {
        try {
            return restTemplate.getForEntity(API_URL, MonetaryRateDTO.class);
        } catch (Exception ex) {
            logger.error(NAME_CLASS.concat(" - ").concat("Service is down"));
            throw new ResponseStatusException(BAD_REQUEST, "Service is down");
        }
    }
}
