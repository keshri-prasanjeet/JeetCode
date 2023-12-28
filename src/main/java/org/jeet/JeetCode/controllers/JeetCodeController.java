package org.jeet.JeetCode.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.jeet.JeetCode.Utility.JwtUtil;
import org.jeet.JeetCode.Utility.SignUpDataValidator;
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
    private SignUpDataValidator signUpDataValidator;
    private UserService userService;
    private JwtUtil jwtUtil;
    private SubmissionService submissionService;
    public JeetCodeController(ProblemService problemService,
                              SignUpDataValidator signUpDataValidator,
                              UserService userService,
                              JwtUtil jwtUtil,
                              SubmissionService submissionService){
        this.problemService = problemService;
        this.signUpDataValidator = signUpDataValidator;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.submissionService = submissionService;
    }

    @GetMapping(path = "/hello")
    public String helloWorld(){
        return "Hello world";
    }

//    @GetMapping(path = "/problems")
//    public List<ProblemEntity> listAllProblems(){
//        return problemService.listAllProblems();
//    }

//    @GetMapping(path = "/problem/{problemId}")
//    public ResponseEntity getProblem(@PathVariable("problemId") String problemId){
//        ProblemEntity problem = problemService.findProblem(problemId);
//        if(problem==null)
//            return new ResponseEntity("Could not find that problem", HttpStatus.BAD_REQUEST);
//        return new ResponseEntity(problem, HttpStatus.OK);
//    }

//    @PostMapping(path = "/signup/")
//    public ResponseEntity singUpUser(@RequestBody SignUpRequest signUpRequest){
//        if(!signUpDataValidator.checkIfSignUpPostDataIsValid(signUpRequest))
//            return new ResponseEntity("Username, full name or password is missing", HttpStatus.BAD_REQUEST);
//        userService.signUpUser(signUpRequest);
//        return new ResponseEntity("User signed up successfully", HttpStatus.OK);
//    }

//    @PostMapping(path = "/login/")
//    public ResponseEntity loginUser(@RequestBody LoginRequest loginRequest){
//        if(userService.authenticate(loginRequest)){
//            String token = jwtUtil.generateToken(loginRequest.getUserName());
//            return new ResponseEntity(token, HttpStatus.OK);
//        }
//        return new ResponseEntity("Wrong credentials", HttpStatus.UNAUTHORIZED);
//    }


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
            /* to do change the return type of addSubmission to SubmissionEntity and add that to userEntity list */
            submissionService.addSubmission(trimmedCode, problemId, username);
            return ResponseEntity.ok(trimmedCode);
        } else
            System.err.println("did not find Authorization header");

        return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
    }
}
