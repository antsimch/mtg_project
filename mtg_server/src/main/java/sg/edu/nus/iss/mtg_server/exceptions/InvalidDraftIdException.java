package sg.edu.nus.iss.mtg_server.exceptions;

public class InvalidDraftIdException extends Exception {
    private String message;

    public InvalidDraftIdException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
