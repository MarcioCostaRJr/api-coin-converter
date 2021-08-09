package br.com.conversormoeda.controller;

import br.com.conversormoeda.service.IConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/external", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ConsumerController {

    private final IConsumerService consumerService;

    @GetMapping
    public ResponseEntity<?> getAllByDefault() {
        final ResponseEntity<?> coinByExchangeApi = consumerService.getCoinByExchangeApi();

        return OK.equals(coinByExchangeApi.getStatusCode())
            ? coinByExchangeApi
            : ResponseEntity.badRequest().build();
    }
}
