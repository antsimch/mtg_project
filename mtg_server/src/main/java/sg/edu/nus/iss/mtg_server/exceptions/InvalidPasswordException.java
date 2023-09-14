package sg.edu.nus.iss.mtg_server.exceptions;

public class InvalidPasswordException extends Exception {
    
    private String message;

    public InvalidPasswordException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
