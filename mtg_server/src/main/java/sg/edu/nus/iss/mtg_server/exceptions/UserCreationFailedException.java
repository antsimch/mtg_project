package sg.edu.nus.iss.mtg_server.exceptions;

public class UserCreationFailedException extends Exception {
    
    private String message;

    public UserCreationFailedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
