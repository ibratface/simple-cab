package com.datarepublic.simplecab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Server {
    public static void main(String[] args)
    {
        SpringApplication.run(Server.class, args);
    }
}
