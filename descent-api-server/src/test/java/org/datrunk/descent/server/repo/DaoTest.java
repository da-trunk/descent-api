package org.datrunk.descent.server.repo;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.datrunk.descent.entities.*;
import org.datrunk.naked.db.mysql.MySqlTestContainer;
import org.datrunk.naked.server.repo.BaseRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.ForwardedHeaderFilter;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ExtendWith({SpringExtension.class})
@EnableTransactionManagement
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
    initializers = {MySqlTestContainer.Factory.class},
    classes = {DaoTest.Config.class})
@EnableConfigurationProperties({DataSourceProperties.class})
@ActiveProfiles("test")
//@Disabled(
//    "These test work only if the DB is running.  Execute mvn spring-boot:run -pl descent-api-server before these tests")
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
    //        boolean initialized = false;
    //
    //        public DataSource dataSource(MySqlTestContainer db) throws LiquibaseException,
    // SQLException {
    //            if (!initialized) {
    //                initialized = true;
    //            }
    //            return db.getDataSource();
    //        }

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
  void before(ConfigurableApplicationContext ctx) {
    assertThat(em).isNotNull();
    MySqlTestContainer.Factory factory = new MySqlTestContainer.Factory();
    factory.initialize(ctx);
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
          em.createQuery("select i from Item i right join i.hero h where h.id = :id ")
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
