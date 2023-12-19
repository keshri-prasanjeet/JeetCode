package org.jeet.JeetCode.services;

import java.util.List;

import org.jeet.JeetCode.domain.entities.ProblemEntity;

public interface ProblemService {
    List<ProblemEntity> listAllProblems();

    ProblemEntity findProblem(String problemId);
}
