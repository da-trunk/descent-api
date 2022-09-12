package org.datrunk.descent.entities.random;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.datrunk.descent.entities.HeroCard;
import org.datrunk.descent.entities.Skill;
import org.datrunk.descent.entities.embedded.Traits;
import org.datrunk.naked.entities.random.Randomizer;
import org.datrunk.naked.entities.random.RepeatingRandomizer;
import org.datrunk.naked.entities.random.UniqueRandomizer;

import lombok.Getter;

@Getter
public class HeroCardRandomizer extends RepeatingRandomizer<HeroCard> {
  private final SkillRandomizer skillRandomizer;
  private final UniqueRandomizer<Skill> abilityRandomizer;
  private final TraitsRandomizer traitsRandomizer;

  public HeroCardRandomizer(int poolSize, Consumer<Skill> persistFn) {
    super(poolSize);
    skillRandomizer = new SkillRandomizer(3);
    skillRandomizer.setUpdater(skill -> {
      persistFn.accept(skill);
      return skill;
    });
    abilityRandomizer = UniqueRandomizer.createFromFaker(faker -> {
      Skill result = new Skill(faker.yoda().quote());
      persistFn.accept(result);
      return result;
    });
    traitsRandomizer = new TraitsRandomizer();
  }

  @Override
  protected HeroCard get() throws Randomizer.Exception {
    HeroCard result = new HeroCard(faker.lordOfTheRings().character(), new Traits(random.nextInt(20), random.nextInt(6), random.nextInt(1)),
        random.nextInt(6), abilityRandomizer.getRandomValue());
    result.setSkills(getRandomSkills());
    result.setTraits(traitsRandomizer.getRandomValue());
    return result;
  }

  Set<Skill> getRandomSkills() {
    int numSkills = random.nextInt(4);
    return IntStream.range(0, numSkills).mapToObj(i -> {
      try {
        return skillRandomizer.getRandomValue();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }).collect(Collectors.toSet());
  }
}
