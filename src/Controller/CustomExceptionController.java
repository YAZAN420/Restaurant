package Controller;

public class CustomExceptionController extends Exception {
    public CustomExceptionController(String message) {
        super(message);
    }
}
