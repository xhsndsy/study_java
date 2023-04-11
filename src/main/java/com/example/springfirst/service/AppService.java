package com.example.springfirst.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class AppService {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Value("classpath:/bean.xml")
    private Resource resource;

    String logo;

    @PostConstruct
    public void init() throws IOException {
        try (var reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            logo = reader.lines().collect(Collectors.joining("\n"));
            logger.info(logo);
        }
    }

    public void getResourceFileName() {
    }
}
//
//    public Resource getResource() {
//        logger.info("getResource");
//        return resource;
//    }
//}
