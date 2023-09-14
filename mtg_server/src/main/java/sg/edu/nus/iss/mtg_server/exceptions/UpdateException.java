package sg.edu.nus.iss.mtg_server.exceptions;

public class UpdateException extends Exception {
    
    private String message;

    public UpdateException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
