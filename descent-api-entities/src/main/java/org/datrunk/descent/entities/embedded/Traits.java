package org.datrunk.descent.entities.embedded;

import javax.annotation.Nonnull;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Traits {
  @Nonnull private int healthMax;
  private int health;
  @Nonnull private int staminaMax;
  private int stamina;
  @Nonnull private int armor;
  @Nonnull private int moveMax;
  private int move;
  private int attack;
  private int range;
  private int magic;

  protected void setHealthMax(int healthMax) {
    this.healthMax = healthMax;
    this.health = healthMax;
  }

  protected void setStaminaMax(int staminaMax) {
    this.staminaMax = staminaMax;
    this.stamina = staminaMax;
  }

  protected void setMoveMax(int moveMax) {
    this.moveMax = moveMax;
    this.move = moveMax;
  }
}
