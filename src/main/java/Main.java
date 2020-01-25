// LIMA Group
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import java.io.Console;

public class Main {

    public static void main(String[] args) {

        Console console = System.console();

        String user = console.readLine("Enter user: ");
        CompareFacesMatch match = MakeComparison.login(user.trim());

        if (match == null) {
            System.out.println("No match found.");
        }
        else {
            System.out.println("Face matches with "
                + match.getSimilarity().toString()
                + "% confidence.");
        }
    }
}
