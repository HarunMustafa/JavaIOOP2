
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fxml.Konfig;

public class KonfigTest {

    @Test
    void testCreateAppFolder() {
        // Test with non-existent folder
        Path appFolderPath = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", "NonExistentAppFolder");
        Assertions.assertFalse(Files.exists(appFolderPath));
        Konfig.createAppFolder();
        Assertions.assertTrue(Files.exists(appFolderPath));

        // Test with existing folder
        Assertions.assertDoesNotThrow(() -> Konfig.createAppFolder());
    }

    @Test
    public void testGetPortalId() {
        try {
            String portalId = Konfig.getPortalId();
            Assertions.assertNotNull(portalId, "Portal ID should not be null");
            Assertions.assertFalse(portalId.isEmpty(), "Portal ID should not be empty");
        } catch (IOException e) {
            Assertions.fail("Failed to get portal ID: " + e.getMessage());
        }
    }
    
}
