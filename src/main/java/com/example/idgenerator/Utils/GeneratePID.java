package com.example.idgenerator.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GeneratePID {

    @Bean
    public static int generatePid() {
        return Integer.parseInt(System.getProperty("PID"));
    }
}
