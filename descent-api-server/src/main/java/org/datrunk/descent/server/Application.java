package org.datrunk.descent.server;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.datrunk.descent.entities.Hero;
import org.datrunk.descent.server.config.JerseyConfig;
import org.datrunk.descent.server.repo.HeroRepo;
import org.datrunk.naked.server.repo.BaseRepositoryImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.ForwardedHeaderFilter;

@SpringBootApplication
@Import({Application.Config.class})
// @PropertySources({
//  @PropertySource("classpath:application-server.yml"),
//  @PropertySource("classpath:application-default.yml")
// })
public class Application extends SpringBootServletInitializer {
  private static Logger log = LogManager.getLogger();

  @SpringBootConfiguration
  @EntityScan(basePackageClasses = {Hero.class})
  @EnableJpaRepositories(
      basePackageClasses = {HeroRepo.class},
      repositoryBaseClass = BaseRepositoryImpl.class,
      considerNestedRepositories = true)
  @EnableHypermediaSupport(type = HypermediaType.HAL)
  @EnableAspectJAutoProxy
  @EnableTransactionManagement
  @EnableAutoConfiguration
  public static class Config {
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

  public static void main(String[] args) throws Exception {
    SpringApplication app = new SpringApplication(Application.class);
    ConfigurableApplicationContext ctx = app.run(args);
  }

  @RestController
  public static class TestController {
    @GetMapping("/test/controller")
    public String handler() {
      return "success!";
    }
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

  @Bean
  FilterRegistrationBean<ForwardedHeaderFilter> loggingFilter() {
    FilterRegistrationBean<ForwardedHeaderFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new ForwardedHeaderFilter());
    return registrationBean;
  }

  @Bean
  ResourceConfig jerseyConfig() {
    return new JerseyConfig();
  }
}
