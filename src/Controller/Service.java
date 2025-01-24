import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.*;
public class Service {
    public static File users = new File("users.txt");

    public void registerUser(User user) {
        if (isFieldTaken(1, user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        if (isFieldTaken(0, user.getName())) {
            throw new IllegalArgumentException("Name is already taken.");
        }
        try {
            String passwordHashed = hashPassword(user.getPassword());
            user.setPassword(passwordHashed);
            File defaultImage = new File("photos\\default.jpg");
            File SaveToPhotos = new File(new File("photos"),user.getPhotoPath());
            try {
                 Files.copy(defaultImage.toPath(), SaveToPhotos.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }catch(Exception e){
                e.printStackTrace();
            }
            saveUserToFile(user.getName(), user.getEmail(), passwordHashed, user.getRole(),
                    "photos/" + user.getPhotoPath(),user.getId());
            user.setPhotoPath("photos/" + user.getPhotoPath());
            User.getUsers().add(user);
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
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
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
    public static void saveUserToFile(String name, String email, String hashedPassword, Role role,String photoPath,int id) {
        try (FileWriter fw = new FileWriter(users, true)) {
            fw.write(name + "," + email + "," + hashedPassword +","+role.name()+","+photoPath+","+id+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
