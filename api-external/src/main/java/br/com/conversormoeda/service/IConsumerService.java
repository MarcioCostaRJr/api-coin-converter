package br.com.conversormoeda.service;

import org.springframework.http.ResponseEntity;

/**
 * Interface responsible for consuming information by external API
 *
 * @author mcrj
 */
public interface IConsumerService {

    /**
     * Get values of Coin in: BRL, USD, EUR and JPY
     *
     * @return {@link ResponseEntity<?>}
     */
    public ResponseEntity<?> getCoinByExchangeApi();
}
