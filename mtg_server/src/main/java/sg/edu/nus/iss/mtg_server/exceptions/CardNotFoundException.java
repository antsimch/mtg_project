package sg.edu.nus.iss.mtg_server.exceptions;

public class CardNotFoundException extends Exception {
    
    private String message;

    public CardNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
