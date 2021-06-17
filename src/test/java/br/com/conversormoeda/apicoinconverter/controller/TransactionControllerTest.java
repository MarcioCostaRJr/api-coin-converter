package br.com.conversormoeda.apicoinconverter.controller;

import br.com.conversormoeda.apicoinconverter.dto.TransactionFinalDTO;
import br.com.conversormoeda.apicoinconverter.security.BadRequestException;
import br.com.conversormoeda.apicoinconverter.service.ITransactionService;
import org.apache.logging.log4j.util.Strings;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private ITransactionService transactionService;

    @Before
    public void setUp() {
        when(transactionService.processTransactionDTO(anyInt(), anyString(), any()))
                .thenReturn(TransactionFinalDTO.builder().build());
        when(transactionService.getAllTransactionByUserId(anyInt()))
                .thenReturn(Collections.singletonList(TransactionFinalDTO.builder().build()));
    }

    @Test
    public void testGetCoinConverter_whenReturnNotEmpty_thenResponse200() {
        final ResponseEntity<TransactionFinalDTO> returnDTO = transactionController.getCoinConverter(1, Strings.EMPTY, BigDecimal.ONE);
        assertEquals(HttpStatus.OK, returnDTO.getStatusCode());
        assertNotNull(returnDTO.getBody());
    }

    @Test
    public void testGetCoinConverter_whenReturnNull_thenResponseBadRequest() {
        when(transactionService.processTransactionDTO(anyInt(), anyString(), any()))
                .thenReturn(null);
        final ResponseEntity<TransactionFinalDTO> returnDTO = transactionController.getCoinConverter(1, Strings.EMPTY, BigDecimal.ONE);
        assertEquals(HttpStatus.BAD_REQUEST, returnDTO.getStatusCode());
        assertNull(returnDTO.getBody());
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetCoinConverter_whenBadRequestException_thenResponseStatusException() {
        when(transactionService.processTransactionDTO(anyInt(), anyString(), any()))
                .thenThrow(new BadRequestException(Strings.EMPTY));
        transactionController.getCoinConverter(1, Strings.EMPTY, BigDecimal.ONE);
    }

    @Test
    public void testGetAllTransactionByUser_whenReturnNotEmpty_thenResponse200() {
        final ResponseEntity<Collection<TransactionFinalDTO>> returnDTO = transactionController.getAllTransactionByUser(1);
        assertEquals(HttpStatus.OK, returnDTO.getStatusCode());
        assertNotNull(returnDTO.getBody());
    }

    @Test
    public void testGetAllTransactionByUser_whenReturnIsEmpty_thenResponseBodyNull() {
        when(transactionService.getAllTransactionByUserId(anyInt())).thenReturn(Collections.emptyList());
        final ResponseEntity<Collection<TransactionFinalDTO>> returnDTO = transactionController.getAllTransactionByUser(1);
        assertEquals(HttpStatus.BAD_REQUEST, returnDTO.getStatusCode());
        assertNull(returnDTO.getBody());
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetAllTransactionByUser_whenBadRequestException_thenResponseStatusException() {
        when(transactionService.getAllTransactionByUserId(anyInt()))
            .thenThrow(new BadRequestException(Strings.EMPTY));
        transactionController.getAllTransactionByUser(1);
    }
}
