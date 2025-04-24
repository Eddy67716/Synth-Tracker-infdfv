/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lang;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.json.JSONObject;

/**
 *
 * @author eddy6
 */
public class LanguageHandler {

    private final JSONObject languageJson;

    public LanguageHandler() throws FileNotFoundException, IOException {

        String langAddress = "src/lang/eng_au.json";

        String jsonString = Files.readString(Path.of(langAddress));

        languageJson = new JSONObject(jsonString);
    }

    public JSONObject getLanguageJson() {
        return languageJson;
    }

    public String getLanguageText(String name) {
        return (String) languageJson.get(name);
    }
}
