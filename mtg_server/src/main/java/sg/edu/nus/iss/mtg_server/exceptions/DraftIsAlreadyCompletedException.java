package sg.edu.nus.iss.mtg_server.exceptions;

public class DraftIsAlreadyCompletedException extends Exception {
    private String message;

    public DraftIsAlreadyCompletedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }   
}
