package com.sasajankovic.domain.entities.airport;

import com.sasajankovic.domain.constants.FlightAdvisorConstants;
import lombok.NonNull;

import java.util.Optional;

public class TimezoneOffset {
    private final int offset;

    private TimezoneOffset(@NonNull Integer offset) {
        if (offset < FlightAdvisorConstants.TIMEZONE_OFFSET_MIN
                || offset > FlightAdvisorConstants.TIMEZONE_OFFSET_MAX)
            throw new IllegalArgumentException(
                    String.format(
                            "Timezone must be between %d and %d",
                            FlightAdvisorConstants.TIMEZONE_OFFSET_MIN,
                            FlightAdvisorConstants.TIMEZONE_OFFSET_MAX));

        this.offset = offset;
    }

    public static TimezoneOffset of(Optional<Integer> offset) {
        return offset.isPresent() ? new TimezoneOffset(offset.get()) : null;
    }

    public int get() {
        return offset;
    }
}
