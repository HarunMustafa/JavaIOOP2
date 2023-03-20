import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import fxml.Global;

public class GlobalTest {

    @Test
    public void testGetCsvFile() {
        String expectedPath = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", "Schalterassistent", "Artikel.csv").toString();
        String result = Global.getCsvFile();
        Assertions.assertEquals(expectedPath, result);
    }
    
}
    
