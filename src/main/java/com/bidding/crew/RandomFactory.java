package com.bidding.crew;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomFactory {

    @Bean
    public Random createRandomObject() {
        System.out.println("Random created");
        return new Random();
    }

}
