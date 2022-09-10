package org.datrunk.descent.entities.random;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import org.datrunk.descent.entities.HeroCard;
import org.datrunk.descent.entities.Skill;
import org.datrunk.descent.entities.embedded.Traits;
import org.datrunk.naked.entities.random.Randomizer;
import org.datrunk.naked.entities.random.RepeatingRandomizer;

@Getter
public class HeroCardRandomizer extends RepeatingRandomizer<HeroCard> {
  private final SkillRandomizer skillRandomizer;

  public HeroCardRandomizer(int poolSize, Consumer<Skill> persistFn) {
    super(poolSize);
    skillRandomizer = new SkillRandomizer(3);
    skillRandomizer.setUpdater(
        skill -> {
          persistFn.accept(skill);
          return skill;
        });
  }

  @Override
  protected HeroCard get() throws Randomizer.Exception {
    HeroCard result =
        new HeroCard(
            faker.lordOfTheRings().character(),
            new Traits(random.nextInt(20), random.nextInt(6), random.nextInt(1)),
            random.nextInt(6),
            skillRandomizer.getUniqueRandomValue());
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
