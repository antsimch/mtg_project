package sg.edu.nus.iss.mtg_server.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import sg.edu.nus.iss.mtg_server.config.UserAuthProvider;
import sg.edu.nus.iss.mtg_server.exceptions.InvalidPasswordException;
import sg.edu.nus.iss.mtg_server.exceptions.UserCreationFailedException;
import sg.edu.nus.iss.mtg_server.exceptions.UserNotFoundException;
import sg.edu.nus.iss.mtg_server.exceptions.UsernameAlreadyTakenException;
import sg.edu.nus.iss.mtg_server.models.LoginDetails;
import sg.edu.nus.iss.mtg_server.models.User;
import sg.edu.nus.iss.mtg_server.models.UserToken;
import sg.edu.nus.iss.mtg_server.services.EmailService;
import sg.edu.nus.iss.mtg_server.services.UserService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class AuthController {
    
    private final UserService userSvc;
    private final UserAuthProvider userAuthProvider;
    private final EmailService emailSvc;
    
    /*
     * Endpoint for creation of new user, includes validation of User object.
     * Controller returns {"message": "User has been created"} upon successfully 
     * inserting the user into database or {"message": "Username is already 
     * taken"} if database already contains the same username
     */
    @PostMapping(path = "/register")
    public ResponseEntity<String> createUser(
            @RequestBody @Valid User user,
            BindingResult binding) {

        JsonObjectBuilder objbuilder = Json.createObjectBuilder();

        if (binding.hasErrors()) {
            binding.getAllErrors()
                    .forEach(error -> {
                        objbuilder.add(
                                ((FieldError) error).getField(),
                                error.getDefaultMessage());
                    });

            return ResponseEntity.badRequest().body(
                    objbuilder.build().toString());
        }

        try {
            userSvc.insertUser(user);

            String subject = 
                "MTG Draft & Build | Account Registration Notification";

            String email = user.getUserEmail();

            String message = "Hi " + user.getUsername() + 
                    ", \n Your account setup is completed \n";

            emailSvc.sendEmail(email, subject, message);
            
            UserToken token = new UserToken();
            token.setId(user.getUserId());
            token.setUsername(user.getUsername());
            token.setToken(
                    userAuthProvider.createToken(
                            new UserToken(
                                    user.getUserId(), 
                                    user.getUsername(), 
                                    "")));

            System.out.println(token);

            return ResponseEntity.created(URI.create("/register/" + token.getId()))
                    .body(token.toJson().toString());

        } catch (UsernameAlreadyTakenException | UserCreationFailedException ex) {

            objbuilder.add("message", ex.getMessage());
            return ResponseEntity.badRequest().body(
                    objbuilder.build().toString());
        }
    }

    /*
     * Endpoint for login authentication includes validation of LoginDetails
     * object. Controller returns {"message": "Welcome {username}", 
     * "userId": {userId}} upon successful login or {"message": "Invalid 
     * username and/or password"} if login is unsuccessful.
     */
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(
            @RequestBody @Valid LoginDetails loginDetails,
            BindingResult binding) {

        JsonObjectBuilder objbuilder = Json.createObjectBuilder();

        if (binding.hasErrors()) {

            binding.getAllErrors()
                    .forEach(error -> {
                        objbuilder.add(
                                ((FieldError) error).getField(),
                                error.getDefaultMessage());
                    });

            return ResponseEntity.badRequest().body(
                    objbuilder.build().toString());
        }

        try {
            UserToken token = userSvc.login(loginDetails);
            token.setToken(userAuthProvider.createToken(token));

            return ResponseEntity.ok(token.toJson().toString());
        } catch (InvalidPasswordException | UserNotFoundException ex) {

            objbuilder.add("message", ex.getMessage());
            return ResponseEntity.badRequest().body(
                    objbuilder.build().toString());
        }
    }
}
