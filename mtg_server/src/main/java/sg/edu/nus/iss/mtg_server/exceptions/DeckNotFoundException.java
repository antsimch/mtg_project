package sg.edu.nus.iss.mtg_server.exceptions;

public class DeckNotFoundException extends Exception {
    
    private String message;

    public DeckNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
