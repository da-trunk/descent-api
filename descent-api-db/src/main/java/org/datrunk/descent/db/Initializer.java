package org.datrunk.descent.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.datrunk.descent.entities.random.HeroRandomizer;
import org.datrunk.naked.entities.random.Randomizer.Exception;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class Initializer {
  @PersistenceContext private EntityManager em;

  public void init() throws Exception {
    HeroRandomizer heros = new HeroRandomizer(5);
    em.persist(heros.getAll());
    em.flush();
  }
}
