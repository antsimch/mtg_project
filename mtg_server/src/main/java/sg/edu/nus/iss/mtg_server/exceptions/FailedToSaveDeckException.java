package sg.edu.nus.iss.mtg_server.exceptions;

public class FailedToSaveDeckException extends Exception {
    
    private String message;

    public FailedToSaveDeckException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
