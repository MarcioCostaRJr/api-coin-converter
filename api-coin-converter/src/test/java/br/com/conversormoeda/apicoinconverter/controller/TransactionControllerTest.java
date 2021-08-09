package br.com.conversormoeda.apicoinconverter.controller;

import br.com.conversormoeda.apicoinconverter.dto.RequestCoinDTO;
import br.com.conversormoeda.apicoinconverter.dto.TransactionFinalDTO;
import br.com.conversormoeda.apicoinconverter.service.ITransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

import static br.com.conversormoeda.apicoinconverter.enums.ECoin.BRL;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private ITransactionService transactionService;

    private RequestCoinDTO coinDTO;

    @Before
    public void setUp() {
        when(transactionService.processTransactionDTO(anyInt(), any(), any()))
                .thenReturn(TransactionFinalDTO.builder().build());
        when(transactionService.getAllTransactionByUserId(anyInt()))
                .thenReturn(Collections.singletonList(TransactionFinalDTO.builder().build()));

        coinDTO = RequestCoinDTO.builder()
                .coinDestiny(BRL)
                .userId(1)
                .value(BigDecimal.TEN)
                .build();
    }

    @Test
    public void testGetCoinConverter_whenReturnNotEmpty_thenResponse200() {
        final ResponseEntity<TransactionFinalDTO> returnDTO = transactionController.getCoinConverter(coinDTO);
        assertEquals(OK, returnDTO.getStatusCode());
        assertNotNull(returnDTO.getBody());
    }

    @Test
    public void testGetCoinConverter_whenReturnNull_thenResponseBadRequest() {
        when(transactionService.processTransactionDTO(anyInt(), any(), any()))
                .thenReturn(null);
        final ResponseEntity<TransactionFinalDTO> returnDTO = transactionController.getCoinConverter(coinDTO);
        assertEquals(BAD_REQUEST, returnDTO.getStatusCode());
        assertNull(returnDTO.getBody());
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetCoinConverter_whenBadRequestException_thenResponseStatusException() {
        when(transactionService.processTransactionDTO(anyInt(), any(), any()))
                .thenThrow(new ResponseStatusException(BAD_REQUEST));
        transactionController.getCoinConverter(coinDTO);
    }

    @Test
    public void testGetAllTransactionByUser_whenReturnNotEmpty_thenResponse200() {
        final ResponseEntity<Collection<TransactionFinalDTO>> returnDTO = transactionController.getAllTransactionByUser(1);
        assertEquals(OK, returnDTO.getStatusCode());
        assertNotNull(returnDTO.getBody());
    }

    @Test
    public void testGetAllTransactionByUser_whenReturnIsEmpty_thenResponseBodyNull() {
        when(transactionService.getAllTransactionByUserId(anyInt())).thenReturn(Collections.emptyList());
        final ResponseEntity<Collection<TransactionFinalDTO>> returnDTO = transactionController.getAllTransactionByUser(1);
        assertEquals(BAD_REQUEST, returnDTO.getStatusCode());
        assertNull(returnDTO.getBody());
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetAllTransactionByUser_whenBadRequestException_thenResponseStatusException() {
        when(transactionService.getAllTransactionByUserId(anyInt()))
            .thenThrow(new ResponseStatusException(BAD_REQUEST));
        transactionController.getAllTransactionByUser(1);
    }
}
