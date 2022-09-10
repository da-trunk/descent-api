package org.datrunk.descent.entities.embedded;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
  @Enumerated(EnumType.STRING)
  @Getter(onMethod_ = {@InlineAssociation})
  private Set<Dice> attack = new HashSet<>();

  @ElementCollection
  @Enumerated(EnumType.STRING)
  @Column(name = "\"range\"", nullable = false)
  @Getter(onMethod_ = {@InlineAssociation})
  private Set<Dice> range = new HashSet<>();

  @ElementCollection
  @Enumerated(EnumType.STRING)
  @Getter(onMethod_ = {@InlineAssociation})
  private Set<Dice> magic = new HashSet<>();

  public Traits(int health, int moves, int armor) {
    this.health = health;
    this.moves = moves;
    this.armor = armor;
  }
}
