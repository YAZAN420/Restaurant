import java.io.*;
import java.util.*;
public class Register {
    @SuppressWarnings("unused")
    private String name, email, password, confirmPassword;
    Service ser = new Service();
    File users = new File("users.txt");
    FileWriter fw;
    Scanner s = new Scanner(System.in);
    Register(String name,String email,String password,String confirmPassword) {
        try {
            this.name = name;
            this.email = email;
            this.password = password;
            this.confirmPassword = confirmPassword;
            try {
                User user = new User(name, email, password, confirmPassword, Role.USER);
                ser.registerUser(user);
                ReportService.customerOrdersNumber.computeIfAbsent(this.name,k->0);//lujain
                System.out.println("User registered successfully!");
                User.setCurrentUser(user);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}