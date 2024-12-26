import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
public class User {
    private String name, email, password;
    private Role role;
    private static User currentUser; 
    private static List<User> users = new ArrayList<>();
    public User(String name, String email, String password, String confirmPassword, Role role) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a digit, and a special character");
        }
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
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
        saveUsersToFile(u, Service.users);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ChangeRoleLog.txt", true))) {
            bw.write(logMessage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write ChangeRole log", e);
        }
    }
    public static void checkPermission(Role role, Permission perm) {//BACK
        if (!RolePermissions.hasPermission(role, perm)) {
            throw new IllegalArgumentException("You do not have permission to perform this action");
        }
    }
    public void saveUsersToFile(User u, File file) {
        for (User user : getUsers()) {
            Service.saveUserToFile(user.name, user.email, user.password, user.role);
        }
    }
    public static void clearFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
        } catch (IOException e) {
            throw new RuntimeException("Failed to clear the file: " + file, e);
        }
    }
    public static List<User> loadUsers(File file) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String email = parts[1];
                String password = parts[2];
                Role role;
                try {
                    role = Role.valueOf(parts[3]);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid role: " + parts[3]);
                    continue;
                }
                User user = new User(name, email, password, role);
                users.add(user);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
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
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
    public boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordRegex, password);
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
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
