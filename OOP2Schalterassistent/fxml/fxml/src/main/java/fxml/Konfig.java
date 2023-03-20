package fxml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class Konfig {

    private static final String APP_FOLDER_NAME = "Schalterassistent";
    private static final String FILE_NAME = "config.json";
    private static final String PLACEHOLDER = "11111111";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String CONFIG_FILE_PATH = System.getenv("APPDATA") + File.separator + APP_FOLDER_NAME;

    private static String standort;

    public static void createAppFolder() {
        Path appFolderPath = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", APP_FOLDER_NAME);
        try {
            if (!Files.exists(appFolderPath)) {
                Files.createDirectories(appFolderPath);
                System.out.println("App folder created at: " + appFolderPath);
            } else {
                System.out.println("App folder already exists at: " + appFolderPath);
            }
        } catch (Exception e) {
            System.err.println("Failed to create app folder: " + e.getMessage());
        }
    }

    public static String getPortalId() throws IOException {
        File file = new File(CONFIG_FILE_PATH, FILE_NAME);
        if (!file.exists()) {
            createConfigFile(file, PLACEHOLDER);
            return PLACEHOLDER;
        }
        try (FileReader reader = new FileReader(file)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            String portalId = jsonObject.get("portalId").getAsString();
            if (portalId == null || portalId.isEmpty()) {
                return PLACEHOLDER;
            }
            return portalId.trim();
        } catch (JsonParseException e) {
            System.err.println("Failed to parse configuration file: " + e.getMessage());
            return PLACEHOLDER;
        }
    }

    public static String getStandort() {
        return standort;
    }

    private static void createConfigFile(File file, String content) throws IOException {
        boolean created = file.createNewFile();
        if (!created) {
            throw new IOException("Failed to create configuration file.");
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("portalId", content);
        jsonObject.addProperty("standort", standort);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonObject.toString() + LINE_SEPARATOR);
        }
    }

    public static void setStandort(String standort) {
        Konfig.standort = standort;
    }
}
