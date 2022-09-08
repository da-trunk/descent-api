package org.datrunk.descent.db;

import java.sql.SQLException;
import javax.sql.DataSource;
import liquibase.exception.LiquibaseException;
import org.datrunk.naked.db.oracle.OracleTestContainer;
import org.datrunk.naked.entities.random.Randomizer.Exception;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@Import({Application.Config.class})
public class Application {
  static class Config {
    @Bean
    DataSource dataSource(OracleTestContainer db, Initializer initializer)
        throws LiquibaseException, SQLException, Exception {
      initializer.init();
      return db.getDataSource();
    }
  }

  //  @Autowired private JdbcTemplate jdbcTemplate;

  @SuppressWarnings("resource")
  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.addInitializers(new OracleTestContainer.Factory());
    app.run(args);
  }

  //  @PostConstruct
  //  private void initDb() {
  //    assert (jdbcTemplate != null);
  //  }
}
