package org.datrunk.descent.server.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.datrunk.descent.entities.HeroCard;
import org.datrunk.descent.entities.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest(classes = DaoTest.Config.class, webEnvironment = WebEnvironment.NONE)
@ExtendWith({ SpringExtension.class })
@EnableTransactionManagement
@Transactional
public class DaoTest {
  @Configuration
  @EntityScan(basePackageClasses = { HeroCard.class })
  @ComponentScan
  @EnableAutoConfiguration
  static class Config {
  }

  private @PersistenceContext EntityManager em;

  @BeforeEach
  void before() {
    assertThat(em).isNotNull();
  }

  @Test
  void testQueries() {
    select("select h from HeroCard h left join fetch h.skills", HeroCard.class);
    List<HeroCard> actual = select("select h from Skill s join Hero h", HeroCard.class);
    assertThat(actual).isNotEmpty();
  }

  @Test
  void testItemsByHero() {
    List<Item> actual = em.createQuery("select i from Item i right join i.hero h where h.id = :id").setParameter("id", "Belrond").getResultList();
    assertThat(actual).isNotEmpty();
  }

  private <T> List<T> select(String jql, Class<T> clazz) {
    System.out.println(jql);
    List<T> actual = em.createQuery(jql, clazz).getResultList();
    System.out.println("  " + actual);
    return actual;
  }
}
