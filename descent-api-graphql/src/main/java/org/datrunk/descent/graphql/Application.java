package org.datrunk.descent.graphql;

import java.io.PrintStream;

import javax.persistence.EntityManager;

import org.datrunk.descent.entities.Hero;
import org.datrunk.descent.server.repo.HeroRepo;
import org.datrunk.descent.server.repo.TestController;
import org.datrunk.naked.server.repo.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.ForwardedHeaderFilter;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Import({ Application.Config.class })
@Log4j2
public class Application extends SpringBootServletInitializer {
  @SpringBootConfiguration
  @EntityScan(basePackageClasses = { Hero.class })
  @EnableJpaRepositories(basePackageClasses = {
      HeroRepo.class }, repositoryBaseClass = BaseRepositoryImpl.class, considerNestedRepositories = true)
  @EnableHypermediaSupport(type = HypermediaType.HAL)
  @EnableAspectJAutoProxy
  @EnableTransactionManagement
  @EnableAutoConfiguration
  @ComponentScan(basePackageClasses = { TestController.class })
  public static class Config {
    @Bean
    RepositoryRestConfigurer repositoryRestConfigurer(EntityManager entityManager) {
      return RepositoryRestConfigurer.withConfig(config -> {
        config.exposeIdsFor(
            entityManager.getMetamodel().getEntities().stream().map(javax.persistence.metamodel.Type::getJavaType).toArray(Class[]::new));
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

  @SuppressWarnings("resource")
  public static void main(String[] args) throws Exception {
    // This is a jar deploy. It needs to be different if deploying with a war.
    SpringApplication app = new SpringApplication(Application.class);
    app.setBanner(new Banner());
    app.run(args);
  }

  /** Custom banner. Useful for outputting properties. */
  public static class Banner implements org.springframework.boot.Banner {
    @Override
    public void printBanner(Environment env, Class<?> sourceClass, PrintStream out) {
      log.info("-------------------- Descent API ----------------------- ");
      if (env.getProperty("spring.datasource.url") == null) {
        throw new RuntimeException("'spring.datasource.url' is not configured! This should be specified in application.yml.");
      }
      log.info("Starting server at [http://localhost:{}{}]", env.getProperty("server.port"), env.getProperty("spring.data.rest.base-path"));
    }
  }

  @Bean
  FilterRegistrationBean<ForwardedHeaderFilter> loggingFilter() {
    FilterRegistrationBean<ForwardedHeaderFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new ForwardedHeaderFilter());
    return registrationBean;
  }
}
