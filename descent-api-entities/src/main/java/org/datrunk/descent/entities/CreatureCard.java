package org.datrunk.descent.entities;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.datrunk.descent.entities.embedded.Traits;
import org.datrunk.naked.entities.IdClass;
import org.datrunk.naked.entities.bowman.annotation.InlineAssociation;
import org.datrunk.naked.entities.bowman.annotation.LinkedResource;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public class CreatureCard extends IdClass<String> {
  @Id
  @Column(updatable = false)
  @Nonnull
  private String id;

  @Nonnull
  @Getter(onMethod_ = {@InlineAssociation})
  private Traits traits;

  @ManyToMany(fetch = FetchType.LAZY)
  @Getter(onMethod_ = {@LinkedResource})
  private Set<Skill> skills = new HashSet<>();

  public CreatureCard(String id) {
    this.id = id;
  }
}
