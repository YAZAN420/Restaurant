import java.io.*;
import java.util.*;
public class Register {
    private String name, email, password, confirmPassword;
    Service ser = new Service();
    File users = new File("users.txt");
    FileWriter fw;
    Scanner s = new Scanner(System.in);
    Register() {
        try {   
            System.out.println("name,email,password,confirmPassword");
            this.name = s.next();
            this.email = s.next();
            this.password = s.next();
            this.confirmPassword = s.next();
            try {
                User user = new User(name, email, password, confirmPassword);
                ser.registerUser(user);
                System.out.println("User registered successfully!");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}