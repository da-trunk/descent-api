package org.datrunk.descent.entities.random;

import org.datrunk.descent.entities.Dice;
import org.datrunk.descent.entities.embedded.Traits;
import org.datrunk.naked.entities.random.AbstractRandomizer;
import org.datrunk.naked.entities.random.EnumRandomizer;

public class TraitsRandomizer extends AbstractRandomizer<Traits> {
  private EnumRandomizer<Dice> diceRandomizer = new EnumRandomizer<>(Dice.class);

  protected TraitsRandomizer() {
    super(0);
  }

  @Override
  protected Traits get() throws Exception {
    Traits result = new Traits(random.nextInt(12), random.nextInt(6), random.nextInt(2));
    double probability;
    do {
      probability = random.nextDouble();
      if (probability < 0.2 + 0.1 * result.getAttack().size())
        result.addAttack(diceRandomizer.getRandomValue());
      else if (probability < 0.4 + 0.1 * result.getRange().size())
        result.addRange(diceRandomizer.getRandomValue());
      else result.addMagic(diceRandomizer.getRandomValue());
    } while (probability < 0.6);
    return result;
  }
}
