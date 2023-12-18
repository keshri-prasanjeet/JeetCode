package org.jeet.JeetCode.controllers;

import java.util.List;
import org.jeet.JeetCode.domain.entities.problemEntity;
import org.jeet.JeetCode.services.ProblemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JeetCodeController {

    private ProblemService problemService;
    public JeetCodeController(ProblemService problemService){
        this.problemService = problemService;

    }

    @GetMapping(path = "/hello")
    public String helloWorld(){
        return "Hello world";
    }

    @GetMapping(path = "/problems")
    public List<problemEntity> listAllProblems(){
        return problemService.listAllProblems();
    }
}
