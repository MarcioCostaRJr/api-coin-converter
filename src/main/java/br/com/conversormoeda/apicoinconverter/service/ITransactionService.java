package br.com.conversormoeda.apicoinconverter.service;

import br.com.conversormoeda.apicoinconverter.dto.TransactionFinalDTO;
import br.com.conversormoeda.apicoinconverter.model.Transaction;

import java.math.BigDecimal;

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
    TransactionFinalDTO processTransactionDTO(final Integer idUser, final String coinDestiny, final BigDecimal value);
}
