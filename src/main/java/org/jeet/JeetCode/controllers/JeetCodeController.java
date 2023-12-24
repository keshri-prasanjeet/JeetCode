package org.jeet.JeetCode.controllers;

import java.util.List;

import org.jeet.JeetCode.Utility.JwtUtil;
import org.jeet.JeetCode.Utility.SignUpDataValidator;
import org.jeet.JeetCode.domain.entities.ProblemEntity;
import org.jeet.JeetCode.services.ProblemService;
import org.jeet.JeetCode.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JeetCodeController {

    private ProblemService problemService;
    private SignUpDataValidator signUpDataValidator;
    private UserService userService;
    private JwtUtil jwtUtil;
    public JeetCodeController(ProblemService problemService, SignUpDataValidator signUpDataValidator, UserService userService,
                              JwtUtil jwtUtil){
        this.problemService = problemService;
        this.signUpDataValidator = signUpDataValidator;
        this.userService = userService;
        this.jwtUtil = jwtUtil;

    }

    @GetMapping(path = "/hello")
    public String helloWorld(){
        return "Hello world";
    }

    @GetMapping(path = "/problems")
    public List<ProblemEntity> listAllProblems(){
        return problemService.listAllProblems();
    }

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
}
