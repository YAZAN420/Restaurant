package Controller;

import Model.RoleModel;
import Model.UserModel;

import java.io.*;
import java.util.*;

public class RegisterController {
    @SuppressWarnings("unused")
    private String name, email, password, confirmPassword;
    ServiceController ser = new ServiceController();
    File users = new File("txtFiles/users.txt");
    FileWriter fw;
    Scanner s = new Scanner(System.in);

    public RegisterController(String name, String email, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        int last = UserModel.getUsers().size() - 1;
        UserModel user = new UserModel(name, email, password, confirmPassword, RoleModel.USER,
                (UserModel.getUsers().get(last).getId() + 1) + ".jpg", UserModel.getUsers().get(last).getId() + 1);
        ser.registerUser(user);
        ReportServiceController.customerOrdersNumber.computeIfAbsent(this.name, k -> 0);
        UserModel.setCurrentUser(user);
    }
}