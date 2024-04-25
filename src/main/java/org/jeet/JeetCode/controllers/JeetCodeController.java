package org.jeet.JeetCode.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.jeet.JeetCode.Utility.SignUpDataValidatorDEPRECATED;
import org.jeet.JeetCode.domain.entities.AdminDetail;
import org.jeet.JeetCode.services.ProblemService;
import org.jeet.JeetCode.services.SubmissionService;
import org.jeet.JeetCode.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JeetCodeController {

    private ProblemService problemService;
    private SignUpDataValidatorDEPRECATED signUpDataValidator;
    private UserService userService;
    private SubmissionService submissionService;
    private AdminDetail adminDetail;

    public JeetCodeController(ProblemService problemService,
                              SignUpDataValidatorDEPRECATED signUpDataValidator,
                              UserService userService,
                              SubmissionService submissionService,
                              AdminDetail adminDetail) {
        this.adminDetail = adminDetail;
        this.problemService = problemService;
        this.signUpDataValidator = signUpDataValidator;
        this.userService = userService;
        this.submissionService = submissionService;
    }

    @GetMapping(path = "/hello")
    public String helloWorld() {
        return "Hello world";
    }


    //TODO cleanup this function and move business logic to some util function
    @PostMapping(path = "/problem-submit/{problemId}")
    public ResponseEntity<String> submitSolution(@PathVariable("problemId") String problemId, @RequestBody @NotNull String rawCode, HttpServletRequest request) {
        String trimmedCode = rawCode.replaceAll("^\"|\"$", "");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        if(trimmedCode.isEmpty()) {
            return ResponseEntity.ok("There is no code");
        } else if(trimmedCode.length() > 0) {
            userService.updateSubmissionCount(username);
            /* TODO change the return type of addSubmission to SubmissionEntity and add that to userEntity list */
            submissionService.addSubmission(trimmedCode, problemId, username);
            return ResponseEntity.ok(trimmedCode);
        } else
            System.err.println("did not find Authorization header");

        return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
    }
//    @PostMapping(path = "/set-language")
//    public ResponseEntity<String> setLanguage(@RequestBody @NotNull Integer languageCode){
//        Integer lCode = languageCode;
//    }
}
