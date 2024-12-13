import java.io.*;
import java.security.*;
import java.util.*;
public class Login {
    private String name, email, password;
    private static User currentUser;
    Scanner s = new Scanner(System.in);
    Service ser = new Service();

    public Login() {
        try {
            System.out.println("email,password");
            this.email = s.next();
            this.password = s.next();
            if (!User.isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid email format");
            }
            try {
                this.password=ser.hashPassword(this.password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if (userExist(email, password)) {
                currentUser = new User(name, email, password, password);
                System.out.println("LoggedIn");
            } else {
                System.out.println("Email or Password Wrong ");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private boolean userExist(String email, String password) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] partsOfLine = line.split(",");
                    if (partsOfLine[1].equals(email) && partsOfLine[2].equals(password)) {
                        this.name = partsOfLine[0];
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
}
