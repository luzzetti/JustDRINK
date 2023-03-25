package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.mappers.RestaurantJpaMapperImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("it.luzzetti.justdrink.backoffice.infrastructure.output.jpa")
@ComponentScan
@EntityScan
@Import({RestaurantJpaMapperImpl.class})
public class SpringDataConfiguration {}
