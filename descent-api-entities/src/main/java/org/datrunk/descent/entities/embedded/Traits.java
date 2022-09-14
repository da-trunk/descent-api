package org.datrunk.descent.entities.embedded;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.datrunk.descent.entities.Dice;
import org.datrunk.naked.entities.bowman.annotation.InlineAssociation;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Traits {
  @Nonnull private int health;
  @Nonnull private int armor;
  @Nonnull private int moves;

  @ElementCollection
  //  @Enumerated(EnumType.STRING)
  //  @org.hibernate.annotations.SetId(generator = "identity", type = @Type(type = "int"))
  @MapKeyEnumerated(EnumType.STRING)
  @MapKeyColumn(name = "dice")
  @Column(name = "count", nullable = false)
  @Getter(onMethod_ = {@InlineAssociation})
  private Map<Dice, Integer> attack = new HashMap<>();

  @ElementCollection
  @MapKeyEnumerated(EnumType.STRING)
  @MapKeyColumn(name = "dice")
  @Column(name = "count", nullable = false)
  @Getter(onMethod_ = {@InlineAssociation})
  private Map<Dice, Integer> range = new HashMap<>();

  @ElementCollection
  @MapKeyEnumerated(EnumType.STRING)
  @MapKeyColumn(name = "dice")
  @Column(name = "count", nullable = false)
  @Getter(onMethod_ = {@InlineAssociation})
  private Map<Dice, Integer> magic = new HashMap<>();

  public Traits(int health, int moves, int armor) {
    this.health = health;
    this.moves = moves;
    this.armor = armor;
  }

  public void addAttack(Dice dice) {
    attack.compute(dice, (k, v) -> v == null ? 1 : v + 1);
  }

  public void addRange(Dice dice) {
    range.compute(dice, (k, v) -> v == null ? 1 : v + 1);
  }

  public void addMagic(Dice dice) {
    magic.compute(dice, (k, v) -> v == null ? 1 : v + 1);
  }
}
