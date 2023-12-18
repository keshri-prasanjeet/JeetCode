package org.jeet.JeetCode.services;

import java.util.List;

import org.jeet.JeetCode.domain.entities.problemEntity;

public interface ProblemService {
    List<problemEntity> listAllProblems();
}
