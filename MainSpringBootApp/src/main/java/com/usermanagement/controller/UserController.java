package com.usermanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.usermanagement.entity.LoginResponse;
import com.usermanagement.entity.Message;
import com.usermanagement.entity.User;
import com.usermanagement.entity.UserResponse;
import com.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;



import com.usermanagement.utils.TokenValidator;

import static com.usermanagement.utils.TokenValidator.generateToken;

@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String showHome(Model model){
        return "redirect:/login_form";
    }

    @GetMapping("/registration_form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration_form";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user,Model model) {

        //System.out.println(user.getEmail());
        User retrievedUser = userRepository.getUserByEmail(user.getEmail());
       // System.out.println(retrievedUser);
        if (retrievedUser != null ) {
           // System.out.println("if inside");
            model.addAttribute("error", "EmailId already exists!");
            return "registration_form";
        } else {
            userRepository.save(user);
            return "redirect:/login_form";
        }
    }

    /*
    @GetMapping("/user/exists/{id}")
    public ResponseEntity<?> checkUserExists(@PathVariable("id") String userId) {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok("User exists in DynamoDB");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User doesn't exist in DynamoDB");
        }
    }
    */



    @GetMapping("/user/{id}")
    public ResponseEntity<?> checkUserExists(@PathVariable("id") String userId) {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String userJson;
            try {
                userJson = objectMapper.writeValueAsString(user);
                return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(user.getUserid(),user.getName(),user.getEmail(),user.getToken()));

               // return ResponseEntity.ok(userJson);
            } catch (JsonProcessingException e) {
                // Handle JSON processing exception
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting user to JSON");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User doesn't exist in DynamoDB");
        }
    }


    @GetMapping("/private/user/{id}")
    public ResponseEntity<?> checkResourceExists(@PathVariable("id") String userId, @RequestHeader("Authorization") String authorizationHeader) {
        // Validate token
        String token = authorizationHeader.replace("Bearer ", "");
        if (!TokenValidator.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        // Retrieve user from DynamoDB
        User user = userRepository.getUserById(userId);
        if (user != null) {
            UserResponse userResponse = new UserResponse(user.getUserid(), user.getName(), user.getEmail(), user.getToken());
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User doesn't exist in DynamoDB");
        }
    }


    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        userRepository.save(user);

        // Create a response object with the desired properties
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserid());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(user.getUserid(),user.getName(),user.getEmail()));
        //return ResponseEntity.ok(response);
    }

    @GetMapping("/login_form")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login_form";
    }

    /*
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model) {
        User retrievedUser = userRepository.getUserByEmail(user.getEmail());

        if (retrievedUser != null && retrievedUser.getPassword().equals(user.getPassword())) {
            model.addAttribute("user", retrievedUser);
            return "home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login_form";
        }
    }

     */


    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model) {
        User retrievedUser = userRepository.getUserByEmail(user.getEmail());

        if (retrievedUser != null && retrievedUser.getPassword().equals(user.getPassword())) {
            // Generate JWT token
            String token = generateToken(retrievedUser.getEmail());

            // Save the token in DynamoDB
            retrievedUser.setToken(token);
            System.out.println(retrievedUser);

            userRepository.save(retrievedUser);
            System.out.println(retrievedUser);

            // Add the token to the model
            model.addAttribute("token", token);

            // Redirect to the home page or any other protected resource
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login_form";
        }
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }




    @PostMapping("/checkLogin")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        User retrievedUser = userRepository.getUserByEmail(email);

        if (retrievedUser != null && retrievedUser.getPassword().equals(password)) {
            // Generate JWT token
            String token = generateToken(retrievedUser.getEmail());

            // Save the token in DynamoDB
            retrievedUser.setToken(token);
            userRepository.save(retrievedUser);

            // Return success response with token as JSON
            return ResponseEntity.ok().body(new LoginResponse(retrievedUser.getUserid(),token));


        } else {

            // Return error response with status code 401 (Unauthorized) as JSON
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Message("Invalid username or password"));
        }
    }

    @PostMapping("/checkRegisteredUser")
    public ResponseEntity<?> registeredUser(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String name = payload.get("name");

        User retrievedUser = userRepository.getUserByEmail(email);

        if (retrievedUser != null && retrievedUser.getEmail().equals(email)) {
            // Generate JWT token
           // String token = generateToken(retrievedUser.getEmail());

            // Save the token in DynamoDB
            //retrievedUser.setToken(token);
            userRepository.save(retrievedUser);

            // Return success response with token as JSON
         //   return ResponseEntity.ok().body(new Message(token));

          //  userRepository.save(user);

            // Create a response object with the desired properties
            Map<String, Object> response = new HashMap<>();
            response.put("userId", retrievedUser.getUserid());
            response.put("name", retrievedUser.getName());
            response.put("email", retrievedUser.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(retrievedUser.getUserid(),retrievedUser.getName(),retrievedUser.getEmail()));
            //return ResponseEntity.ok(response);

        } else {

            // Return error response with status code 401 (Unauthorized) as JSON
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Message("User not found"));
        }
    }





}
