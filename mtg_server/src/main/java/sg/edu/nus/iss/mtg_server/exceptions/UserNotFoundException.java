package sg.edu.nus.iss.mtg_server.exceptions;

public class UserNotFoundException extends Exception {
    
    private String message;

    public UserNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
