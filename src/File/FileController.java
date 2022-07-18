package File;

import app.User;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;
/**
 * The class File controller implements serializable
 *  @author Manankumar Patel mpp128
 *  @author Manav Gohil mrg225
 */
public class FileController implements Serializable {
    static String loc="src/data/Users.ser";
    /**
     *
     * Writes object inside the file
     *
     * @param userArrayList
     * @throws   IOException
     */
    public static void write(ArrayList<User> userArrayList) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(loc))) {
            oos.writeObject(userArrayList);
        }
    }

    /**
     *
     * Read objects from file
     *
     * @return ArrayList<User>
     * @throws   IOException
     * @throws  ClassNotFoundException
     */
    public static ArrayList<User> read() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(loc))) {
            return (ArrayList<User>) ois.readObject();
        }
    }
    /**
     *
     * Show alert based on the error
     *
     * @param title  the title
     * @param header  the header
     * @param context  the context
     */
    public static void showAlert(String title, String header, String context){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}
