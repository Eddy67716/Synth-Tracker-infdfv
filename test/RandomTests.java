
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import org.json.JSONObject;

public class RandomTests {

    public static void main(String[] args) {

        try {
            
            String langAddress = "src/lang/eng_au.json";
            
            String jsonString = Files.readString(Path.of(langAddress));

            JSONObject j = new JSONObject(jsonString);
            
            String name = (String)j.get("edit.undo");
            
            System.out.println(name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
