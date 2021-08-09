package br.com.conversormoeda.apicoinconverter.service;

import br.com.conversormoeda.apicoinconverter.dto.TransactionFinalDTO;
import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import br.com.conversormoeda.apicoinconverter.model.Transaction;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Interface responsible for ensuring transaction service subscriptions
 *
 * @author mcrj
 */
public interface ITransactionService {

    /**
     * Get currency conversion values through API
     *
     * @return {@link Transaction}
     */
    TransactionFinalDTO processTransactionDTO(final Integer idUser, final ECoin coinDestiny, final BigDecimal value);

    /**
     * Get list of transaction by user
     *
     * @return {@link Collection<Transaction>}
     */
    Collection<TransactionFinalDTO> getAllTransactionByUserId(final Integer idUser);
}
