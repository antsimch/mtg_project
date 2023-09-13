package sg.edu.nus.iss.mtg_server.exceptions;

public class InvalidUsernameOrPasswordException extends Exception {
    
    private String message;

    public InvalidUsernameOrPasswordException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
