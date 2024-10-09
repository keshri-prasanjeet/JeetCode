package org.jeet.JeetCode.controllers;

import org.jeet.JeetCode.Utility.SignUpDataValidatorDEPRECATED;
import org.jeet.JeetCode.domain.dto.MePageDTO;
import org.jeet.JeetCode.domain.entities.ProblemEntity;
import org.jeet.JeetCode.domain.entities.UserEntity;
import org.jeet.JeetCode.services.ProblemService;
import org.jeet.JeetCode.services.SubmissionService;
import org.jeet.JeetCode.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ViewController {

    private UserService userService;
    private SignUpDataValidatorDEPRECATED signUpDataValidator;
    private ProblemService problemService;
    private SubmissionService submissionService;
    public ViewController(UserService userService, ProblemService problemService, SubmissionService submissionService) {
        this.userService = userService;
        this.problemService = problemService;
        this.submissionService = submissionService;
    }

    @GetMapping(path = "/problem/{problemId}")
    public ResponseEntity<Map<String, Object>> getProblem(@PathVariable("problemId") String problemId) {
        Map<String, Object> response = new HashMap<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        try {
            ProblemEntity problem = problemService.findProblem(problemId);
            if (problem == null) {
                response.put("error", "Could not find the problem");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("problemData", problem);
            response.put("username", username); // Assuming you have a method to get the username
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


//    @GetMapping(path = "/problems")
//    public Map<String,Object> listAllProblems(){
//        Map<String,Object> response = new HashMap<>();
//        List<ProblemEntity> problemEntityList = problemService.listAllProblems();
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username;
//        if(principal instanceof UserDetails) {
//            username = ((UserDetails) principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
//        System.out.println(problemEntityList);
//        System.out.println(username);
//        response.put("problemsList",problemEntityList);
//        response.put("username",username);
//        System.out.println(response);
//        return response;
//    }

//    @GetMapping(path = "/logout")
//    public String logoutPage(){
//        return "logout";
//    }
//
//    @GetMapping("/problems/submissions/{problemId}")
//    public String getSubmissionsPage(@PathVariable("problemId") String problemId, Model model) {
//        List<SubmissionEntity> submissions = submissionService.findByProblemId(problemId);
//        model.addAttribute("problemId", problemId);
//        model.addAttribute("submissions", submissions);
//        return "submission"; // Make sure this matches the Thymeleaf template file name
//    }
//
    @GetMapping("/me")
    public MePageDTO myProfile(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserEntity user = userService.findById(username);
        MePageDTO mePageDTO = new MePageDTO();
        mePageDTO.setFullName(user.getFullName());
        mePageDTO.setEmailId(user.getEmailId());
        mePageDTO.setSubmissionCount(user.getSubmissionCount());
        return mePageDTO;
    }

}

