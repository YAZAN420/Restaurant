package Model;

import Controller.*;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserModel {
    private String name, email, password, photoPath;
    private RoleModel role;
    private int id;
    private static UserModel currentUser;
    private static List<UserModel> users = new ArrayList<>();

    public UserModel(String name, String email, String password, String confirmPassword, RoleModel role,
            String photoPath, int id) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a digit");
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.photoPath = photoPath;
    }

    public UserModel(String name, String email, String password, RoleModel role, String photoPath, int id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.id = id;
        this.photoPath = photoPath;
    }

    public void changeRole(UserModel u, RoleModel newRole) {
        checkPermission(currentUser.getRole(), PermissionModel.CHANGE_ROLE);
        if (currentUser == u) {
            throw new IllegalArgumentException("You cannot change your own role.");
        }
        String logMessage = String.format("Admin %s changed %s's role from %s to %s at %s\n",
                currentUser.name, u.getName(), u.getRole(), newRole, new java.util.Date());
        u.setRole(newRole);
        clearFile(ServiceController.users);
        saveUsersToFile(ServiceController.users);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("txtFiles/ChangeRoleLog.txt", true))) {
            bw.write(logMessage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write ChangeRole log", e);
        }
    }

    public static void checkPermission(RoleModel role, PermissionModel perm) {
        if (!RolePermissionsController.hasPermission(role, perm)) {
            throw new IllegalArgumentException("You do not have permission to perform this action");
        }
    }

    public static void saveUsersToFile(File file) {
        for (UserModel user : getUsers()) {
            ServiceController.saveUserToFile(user.getName(), user.getEmail(), user.getPassword(), user.getRole(),
                    user.getPhotoPath(), user.getId());
        }
    }

    public static void clearFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
        } catch (IOException e) {
            throw new RuntimeException("Failed to clear the file: " + file, e);
        }
    }

    public static void editUser(String oldName, String name, String email, String path) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        for (UserModel user : getUsers()) {
            if (oldName.equalsIgnoreCase(user.getName())) {
                user.setPhotoPath(path);
                user.setName(name);
                user.setEmail(email);
                currentUser = user;
            }
        }
        clearFile(new File("txtFiles/users.txt"));
        saveUsersToFile(new File("txtFiles/users.txt"));
    }

    public static void editUserPassword(String oldName, String oldPassword, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a digit");
        }
        try {
            oldPassword = ServiceController.hashPassword(oldPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        for (UserModel user : getUsers()) {
            if (oldName.equalsIgnoreCase(user.getName())) {
                if (!oldPassword.trim().equalsIgnoreCase(user.getPassword().trim())) {
                    throw new IllegalArgumentException(
                            "Invalid Password");
                }
                String hashPassword = "";
                try {
                    hashPassword = ServiceController.hashPassword(password);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                user.setPassword(hashPassword);
                currentUser = user;
            }
        }
        clearFile(new File("txtFiles/users.txt"));
        saveUsersToFile(new File("txtFiles/users.txt"));
    }

    public static List<UserModel> loadUsers(File file) {
        List<UserModel> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0], email = parts[1], password = parts[2], photoPath = parts[4];
                int id = Integer.parseInt(parts[5]);
                RoleModel role;
                try {
                    role = RoleModel.valueOf(parts[3]);
                } catch (IllegalArgumentException e) {
                    continue;
                }
                UserModel user = new UserModel(name, email, password, role, photoPath, id);
                users.add(user);
            }
        } catch (IOException e) {
        }
        return users;
    }

    public static UserModel findUserByName(String name) {
        for (UserModel user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleModel stringToRole(String role) {
        if (role == "ADMIN")
            return RoleModel.ADMIN;
        else if (role == "EMPLOYEE")
            return RoleModel.EMPLOYEE;
        else
            return RoleModel.USER;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d\\W_]{8,}$";
        return Pattern.matches(passwordRegex, password);
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public static UserModel getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserModel currentUser) {
        UserModel.currentUser = currentUser;
    }

    public static List<UserModel> getUsers() {
        return users;
    }

    public static void setUsers(List<UserModel> users) {
        UserModel.users = users;
    }
}
