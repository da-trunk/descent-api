package org.datrunk.descent.entities.random;

import java.util.stream.Collectors;
import org.datrunk.descent.entities.Hero;
import org.datrunk.descent.entities.embedded.Traits;
import org.datrunk.naked.entities.random.Randomizer;
import org.datrunk.naked.entities.random.RepeatingRandomizer;

public class HeroRandomizer extends RepeatingRandomizer<Hero> {
  private final SkillRandomizer skillRandomizer;

  public HeroRandomizer(int poolSize) {
    super(poolSize);
    skillRandomizer = new SkillRandomizer(3);
  }

  @Override
  protected Hero get() throws Randomizer.Exception {
    Hero result =
        new Hero(
            faker.lordOfTheRings().character(),
            new Traits(random.nextInt(20), random.nextInt(6), random.nextInt(1), random.nextInt(6)),
            faker.yoda().quote());
    result.setSkills(skillRandomizer.getAll().stream().collect(Collectors.toSet()));
    return result;
  }
}
