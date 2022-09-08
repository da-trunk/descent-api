// package org.datrunk.descent.db;
//
// import javax.persistence.EntityManager;
// import javax.transaction.Transactional;
// import org.datrunk.descent.entities.random.HeroRandomizer;
// import org.datrunk.naked.entities.random.Randomizer.Exception;
//
// public class Initializer {
//  @Transactional
//  public void init(EntityManager em) throws Exception {
//    HeroRandomizer heros = new HeroRandomizer(5);
//    em.persist(heros.getAll());
//    em.flush();
//  }
// }
