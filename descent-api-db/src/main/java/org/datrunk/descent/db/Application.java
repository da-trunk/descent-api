package org.datrunk.descent.db;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.datrunk.descent.entities.Hero;
import org.datrunk.descent.entities.random.HeroRandomizer;
import org.datrunk.naked.db.oracle.OracleTestContainer;
import org.datrunk.naked.entities.random.Randomizer.Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication()
@Import({Application.Config.class})
public class Application implements ApplicationRunner {
  @EnableTransactionManagement
  @EntityScan(basePackageClasses = {Hero.class})
  @EnableAutoConfiguration
  static class Config {
    @Bean
    DataSource dataSource(OracleTestContainer db) {
      //      (new Initializer()).init(em);
      return db.getDataSource();
    }
  }

  @Autowired private JdbcTemplate jdbcTemplate;
  @PersistenceContext private EntityManager em;

  @SuppressWarnings("resource")
  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.addInitializers(new OracleTestContainer.Factory());
    app.run(args);
  }

  @PostConstruct
  public void initDb() throws Exception {
    assert (jdbcTemplate != null);
  }

  @Override
  @Transactional
  public void run(ApplicationArguments args) throws Exception {
    HeroRandomizer heros = new HeroRandomizer(5);
    em.persist(new ArrayList<>(heros.getAll()));
    em.flush();
  }
}
