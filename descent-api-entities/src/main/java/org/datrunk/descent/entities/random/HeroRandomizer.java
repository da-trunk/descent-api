package org.datrunk.descent.entities.random;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import org.datrunk.descent.entities.Hero;
import org.datrunk.descent.entities.Skill;
import org.datrunk.descent.entities.embedded.Traits;
import org.datrunk.naked.entities.random.Randomizer;
import org.datrunk.naked.entities.random.RepeatingRandomizer;

@Getter
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
            new Traits(random.nextInt(20), random.nextInt(6), random.nextInt(6), random.nextInt(1)),
            faker.yoda().quote());
    result.setSkills(getRandomSkills());
    return result;
  }

  Set<Skill> getRandomSkills() {
    int numSkills = random.nextInt(4);
    return IntStream.range(0, numSkills)
        .mapToObj(
            i -> {
              try {
                return skillRandomizer.getRandomValue();
              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            })
        .collect(Collectors.toSet());
  }
}
