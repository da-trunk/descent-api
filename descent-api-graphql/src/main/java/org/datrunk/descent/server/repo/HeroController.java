package org.datrunk.descent.server.repo;

import java.util.Set;
import org.datrunk.descent.entities.Hero;
import org.datrunk.descent.entities.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HeroController {
  @Autowired HeroRepo heroRepo;

  @QueryMapping
  public Hero heroById(@Argument String id) {
    return heroRepo.findById(id).get();
  }

  @SchemaMapping
  public Set<Skill> skills(Hero hero) {
    return hero.getSkills();
  }
}
