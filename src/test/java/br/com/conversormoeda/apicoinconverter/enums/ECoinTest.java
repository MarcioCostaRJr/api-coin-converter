package br.com.conversormoeda.apicoinconverter.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ECoinTest {

    @Test
    public void testECoinBRL_whenExpected_thenReturnEquals(){
        assertEquals(0, ECoin.BRL.ordinal());
    }

    @Test
    public void testECoinUSD_whenExpected_thenReturnEquals(){
        assertEquals(1, ECoin.USD.ordinal());
    }

    @Test
    public void testECoinEUR_whenExpected_thenReturnEquals(){
        assertEquals(2, ECoin.EUR.ordinal());
    }

    @Test
    public void testECoinJPY_whenExpected_thenReturnEquals (){
        assertEquals(3, ECoin.JPY.ordinal());
    }
}
