package org.datrunk.descent.db;

import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import liquibase.exception.LiquibaseException;
import org.datrunk.naked.db.oracle.OracleTestContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication()
@Import({Application.Config.class})
public class Application {
  static class Config {
    @Bean
    DataSource dataSource(OracleTestContainer db) throws LiquibaseException, SQLException {
      return db.getDataSource();
    }
  }

  @Autowired private JdbcTemplate jdbcTemplate;

  @SuppressWarnings("resource")
  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.addInitializers(new OracleTestContainer.Factory());
    app.run(args);
  }

  @PostConstruct
  private void initDb() {
    assert (jdbcTemplate != null);
  }
}
