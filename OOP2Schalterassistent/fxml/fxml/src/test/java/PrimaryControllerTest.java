import javafx.event.ActionEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import fxml.PrimaryController;

public class PrimaryControllerTest {
    @Test
    public void testHandleButtonClick() throws Exception {
        // Arrange
        PrimaryController controller = new PrimaryController();
        ActionEvent event = mock(ActionEvent.class);

        // Act
        controller.handleButtonClick(event);

        // Assert
        assertEquals(1, 1); // Replace with actual assertions
        verify(event).getSource(); // Example Mockito verification
    }
}
