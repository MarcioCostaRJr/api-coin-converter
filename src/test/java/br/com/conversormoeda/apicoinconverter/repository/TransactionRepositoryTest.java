package br.com.conversormoeda.apicoinconverter.repository;

import br.com.conversormoeda.apicoinconverter.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRepositoryTest {

    @Mock
    private ITransactionRepository transactionRepository;

    private Collection<Transaction> transactions;

    @Before
    public void setUp(){
        when(transactionRepository.findByUserId(anyInt()))
                .thenReturn(Collections.singleton(Transaction.builder().build()));
    }

    @Test
    public void testFindByUserId_whenNotNull_thenReturnTrue() {
        assertNotNull(transactionRepository.findByUserId(1));
    }

    @Test
    public void testFindByUserId_whenIsNotEmpty_thenReturnTrue() {
        assertTrue(transactionRepository.findByUserId(1).size() > 0);
    }

    @Test
    public void testFindByUserId_whenIsNull_thenReturnNull() {
        when(transactionRepository.findByUserId(anyInt())).thenReturn(null);
        assertNull(transactionRepository.findByUserId(1));
    }
}
