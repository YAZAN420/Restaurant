import java.io.*;
import java.security.*;
import java.util.*;
public class Login {
    private String name, email, password;
    Role role;
    Scanner s = new Scanner(System.in);
    Service ser = new Service();
    public Login(String email,String password) {
            this.email = email;
            this.password = password;
            try {
                this.password=ser.hashPassword(this.password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if (userExist(this.email, this.password)) {
                User.setCurrentUser(new User(name, this.email, this.password, role));
            } else {
                throw new IllegalArgumentException("Email or Password Wrong ");
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
                        try {
                            this.role = Role.valueOf(partsOfLine[3].toUpperCase());
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException("Invalid role in file: " + partsOfLine[3]);
                        }
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}