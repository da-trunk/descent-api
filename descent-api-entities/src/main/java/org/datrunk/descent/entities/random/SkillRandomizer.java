package org.datrunk.descent.entities.random;

import com.github.javafaker.Yoda;
import org.datrunk.descent.entities.Skill;
import org.datrunk.naked.entities.random.Randomizer;
import org.datrunk.naked.entities.random.RepeatingRandomizer;

public class SkillRandomizer extends RepeatingRandomizer<Skill> {
  public SkillRandomizer(int poolSize) {
    super(poolSize);
  }

  @Override
  protected Skill get() throws Randomizer.Exception {
    final Yoda name = faker.yoda();
    return new Skill(name.quote());
  }
}
