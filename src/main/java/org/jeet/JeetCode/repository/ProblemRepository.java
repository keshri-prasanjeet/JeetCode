package org.jeet.JeetCode.repository;

import org.jeet.JeetCode.domain.entities.problemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends CrudRepository<problemEntity, String> {
}
