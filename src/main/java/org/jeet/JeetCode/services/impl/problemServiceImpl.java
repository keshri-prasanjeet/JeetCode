package org.jeet.JeetCode.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.jeet.JeetCode.repository.ProblemRepository;
import org.jeet.JeetCode.domain.entities.ProblemEntity;
import org.jeet.JeetCode.services.ProblemService;
import org.springframework.stereotype.Service;

@Service
public class problemServiceImpl implements ProblemService {

    private ProblemRepository problemRepository;

    public problemServiceImpl(ProblemRepository problemRepository){
        this.problemRepository = problemRepository;
    }

    @Override
    public List<ProblemEntity> listAllProblems() {
        return StreamSupport.stream(problemRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public ProblemEntity findProblem(String problemId) {
        Optional<ProblemEntity> problem = problemRepository.findById(problemId);
        if(problem.isEmpty())
            return null;
        return problem.get();
    }
}
