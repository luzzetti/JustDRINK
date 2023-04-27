package it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import it.luzzetti.justdrink.backoffice.infrastructure.output.jpa.entities.MenuJpaEntity;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


@DataJpaTest
class MenuJpaRepositoryTest {

  @Autowired private TestEntityManager entityManager;

  @Autowired private MenuJpaRepository repositoryUnderTest;

  @Test
  void whenFindByName_thenReturnSample() {
    // given

    MenuJpaEntity menuJpaEntity = new MenuJpaEntity();
    UUID theMenuId = UUID.randomUUID();
    menuJpaEntity.setId(theMenuId);
    menuJpaEntity.setSections(Collections.emptySet());

    entityManager.persist(menuJpaEntity);
    entityManager.flush();

    // when
    MenuJpaEntity found = repositoryUnderTest.findById(theMenuId).orElse(new MenuJpaEntity());

    // then
    assertThat(found.getId()).isEqualTo(menuJpaEntity.getId());
  }
}
