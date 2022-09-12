import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.datrunk.descent.entities.HeroCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest(classes = DaoTest.Config.class, webEnvironment = WebEnvironment.NONE)
@ExtendWith({ SpringExtension.class })
@ActiveProfiles("test")
@Transactional
public class DaoTest {
  @EnableTransactionManagement
  @EntityScan(basePackageClasses = { HeroCard.class })
  @EnableAutoConfiguration
  static class Config {
  }

  private @PersistenceContext EntityManager em;

  @Test
  void testQueries() {
    assertThat(em).isNotNull();
    select("select h from HeroCard h left join fetch h.skills", HeroCard.class);
    List<HeroCard> actual = select("select h from Skill s join Hero h", HeroCard.class);
    assertThat(actual).isNotEmpty();
  }
  
  private <T> List<T> select(String jql, Class<T> clazz) {
    System.out.println(jql);
    List<T> actual = em.createQuery(jql, clazz).getResultList();
    System.out.println("  " + actual);
    return actual;
  }
}
