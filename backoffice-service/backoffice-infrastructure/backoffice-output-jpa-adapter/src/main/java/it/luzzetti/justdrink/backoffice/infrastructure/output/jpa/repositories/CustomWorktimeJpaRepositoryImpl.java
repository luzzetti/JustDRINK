package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomWorktimeJpaRepositoryImpl implements CustomWorktimeJpaRepository {

  private final EntityManager entityManager;

  /*
   * More Info: "https://samuel-mumo.medium.com/dynamic-queries-and-querydsl-jpa-support-in-spring-a1b4e233084b"
   * More Info: "http://querydsl.com/static/querydsl/4.0.1/reference/html_single/#d0e227"
   */
}
