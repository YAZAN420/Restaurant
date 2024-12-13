import java.io.*;
import java.security.*;
public class Service {
    public static File users = new File("users.txt");
    public void registerUser(User user) {
        if (isFieldTaken(1,user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        if (isFieldTaken(0,user.getName())) {
            throw new IllegalArgumentException("Name is already taken.");
        }
        try {
            String passwordHashed = hashPassword(user.getPassword());
            saveUserToFile(user.getName(), user.getEmail(), passwordHashed);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public boolean isFieldTaken(int index, String value) {
        try (BufferedReader br = new BufferedReader(new FileReader(users))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[index].equals(value)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("not found");
        }
    }
    public void saveUserToFile(String name, String email, String hashedPassword) {
        try (FileWriter fw = new FileWriter(users, true)) {
            fw.write(name + "," + email + "," + hashedPassword +"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
