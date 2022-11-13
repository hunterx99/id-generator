package com.example.idgenerator.Utils;

import org.springframework.context.annotation.Bean;

import java.time.Instant;

public class DateUtils {

    private static final Long START_EPOCH = 1667296800000L;
    @Bean
    public static long getEpochMills() {
        return Instant.now().toEpochMilli() - START_EPOCH;
    }
}
