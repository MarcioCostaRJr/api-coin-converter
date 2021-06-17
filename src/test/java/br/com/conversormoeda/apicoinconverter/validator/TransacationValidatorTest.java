package br.com.conversormoeda.apicoinconverter.validator;


import br.com.conversormoeda.apicoinconverter.dto.MonetaryRateDTO;
import br.com.conversormoeda.apicoinconverter.dto.TransactionFinalDTO;
import br.com.conversormoeda.apicoinconverter.enums.ECoin;
import br.com.conversormoeda.apicoinconverter.model.Transaction;
import br.com.conversormoeda.apicoinconverter.security.BadRequestException;
import br.com.conversormoeda.apicoinconverter.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransacationValidatorTest {

    private static final int ID = 1;
    private static final int USER_ID = 2;
    private static final ECoin ORIGIN_COIN = ECoin.EUR;
    private static final ECoin DESTINY_COIN = ECoin.BRL;
    private static final BigDecimal VALUE_COIN = BigDecimal.ONE;
    private static final BigDecimal VALUE_CONVERSION = BigDecimal.TEN;
    private static final LocalDateTime CURRENT_TIME = LocalDateTime.now();

    @InjectMocks
    private TransactionValidator transactionValidator;

    private Transaction transaction;

    private MonetaryRateDTO monetaryRateDTO;

    @Mock
    private DateUtil dateUtil;

    @Before
    public void setUp(){
        transaction = Transaction.builder()
                .id(ID)
                .userId(USER_ID)
                .originCoin(ORIGIN_COIN)
                .destinyCoin(DESTINY_COIN)
                .valueCoin(VALUE_COIN)
                .conversionTax(VALUE_CONVERSION)
                .conversionDateTime(CURRENT_TIME)
                .build();

        Map<String, BigDecimal> ratesMap = new HashMap<>();
        ratesMap.put(ECoin.USD.name(), BigDecimal.ONE);
        ratesMap.put(ECoin.BRL.name(), BigDecimal.TEN);
        monetaryRateDTO = new MonetaryRateDTO(Boolean.TRUE, ECoin.EUR, LocalDate.now(),
                1L, ratesMap);
    }

    @Test(expected = BadRequestException.class)
    public void testObtainDTOTransaction_whenTransactionIsNull_thenBadRequestException(){
        this.transactionValidator.obtainDTOTransaction(null);
    }

    @Test
    public void testObtainDTOTransaction_whenTransactionNotNull_thenReturnAssertSuccess(){
        final TransactionFinalDTO transactionFinalDTO = this.transactionValidator.obtainDTOTransaction(transaction);
        this.assertFromExpectedToDTO(transactionFinalDTO);
    }

    @Test(expected = BadRequestException.class)
    public void testObtainDTOFromObj_whenReturnDTONotSuccess_thenBadRequestException(){
        monetaryRateDTO.setSuccess(Boolean.FALSE);
        this.transactionValidator.obtainDTOFromObj(monetaryRateDTO,
                1, ECoin.BRL.name(), BigDecimal.ONE);
    }

    @Test(expected = BadRequestException.class)
    public void testObtainDTOFromObj_whenParameterIsWrong_thenBadRequestException(){
        this.transactionValidator.obtainDTOFromObj(monetaryRateDTO,
                1, "BRLA", BigDecimal.ONE);
    }

    @Test(expected = BadRequestException.class)
    public void testObtainDTOFromObj_whenIsNotExistRate_thenBadRequestException(){
        monetaryRateDTO.setRates(Collections.emptyMap());
        when(dateUtil.instantTo(anyLong())).thenReturn(LocalTime.now());
        this.transactionValidator.obtainDTOFromObj(monetaryRateDTO,
                1, ECoin.BRL.name(), BigDecimal.ONE);
    }

    @Test
    public void testObtainDTOFromObj_whenDTONotNull_thenReturnExpected(){
        when(dateUtil.instantTo(anyLong())).thenReturn(CURRENT_TIME.toLocalTime());
        when(dateUtil.datesTo(any(LocalDate.class), any(LocalTime.class))).thenReturn(CURRENT_TIME);
        final Transaction transactionRet = this.transactionValidator.obtainDTOFromObj(monetaryRateDTO,
                USER_ID, ECoin.BRL.name(), BigDecimal.ONE);
        assertFromExpectToTransaction(transactionRet);
    }

    private void assertFromExpectedToDTO(final TransactionFinalDTO transactionFinalDTO) {
        assertEquals(transaction.getId(), transactionFinalDTO.getTransactionId());
        assertEquals(transaction.getUserId(), transactionFinalDTO.getUserId());
        assertEquals(transaction.getOriginCoin(), transactionFinalDTO.getOriginCoin());
        assertEquals(transaction.getDestinyCoin(), transactionFinalDTO.getDestinyCoin());
        assertEquals(transaction.getValueCoin(), transactionFinalDTO.getValueOriginCoin());
        assertEquals(transaction.getConversionTax(), transactionFinalDTO.getConversionTax());
        assertEquals(transaction.getValueCoin().multiply(transaction.getConversionTax()),
                transactionFinalDTO.getValueDestinyCoin());
        assertEquals(transaction.getConversionDateTime(), transactionFinalDTO.getConversionDateTime());
    }

    private void assertFromExpectToTransaction(final Transaction transact) {
        assertEquals(transaction.getUserId(), transact.getUserId());
        assertEquals(transaction.getOriginCoin(), transact.getOriginCoin());
        assertEquals(transaction.getDestinyCoin(), transact.getDestinyCoin());
        assertEquals(transaction.getValueCoin(), transact.getValueCoin());
        assertEquals(transaction.getConversionTax(), transact.getConversionTax());
        assertEquals(transaction.getConversionDateTime(), transact.getConversionDateTime());
    }
}
