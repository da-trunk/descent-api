package org.datrunk.descent.server.repo;

import java.util.List;
import org.datrunk.descent.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ItemController {
  @Autowired ItemRepo itemRepo;

  @QueryMapping
  public Item itemById(@Argument String id) {
    return itemRepo.findById(id).get();
  }

  @SchemaMapping
  public List<Item> itemsByHero(String id) {
    return itemRepo.findByHeroId(id);
  }
}
