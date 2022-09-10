package org.datrunk.descent.entities;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.datrunk.descent.entities.embedded.Traits;
import org.datrunk.naked.entities.bowman.annotation.InlineAssociation;
import org.datrunk.naked.entities.bowman.annotation.RemoteResource;

@Entity
@RemoteResource("/heroes")
@Getter
@Setter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeroCard extends CreatureCard {
  @OneToOne(optional = false, orphanRemoval = true)
  @Getter(onMethod_ = {@InlineAssociation})
  private Skill ability;

  @Nonnull private int stamina;

  public HeroCard(String id, Traits traits, int stamina, Skill ability) {
    super(id);
    setTraits(traits);
    this.stamina = stamina;
    this.ability = ability;
  }
}
