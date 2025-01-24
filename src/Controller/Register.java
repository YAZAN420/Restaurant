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
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        int last = User.getUsers().size()-1;
        User user = new User(name, email, password, confirmPassword, Role.USER,(User.getUsers().get(last).getId()+1)+".jpg",User.getUsers().get(last).getId()+1);
        ser.registerUser(user);
        ReportService.customerOrdersNumber.computeIfAbsent(this.name,k->0);
        User.setCurrentUser(user);
    }
}