package me.jincrates.sample.global.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {

    @PersistenceContext
    private EntityManager entityManager;
}
