package br.com.conversormoeda.apicoinconverter.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilTest {

    private final static LocalTime LOCAL_TIME = Instant.ofEpochMilli(1L).atZone(ZoneId.systemDefault()).toLocalTime();

    @Mock
    private DateUtil dateUtil;

    @Test
    public void testInstantTo_whenProcess_thenReturnSuccess() {
        doReturn(LOCAL_TIME).when(dateUtil).instantTo(anyLong());
        assertEquals(LOCAL_TIME, dateUtil.instantTo(12L));

        doReturn(LOCAL_TIME).when(dateUtil).instantTo(2L);
        assertEquals(LOCAL_TIME, dateUtil.instantTo(2L));
    }

    @Test
    public void testInstantTo_whenValuesDifferent_thenReturnNotEquals() {
        doReturn(LocalTime.now()).when(dateUtil).instantTo(anyLong());
        assertNotEquals(LOCAL_TIME, dateUtil.instantTo(1L));
    }

    @Test
    public void testInstantToStringFormat_whenProcess_thenReturnSuccess() {
        final String localTimeString = LOCAL_TIME.toString();
        doReturn(localTimeString).when(dateUtil).instantToStringFormat(anyLong());
        assertEquals(localTimeString, dateUtil.instantToStringFormat(12L));

        doReturn(localTimeString).when(dateUtil).instantToStringFormat(2L);
        assertEquals(localTimeString, dateUtil.instantToStringFormat(2L));
    }

    @Test
    public void testInstantToStringFormat_whenValuesDifferent_thenReturnNotEquals() {
        final String localTimeString = LocalTime.now().toString();
        doReturn(localTimeString).when(dateUtil).instantToStringFormat(anyLong());
        assertNotEquals(LOCAL_TIME.toString(), dateUtil.instantToStringFormat(1L));
    }
}
