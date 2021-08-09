package br.com.conversormoeda.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class ConsumerService implements IConsumerService{

    private static final String API_KEY = "21738ea68dddb7bf9847bf64728a8e10";
    private static final String API_PARAM_DEFAULT = "&base=EUR&symbols=BRL,USD,EUR,JPY";
    private static final String API_URL = "http://api.exchangeratesapi.io/v1/latest?access_key=".concat(API_KEY)
            .concat(API_PARAM_DEFAULT);

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> getCoinByExchangeApi() {
        try {
            return restTemplate.getForEntity(API_URL, Object.class);
        } catch (Exception ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Service is down");
        }
    }
}