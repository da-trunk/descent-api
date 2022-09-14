package org.datrunk.descent.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.datrunk.descent.entities.embedded.Traits;
import org.datrunk.naked.entities.bowman.annotation.InlineAssociation;
import org.datrunk.naked.entities.bowman.annotation.LinkedResource;
import org.datrunk.naked.entities.bowman.annotation.RemoteResource;

@Entity
@RemoteResource("/monsters")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonsterCard extends CreatureCard {
  @Column(name = "elite")
  @Getter(onMethod_ = {@InlineAssociation})
  private Traits eliteTraits;

  @ManyToMany(fetch = FetchType.LAZY)
  @Getter(onMethod_ = {@LinkedResource})
  private Set<Skill> eliteSkills = new HashSet<>();

  public MonsterCard(String id, int health, int stamina, int move, int armor) {
    super(id);
    setTraits(new Traits(health, move, armor));
  }
}
