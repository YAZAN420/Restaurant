package Controller;

import java.io.*;
import java.security.*;
import java.util.*;
import Model.*;

public class LoginController {
    private String name, email, password, photoPath;
    RoleModel role;
    int id;
    Scanner s = new Scanner(System.in);
    ServiceController ser = new ServiceController();

    @SuppressWarnings("static-access")
    public LoginController(String email, String password) {
        this.email = email;
        this.password = password;
        try {
            this.password = ser.hashPassword(this.password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (userExist(this.email, this.password)) {
            UserModel.setCurrentUser(new UserModel(name, this.email, this.password, role, photoPath, id));
        } else {
            throw new IllegalArgumentException("Email or Password Wrong ");
        }
    }

    private boolean userExist(String email, String password) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("txtFiles/users.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] partsOfLine = line.split(",");
                    if (partsOfLine[1].equals(email) && partsOfLine[2].equals(password)) {
                        this.name = partsOfLine[0];
                        this.photoPath = partsOfLine[4];
                        this.id = Integer.parseInt(partsOfLine[5]);
                        try {
                            this.role = RoleModel.valueOf(partsOfLine[3].toUpperCase());
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