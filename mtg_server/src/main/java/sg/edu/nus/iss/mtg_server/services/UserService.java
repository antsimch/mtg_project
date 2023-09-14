package sg.edu.nus.iss.mtg_server.services;

import java.nio.CharBuffer;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sg.edu.nus.iss.mtg_server.exceptions.InvalidPasswordException;
import sg.edu.nus.iss.mtg_server.exceptions.UserCreationFailedException;
import sg.edu.nus.iss.mtg_server.exceptions.UserNotFoundException;
import sg.edu.nus.iss.mtg_server.exceptions.UsernameAlreadyTakenException;
import sg.edu.nus.iss.mtg_server.models.LoginDetails;
import sg.edu.nus.iss.mtg_server.models.User;
import sg.edu.nus.iss.mtg_server.models.UserToken;
import sg.edu.nus.iss.mtg_server.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    /*
     * Service for creation of new user, checks for duplicate username in
     * database and throws custom UsernameAlreadyTakenException if database
     * already contains the same username
     */
    public String insertUser(User user) 
            throws UsernameAlreadyTakenException, UserCreationFailedException {

        User result = userRepo.findUser(user.getUsername());

        if (result != null)
            throw new UsernameAlreadyTakenException(
                    "Username is already taken");

        user.setUserId(UUID.randomUUID().toString().substring(0, 8));
        user.setUserPassword(
                passwordEncoder.encode(
                        CharBuffer.wrap(user.getUserPassword().toCharArray())));

        System.out.println("\n\nuserId >>>> " + user.getUserId() + "\n\n");
        userRepo.insertUser(user);

        if (userRepo.findUser(user.getUsername()) == null)
            throw new UserCreationFailedException("User is not created");

        return user.getUserId();
    }

    /*
     * Authentication of user for login
     */
    public UserToken login(LoginDetails login) 
            throws UserNotFoundException, InvalidPasswordException  {

        User result = userRepo.findUser(login.getUsername());

        if (result == null) 
            throw new UserNotFoundException("User not found");
        
        if (!passwordEncoder.matches(
                CharBuffer.wrap(
                        login.getPassword().toCharArray()), 
                        result.getUserPassword())) {

            throw new InvalidPasswordException(
                    "Password is invalid. Please try again");
        }

        return new UserToken(result.getUserId(), result.getUsername(), "");
    }
}
