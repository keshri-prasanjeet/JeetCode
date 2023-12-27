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
    @GetMapping(path = "/me")
    public ResponseEntity userProfile(){
        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping(path = "/problem-submit/{problemId}")
    public ResponseEntity<String> submitSolution(@PathVariable("problemId") String problemId, @RequestBody @NotNull String rawCode, HttpServletRequest request){
        String trimmedCode = rawCode.replaceAll("^\"|\"$", "");
        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer")) {
            String token = header.substring(7);
            String userName = jwtUtil.extractUserName(token);
            System.err.println(userName);
            if(trimmedCode.isEmpty()) {
                return ResponseEntity.ok("There is no code");
            }
            else if(trimmedCode.length()>0) {
                submissionService.addSubmission(trimmedCode, problemId, userName);
                return ResponseEntity.ok(trimmedCode);
        }
        else
            System.err.println("did not find Authorization header bruh");
        }
        return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
    }

//    @GetMapping(path = "/problems/submissions/{problemId}")
//    public List<SubmissionEntity> submissionEntityList(@PathVariable("problemId") String problemId){
//        return submissionService.findByProblemId(problemId);
//    }




}
