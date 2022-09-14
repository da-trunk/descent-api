package org.datrunk.descent.server.repo;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.datrunk.descent.entities.HeroCard;
import org.datrunk.descent.entities.HeroItemClass;
import org.datrunk.descent.entities.Item;
import org.datrunk.naked.server.repo.BaseRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.ForwardedHeaderFilter;

@SpringBootTest(classes = DaoTest.Config.class, webEnvironment = WebEnvironment.NONE)
@ExtendWith({SpringExtension.class})
@EnableTransactionManagement
@Transactional
public class DaoTest {
  @Configuration
  @EntityScan(basePackageClasses = {HeroCard.class})
  @EnableJpaRepositories(
      basePackageClasses = {HeroRepo.class},
      repositoryBaseClass = BaseRepositoryImpl.class,
      considerNestedRepositories = true)
  @EnableHypermediaSupport(type = HypermediaType.HAL)
  @ComponentScan
  @EnableAutoConfiguration
  static class Config {
    @Bean
    RepositoryRestConfigurer repositoryRestConfigurer(EntityManager entityManager) {
      return RepositoryRestConfigurer.withConfig(
          config -> {
            config.exposeIdsFor(
                entityManager.getMetamodel().getEntities().stream()
                    .map(javax.persistence.metamodel.Type::getJavaType)
                    .toArray(Class[]::new));
          });
    }

    @Bean
    com.fasterxml.jackson.databind.Module javaTimeModule() {
      JavaTimeModule module = new JavaTimeModule();
      return module;
    }

    @Bean
    FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
      FilterRegistrationBean<ForwardedHeaderFilter> result = new FilterRegistrationBean<>();
      result.setFilter(new ForwardedHeaderFilter());
      result.setOrder(0);
      return result;
    }
  }

  private @PersistenceContext EntityManager em;

  @BeforeEach
  void before() {
    assertThat(em).isNotNull();
  }

  @Test
  void testQueries() {
    List<HeroCard> actual =
        select("select h from HeroCard h left join fetch h.skills", HeroCard.class);
    assertThat(actual).isNotEmpty();
  }

  @Test
  void testItemsByHero() {

    final HeroCard hero = em.find(HeroCard.class, "Beregond");
    Stream.of("sword", "shield", "plate mail")
        .map(Item::new)
        .map(
            item -> {
              item.setHero(hero);
              return item;
            })
        .forEach(em::merge);
    em.flush();
    {
      List<Item> actual =
          em.createQuery("select i from Item i right join i.hero h where h.id = :id")
              .setParameter("id", "Beregond")
              .getResultList();
      assertThat(actual).hasSize(3);
      assertThat(actual).allMatch(item -> item.getHero().getId().equals("Beregond"));
    }
    {
      List<Object[]> actual =
          em.createQuery("select h,i.id from Item i right join i.hero h").getResultList();
      assertThat(actual).isNotEmpty();
      assertThat(actual.get(0)[0]).isInstanceOf(HeroCard.class);
      assertThat(actual.get(0)[1]).isInstanceOf(String.class);
      System.out.println(HeroItemClass.from(actual));
    }
  }

  private <T> List<T> select(String jql, Class<T> clazz) {
    System.out.println(jql);
    List<T> actual = em.createQuery(jql, clazz).getResultList();
    System.out.println("  " + actual);
    return actual;
  }
}
