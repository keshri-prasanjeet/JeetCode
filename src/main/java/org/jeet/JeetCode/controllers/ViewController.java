package org.jeet.JeetCode.controllers;

import java.util.List;

import org.jeet.JeetCode.Utility.JwtUtil;
import org.jeet.JeetCode.Utility.SignUpDataValidator;
import org.jeet.JeetCode.domain.entities.ProblemEntity;
import org.jeet.JeetCode.domain.entities.SignUpRequest;
import org.jeet.JeetCode.domain.entities.SubmissionEntity;
import org.jeet.JeetCode.services.ProblemService;
import org.jeet.JeetCode.services.SubmissionService;
import org.jeet.JeetCode.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class ViewController {

    private UserService userService;
    private JwtUtil jwtUtil;
    private SignUpDataValidator signUpDataValidator;
    private ProblemService problemService;
    private SubmissionService submissionService;

    public ViewController(UserService userService, JwtUtil jwtUtil, SignUpDataValidator signUpDataValidator, ProblemService problemService, SubmissionService submissionService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.signUpDataValidator = signUpDataValidator;
        this.problemService = problemService;
        this.submissionService = submissionService;
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @PostMapping(path = "/signup")
    public String singUpUser(@RequestParam String fullname, @RequestParam String username, @RequestParam String password){
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFullName(fullname);
        signUpRequest.setUserName(username);
        signUpRequest.setPassword(password);
        if(!signUpDataValidator.checkIfSignUpPostDataIsValid(signUpRequest))
            return ("Username, full name or password is missing");
        userService.signUpUser(signUpRequest);
        return "login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

//    @PostMapping("/login/")
//    public ResponseEntity<String> processLoginForm(@RequestParam String username, @RequestParam String password, HttpSession session) {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUserName(username);
//        loginRequest.setPassword(password);
//        if (userService.authenticate(loginRequest)) {
//            String token = jwtUtil.generateToken(loginRequest.getUserName());
//            session.setAttribute("token", token);
//            session.setMaxInactiveInterval(30 * 60);
//            return new ResponseEntity(HttpStatus.OK);
//        }
//        return new ResponseEntity("Wrong credentials", HttpStatus.UNAUTHORIZED);
//
//    }

//    @PostMapping("/login")
//    public ResponseEntity<String> processLoginForm(Authentication authentication, HttpSession session, HttpServletResponse response) {
//        try{
//            if (authentication != null && authentication.isAuthenticated()) {
//                String username = authentication.getName();
//                String token = jwtUtil.generateToken(username);
//                session.setAttribute("token", token);
//                session.setMaxInactiveInterval(30 * 60);
//                Cookie tokenCookie = new Cookie("token", token);
//                tokenCookie.setHttpOnly(true);
//                tokenCookie.setSecure(true);
//                response.addCookie(tokenCookie);
//                System.out.println("Successfully logged in for "+ username);
//
//                return new ResponseEntity<>(HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
//            }
//        } catch (Exception e){
//            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping(path = "/problem/{problemId}")
    public String getProblem(@PathVariable("problemId") String problemId, Model model){
        ProblemEntity problem = problemService.findProblem(problemId);
        if(problem==null)
            return "Could not find that problem";
        model.addAttribute("problem", problem);
        return "problem";
    }


    @GetMapping(path = "/problems")
    public String listAllProblems(Model model){
        List<ProblemEntity> problemEntityList = problemService.listAllProblems();
        model.addAttribute("problems", problemEntityList);
        return "problems";
    }

    @GetMapping(path = "/logout")
    public String logoutPage(){
        return "logout";
    }

    @GetMapping("/problems/submissions/{problemId}")
    public String getSubmissionsPage(@PathVariable("problemId") String problemId, Model model) {
        List<SubmissionEntity> submissions = submissionService.findByProblemId(problemId);
        model.addAttribute("problemId", problemId);
        model.addAttribute("submissions", submissions);
        return "submission"; // Make sure this matches the Thymeleaf template file name
    }


}

