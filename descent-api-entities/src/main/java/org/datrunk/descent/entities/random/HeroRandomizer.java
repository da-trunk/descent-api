package org.datrunk.descent.entities.random;

import com.github.javafaker.Name;
import org.datrunk.descent.entities.Hero;
import org.datrunk.descent.entities.embedded.Traits;
import org.datrunk.naked.entities.random.Randomizer;
import org.datrunk.naked.entities.random.RepeatingRandomizer;

public class HeroRandomizer extends RepeatingRandomizer<Hero> {
  public HeroRandomizer(int poolSize) {
    super(poolSize);
  }

  @Override
  protected Hero get() throws Randomizer.Exception {
    final Name name = faker.name();
    return new Hero(
        name.firstName(),
        new Traits(random.nextInt(20), random.nextInt(6), random.nextInt(1), random.nextInt(6)),
        "");
  }
}
