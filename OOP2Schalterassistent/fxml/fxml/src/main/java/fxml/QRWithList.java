package fxml;
import java.util.ArrayList;
import java.util.List;

public class QRWithList extends QR {

    public QRWithList(String title, String orderNumber, String name, String footer) {
        super(title, orderNumber, name, footer);
    }

    public QRWithList(String title, String orderNumber, String name, String footer, List<String> selectedCheckboxes) {
        super(title, orderNumber, name, footer);
        this.selectedCheckboxes = selectedCheckboxes;
    }

    private List<String> selectedCheckboxes = new ArrayList<>();

    public List<String> getSelectedCheckboxes() {
        return selectedCheckboxes;
    }

    public void setSelectedCheckboxes(List<String> selectedCheckboxes) {
        this.selectedCheckboxes = selectedCheckboxes;
    }
}
