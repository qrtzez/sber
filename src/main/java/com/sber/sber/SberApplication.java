package com.sber.sber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SberApplication {

    public static void main(String[] args) {
        SpringApplication.run(SberApplication.class, args);
    }

    public static String reverseWords(final String original) {
        StringBuilder builder = new StringBuilder();
        List<String> strings = Arrays.stream(original.split(" ")).toList();
        for (String str : strings) {
            builder.append(str).reverse();
        }
        return builder.toString();
    }
}
