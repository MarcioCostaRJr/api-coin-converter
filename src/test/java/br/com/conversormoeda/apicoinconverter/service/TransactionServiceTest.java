package br.com.conversormoeda.apicoinconverter.service;

import br.com.conversormoeda.apicoinconverter.dto.MonetaryRateDTO;
import br.com.conversormoeda.apicoinconverter.dto.TransactionFinalDTO;
import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import br.com.conversormoeda.apicoinconverter.model.Transaction;
import br.com.conversormoeda.apicoinconverter.repository.ITransactionRepository;
import br.com.conversormoeda.apicoinconverter.security.BadRequestException;
import br.com.conversormoeda.apicoinconverter.validator.TransactionValidator;
import org.apache.logging.log4j.util.Strings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private TransactionValidator transactionValidator;
    @Mock
    private ITransactionRepository repository;

    @Before
    public void setUp() {
        Map<String, BigDecimal> ratesMap = new HashMap<>();
        ratesMap.put(ECoin.USD.name(), BigDecimal.ONE);
        ratesMap.put(ECoin.BRL.name(), BigDecimal.TEN);

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(
                new ResponseEntity<>(new MonetaryRateDTO(Boolean.TRUE, ECoin.EUR, LocalDate.now(),
                1L, ratesMap), HttpStatus.OK));
    }

    @Test(expected = BadRequestException.class)
    public void testProcessTransactionDTO_whenException_thenReturnBadRequest() {
        when(restTemplate.getForEntity(anyString(), any())).thenThrow(new RuntimeException());
        transactionService.processTransactionDTO(1, Strings.EMPTY, BigDecimal.ONE);
    }

    @Test
    public void testProcessTransactionDTO_whenResponse400_thenReturnDTONull() {
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(new MonetaryRateDTO(),
                HttpStatus.BAD_REQUEST));
        final TransactionFinalDTO transactionFinalDTO = transactionService.processTransactionDTO(1,
                Strings.EMPTY, BigDecimal.ONE);
        assertNull(transactionFinalDTO);
    }

    @Test(expected = BadRequestException.class)
    public void testProcessTransactionDTO_whenDataIntegrityViolationExceptionForSave_thenBadRequestException() {
        when(repository.save(any())).thenThrow(new DataIntegrityViolationException(Strings.EMPTY));
        transactionService.processTransactionDTO(1,
                Strings.EMPTY, BigDecimal.ONE);
    }

    @Test(expected = BadRequestException.class)
    public void testProcessTransactionDTO_whenRunTimeExceptionForSave_thenBadRequestException() {
        when(repository.save(any())).thenThrow(new RuntimeException());
        transactionService.processTransactionDTO(1,
                Strings.EMPTY, BigDecimal.ONE);
    }

    @Test
    public void testProcessTransactionDTO_whenProcessObtainSuccess_thenReturnDTO() {
        when(repository.save(any())).thenReturn(Transaction.builder().build());
        when(transactionValidator.obtainDTOTransaction(any())).thenReturn(TransactionFinalDTO.builder().build());
        final TransactionFinalDTO transactionFinalDTO = transactionService.processTransactionDTO(1,
                Strings.EMPTY, BigDecimal.ONE);
        assertNotNull(transactionFinalDTO);
    }

    @Test
    public void testGetAllTransactionByUserId_whenProcessObtainSuccess_thenReturnDTO() {
        when(repository.findByUserId(anyInt())).thenReturn(Collections.singletonList(Transaction.builder().build()));
        final Collection<TransactionFinalDTO> collectionTransaction = transactionService
                .getAllTransactionByUserId(1);
        assertNotNull(collectionTransaction);
        assertFalse(collectionTransaction.isEmpty());
    }

    @Test(expected = BadRequestException.class)
    public void testGetAllTransactionByUserId_whenIsEmpty_thenBadRequestException() {
        when(repository.findByUserId(anyInt())).thenReturn(Collections.emptyList());
        transactionService.getAllTransactionByUserId(1);
    }
}
