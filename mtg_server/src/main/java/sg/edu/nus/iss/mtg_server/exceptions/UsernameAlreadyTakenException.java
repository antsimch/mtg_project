package sg.edu.nus.iss.mtg_server.exceptions;

public class UsernameAlreadyTakenException extends Exception {
    
    private String message;

    public UsernameAlreadyTakenException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
