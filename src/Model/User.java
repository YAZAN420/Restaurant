import Model.Role;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
public class User {
    private String name, email, password,photoPath;
    private Role role;
    private int id;
    private static User currentUser;
    private static List<User> users = new ArrayList<>();
    public User(String name, String email, String password, String confirmPassword, Role role,String photoPath,int id) {
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
        this.photoPath=photoPath;
    }
    public User(String name, String email, String password, Role role,String photoPath,int id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.id=id;
        this.photoPath = photoPath;
    }
    public void changeRole(User u, Role newRole) {
        checkPermission(currentUser.getRole(), Permission.CHANGE_ROLE);
        if (currentUser == u) {
            throw new IllegalArgumentException("You cannot change your own role.");
        }
        String logMessage = String.format("Admin %s changed %s's role from %s to %s at %s\n",
                currentUser.name, u.getName(), u.getRole(), newRole, new java.util.Date());
        u.setRole(newRole);
        clearFile(Service.users);
        saveUsersToFile(Service.users);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ChangeRoleLog.txt", true))) {
            bw.write(logMessage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write ChangeRole log", e);
        }
    }
    public static void checkPermission(Role role, Permission perm) {
        if (!RolePermissions.hasPermission(role, perm)) {
            throw new IllegalArgumentException("You do not have permission to perform this action");
        }
    }
    public static void saveUsersToFile(File file) {
        for (User user : getUsers()) {
            Service.saveUserToFile(user.getName(), user.getEmail(), user.getPassword(), user.getRole(),user.getPhotoPath(),user.getId());
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
        for (User user : getUsers()) {
            if (oldName.equalsIgnoreCase(user.getName())) {
                user.setPhotoPath(path);
                user.setName(name);
                user.setEmail(email);
                currentUser = user;
            }
        }
        clearFile(new File("users.txt"));
        saveUsersToFile(new File("users.txt"));
    }
    public static void editUserPassword(String oldName,String oldPassword, String password, String confirmPassword){
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a digit");
        }
        try {
            oldPassword = Service.hashPassword(oldPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        for (User user : getUsers()) {
            if (oldName.equalsIgnoreCase(user.getName())) {
                if(!oldPassword.trim().equalsIgnoreCase(user.getPassword().trim())){
                    throw new IllegalArgumentException(
                        "Invalid Password");
                }
                String hashPassword="";
                try {
                    hashPassword = Service.hashPassword(password);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                user.setPassword(hashPassword);
                currentUser = user;
            }
        }
        clearFile(new File("users.txt"));
        saveUsersToFile(new File("users.txt"));
    }
    public static List<User> loadUsers(File file) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0], email = parts[1], password = parts[2], photoPath = parts[4];
                int id = Integer.parseInt(parts[5]);
                Role role;
                try {
                    role = Role.valueOf(parts[3]);
                } catch (IllegalArgumentException e) {
                    continue;
                }
                User user = new User(name, email, password, role, photoPath,id);
                users.add(user);
            }
        } catch (IOException e) {
        }
        return users;
    }

    public static User findUserByName(String name) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role stringToRole(String role){
        if(role=="ADMIN") return Role.ADMIN;
        else if(role=="EMPLOYEE") return Role.EMPLOYEE;
        else return Role.USER;
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
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }
    public static List<User> getUsers() {
        return users;
    }
    public static void setUsers(List<User> users) {
        User.users = users;
    }
}
