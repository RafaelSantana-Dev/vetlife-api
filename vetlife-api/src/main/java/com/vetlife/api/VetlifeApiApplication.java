package com.vetlife.api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VetlifeApiApplication {
    public static void main(String[] args) { SpringApplication.run(VetlifeApiApplication.class, args); }
}