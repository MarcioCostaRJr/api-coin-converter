package br.com.conversormoeda.apicoinconverter.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Singleton for converting values to date
 *
 * @author mcrj
 */
@Service
@Scope("singleton")
public class DateUtil {

    private static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    /**
     * Convert values from {@link Long} to {@link LocalTime}
     *
     * @param value - {@link Long}
     * @return {@link LocalTime}
     */
    public LocalTime instantTo(final Long value){
        return Instant.ofEpochSecond(value)
                .atZone(ZoneId.of("GMT-3")).toLocalTime();
    }

    /**
     * Convert values from {@link Long} to {@link String}
     *
     * @param value - {@link Long}
     * @return {@link String}
     */
    public String instantToStringFormat(final Long value){
        return Instant.ofEpochSecond(value)
                .atZone(ZoneId.of("GMT-3"))
                .format(DateTimeFormatter.ofPattern(FORMATTER));
    }

    /**
     * Convert values from {@link LocalDate} and {@link LocalTime} to {@link LocalDateTime}
     *
     * @param date - {@link LocalDate}
     * @param time - {@link LocalTime}
     * @return {@link LocalDateTime}
     */
    public LocalDateTime datesTo(final LocalDate date, final LocalTime time) {
        return LocalDateTime.of(date, time);
    }
}
