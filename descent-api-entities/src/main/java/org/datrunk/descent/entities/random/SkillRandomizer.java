package org.datrunk.descent.entities.random;

import org.datrunk.descent.entities.Skill;
import org.datrunk.naked.entities.random.Randomizer;
import org.datrunk.naked.entities.random.RepeatingRandomizer;

public class SkillRandomizer extends RepeatingRandomizer<Skill> {
  public SkillRandomizer(int poolSize) {
    super(poolSize);
  }

  @Override
  protected Skill get() throws Randomizer.Exception {
    return new Skill(faker.dune().title());
  }
}
