package org.datrunk.descent.server.repo;

import org.datrunk.descent.entities.Skill;
import org.datrunk.naked.server.repo.BaseRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SkillRepo extends BaseRepository<Skill, Long> {}
