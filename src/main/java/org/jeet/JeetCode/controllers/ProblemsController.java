package org.jeet.JeetCode.controllers;

import org.jeet.JeetCode.domain.entities.ProblemEntity;
import org.jeet.JeetCode.services.ProblemService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProblemsController {
    private ProblemService problemService;

    public ProblemsController(ProblemService problemService){
        this.problemService=problemService;
    }


    @GetMapping(path = "/problems")
    public Map<String,Object> listAllProblems(){
        Map<String,Object> response = new HashMap<>();
        List<ProblemEntity> problemEntityList = problemService.listAllProblems();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println(problemEntityList);
        System.out.println(username);
        response.put("problemsList",problemEntityList);
        response.put("username",username);
        System.out.println(response);
        return response;
    }
}
