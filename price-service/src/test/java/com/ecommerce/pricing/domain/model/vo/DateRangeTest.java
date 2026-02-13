package com.ecommerce.pricing.domain.model.vo;

import com.ecommerce.pricing.domain.exception.DomainValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.INVALID_DATE_RANGE_DESC;
import static com.ecommerce.pricing.domain.exception.constant.ExceptionConstant.NULL_DATES_DESC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateRangeTest {

    @Test
    void givenValidDates_whenCreateDateRage_thenSuccess() {
        //given
        final var start = LocalDateTime.of(2020, 6, 14, 0, 0);
        final var end = LocalDateTime.of(2020, 12, 31, 23, 59);

        //when
        final var range = new DateRange(start, end);

        //then
        assertNotNull(range);
        assertEquals(start, range.start());
        assertEquals(end, range.end());
    }

    @ParameterizedTest
    @MethodSource("nullDatesProvider")
    void givenNullDates_whenCreateDateRange_thenThrowDomainValidationException(LocalDateTime start, LocalDateTime end) {
        //when /then
        final var exception = assertThrows(DomainValidationException.class, () -> new DateRange(start, end));
        assertEquals(NULL_DATES_DESC, exception.getMessage());
    }

    private static Stream<Arguments> nullDatesProvider() {
        return Stream.of(
                Arguments.of(null, LocalDateTime.now()),
                Arguments.of(LocalDateTime.now(), null),
                Arguments.of(null, null)
        );
    }

    @Test
    void givenEndBeforeStartDates_whenCreateDateRange_thenThrowDomainValidationException() {
        //given
        final var start = LocalDateTime.of(2020, 6, 14, 10, 0);
        final var end = LocalDateTime.of(2020, 6, 14, 9, 0);

        //when /then
        final var exception = assertThrows(DomainValidationException.class, () -> new DateRange(start, end));
        assertEquals(INVALID_DATE_RANGE_DESC, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("inclusionDatesProvider")
    void givenDates_whenValidateInclusion_thenThrowDomainValidationException(LocalDateTime dateToCheck, boolean expectedResult) {
        //given
        final var start = LocalDateTime.of(2020, 6, 14, 0, 0);
        final var end = LocalDateTime.of(2020, 6, 14, 23, 59);
        final var range = new DateRange(start, end);

        //when /then
        assertEquals(expectedResult, range.includes(dateToCheck));
    }

    private static Stream<Arguments> inclusionDatesProvider() {
        final var start = LocalDateTime.of(2020, 6, 14, 0, 0);
        final var end = LocalDateTime.of(2020, 6, 14, 23, 59);
        return Stream.of(
                Arguments.of(start, true),                  //Límite inferior exacto
                Arguments.of(end, true),                    //Límite superior exacto
                Arguments.of(start.plusHours(5), true),     //Dentro del rango
                Arguments.of(start.minusSeconds(1), false), //Justo antes del inicio
                Arguments.of(end.plusSeconds(1), false)     // usto después del fin
        );
    }

    @Test
    void givenDateRanges_whenCompareHashCodeAndEquals_thenCompare() {
        //given
        final var start = LocalDateTime.now();
        final var end = start.plusDays(1);

        final var range1 = new DateRange(start, end);
        final var range2 = new DateRange(start, end);
        final var range3 = new DateRange(start, end.plusHours(1));

        //when /then
        assertEquals(range1, range2);
        assertEquals(range1.hashCode(), range2.hashCode());
        assertNotEquals(range1, range3);
        assertNotEquals(range1.hashCode(), range3.hashCode());
    }
}