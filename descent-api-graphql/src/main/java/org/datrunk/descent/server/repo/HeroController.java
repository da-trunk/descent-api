package org.datrunk.descent.server.repo;

import java.util.List;
import java.util.Set;
import org.datrunk.descent.entities.HeroCard;
import org.datrunk.descent.entities.Item;
import org.datrunk.descent.entities.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HeroController {
  @Autowired HeroRepo heroRepo;
  @Autowired ItemRepo itemRepo;

  @QueryMapping
  public HeroCard heroById(@Argument String id) {
    return heroRepo.findById(id).get();
  }

  @QueryMapping
  public List<Item> itemsByHero(@Argument String id) {
    return itemRepo.findByHeroId(id);
  }

  @QueryMapping
  public List<HeroItem> allItemsByHero() {
    return itemRepo.findAllByHero();
  }

  @SchemaMapping
  public Set<Skill> skills(HeroCard hero) {
    return hero.getSkills();
  }
}
