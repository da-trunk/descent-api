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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Traits {
  @Nonnull private int healthMax;
  @Nonnull private int health;
  @Nonnull private int staminaMax;
  @Nonnull private int stamina;
  @Nonnull private int armor;
  @Nonnull private int moveMax;
  @Nonnull private int move;
  private int attack;
  private int range;
  private int magic;
  
  public Traits(int health, int stamina, int move, int armor) {
    healthMax = this.health = health;
    staminaMax = this.stamina = stamina;
    moveMax = this.move = move;
    this.armor = armor;
    this.attack = this.range = this.magic = 0;
  }

  protected void setHealthMax(int healthMax) {
    this.healthMax = healthMax;
    this.health = healthMax;
  }
  
  protected void setHealth(int health) {
    this.health = health;
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
