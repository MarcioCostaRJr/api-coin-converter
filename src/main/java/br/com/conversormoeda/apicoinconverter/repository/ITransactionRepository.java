package br.com.conversormoeda.apicoinconverter.repository;

import br.com.conversormoeda.apicoinconverter.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for to manipulate data of transaction
 *
 * @author mcrj
 */
@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Integer> {

    /**
     * Find information of transaction by user Id
     *
     * @param userId - User Id
     * @return {@link Transaction}
     */
    Transaction findByUserId(final Integer userId);
}
