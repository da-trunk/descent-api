package org.datrunk.descent.server.repo;

import org.datrunk.descent.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SkillRepo extends JpaRepository<Skill, Long> {}
