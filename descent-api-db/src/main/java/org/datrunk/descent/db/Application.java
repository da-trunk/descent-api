package org.datrunk.descent.db;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.datrunk.descent.entities.HeroCard;
import org.datrunk.descent.entities.Skill;
import org.datrunk.descent.entities.random.HeroCardRandomizer;
import org.datrunk.naked.db.mysql.MySqlTestContainer;
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

@SpringBootApplication
@Import({Application.Config.class})
public class Application implements ApplicationRunner {
  @EnableTransactionManagement
  @EntityScan(basePackageClasses = {HeroCard.class})
  @EnableAutoConfiguration
  static class Config {
    @Bean
    DataSource dataSource(MySqlTestContainer db) {
      return db.getDataSource();
    }
  }

  @Autowired private JdbcTemplate jdbcTemplate;
  @PersistenceContext private EntityManager em;

  @SuppressWarnings("resource")
  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.addInitializers(new MySqlTestContainer.Factory());
    app.run(args);
  }

  @PostConstruct
  public void initDb() throws Exception {
    assert (jdbcTemplate != null);
  }

  @Override
  @Transactional
  public void run(ApplicationArguments args) throws Exception {
    deleteAll();
    HeroCardRandomizer heroRandomizer = new HeroCardRandomizer(5, em::persist);
    heroRandomizer.getAll().stream().forEach(em::persist);
    em.flush();
    List<HeroCard> actual =
        em.createQuery("select h from HeroCard h", HeroCard.class).getResultList();
    System.out.println(actual);
  }

  private void deleteAll() {
    List<HeroCard> heroes =
        em.createQuery("select h from HeroCard h", HeroCard.class).getResultList();
    for (HeroCard HeroCard : heroes) em.remove(HeroCard);
    List<Skill> skills = em.createQuery("select s from Skill s", Skill.class).getResultList();
    for (Skill skill : skills) em.remove(skill);
    em.flush();
  }
}
