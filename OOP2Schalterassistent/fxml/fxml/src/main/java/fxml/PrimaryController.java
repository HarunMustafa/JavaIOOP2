package fxml;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.PrintService;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.WriterException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.awt.image.BufferedImage;


public class PrimaryController {

    @FXML
    private ComboBox<String> ComboBoxDrucker;

    @FXML
    private ComboBox<String> ComboBoxStandort;

    @FXML
    private TextField PortalID;

    @FXML
    private CheckBox chkbAbklaerung;

    @FXML
    private CheckBox chkbAustauschpass;

    @FXML
    private CheckBox chkbAusweisMutter;

    @FXML
    private CheckBox chkbAusweisVater;

    @FXML
    private CheckBox chkbEinwiligungserklaerung;

    @FXML
    private CheckBox chkbElterlicheSorge;

    @FXML
    private CheckBox chkbEntwertung;

    @FXML
    private CheckBox chkbGeburtsurkunde;

    @FXML
    private CheckBox chkbHeimatland;

    @FXML
    private CheckBox chkbKESB;

    @FXML
    private CheckBox chkbNiederlassungsausweis;

    @FXML
    private CheckBox chkbNotfall;

    @FXML
    private CheckBox chkbPersonendaten;

    @FXML
    private CheckBox chkbRechtskraftbescheingung;

    @FXML
    private CheckBox chkbSorgerechtentscheidung;

    @FXML
    private CheckBox chkbVerlustmeldung;

    @FXML
    private CheckBox chkbVerlustmeldungID;

    @FXML
    private CheckBox chkbVerlustmeldungPass;

    @FXML
    private Button konfig;

    @FXML
    private Button naaDrittstaatenButton;

    @FXML
    private Button naaEuEftaButton;

    //Ausländer
    @FXML
    private CheckBox passHeimatlandesCheckBox;

    //Ausländer
    @FXML
    private CheckBox geburtsurkundeCheckBox;

    @FXML
    private Button reisedokumentSemButton;

    @FXML
    private TextField txtAuftrag;

    @FXML
    private TextField txtNameVorname;

    // List to store selected checkboxes
    private List<String> selectedCheckboxes = new ArrayList<>();

    private static final String TITLE = "Pass- und Identitätskartendienst des Kantons Bern";
    private static final String FOOTER = "Die Produktionsfreigabe erfolgt erst nach Bezahlung der Gebühren und Erhalt allfälliger fehlender Dokumente";

    @FXML
    public void initialize() {
        // Initialize ComboBoxDrucker with the printer function of Global
        PrintService printService = Global.getPrintService();
        if (printService != null) {
            ComboBoxDrucker.getItems().add(printService.getName());
            ComboBoxDrucker.getSelectionModel().selectFirst();
        } else {
            // If the printer is not found, disable ComboBoxDrucker
            ComboBoxDrucker.setDisable(true);
        }

        ComboBoxStandort.getItems().addAll("BERN", "BIEL", "THUN", "COURTELARY", "INTERLAKEN", "LANGENTHAL", "MIDI");
        ComboBoxStandort.getSelectionModel().select(Konfig.getStandort());
        
        
        //String selectedStandortString = ComboBoxStandort.getValue();
        //Standort selectedStandort = Standort.valueOf(selectedStandortString);
        //Websocket websocket = new Websocket(selectedStandort);
    }

    // Method to add or remove a string from the selectedCheckboxes list
    @FXML
    private void handleCheckBoxSelection(ActionEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        if (checkBox.isSelected()) {
            String item = Global.getItemInCsvFile(checkBox.getText());
            selectedCheckboxes.add(item);
        } else {
            String item = Global.getItemInCsvFile(checkBox.getText());
            selectedCheckboxes.remove(item);
        }
    }


    
    @FXML
    public void handleButtonClick(ActionEvent event) throws WriterException {
        if (txtAuftrag.getText().isEmpty() || txtNameVorname.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a value for both Auftrag and Name/Vorname.");
            alert.showAndWait();
            return;
        }

        String artikel = null;
        if (event.getSource() == naaEuEftaButton) {
            artikel = "btnEUEFTA";
        } else if (event.getSource() == naaDrittstaatenButton) {
            artikel = "btnNAA";
        } else if (event.getSource() == reisedokumentSemButton) {
            artikel = "btnSEM";
        }

        String item = Global.getItemInCsvFile(artikel);
        // use item for further processing
        String[] subs = item.split(" ");

        StringBuilder sb = new StringBuilder();
        sb.append("A;").append(subs[0]);
        sb.append("\r\n");
        sb.append("A;").append(subs[1]);
        sb.append("\r\n");


        if (selectedCheckboxes.isEmpty()) {
            try{
                    // create QR object with relevant data
            QR qr = new QR(TITLE + sb.toString(), txtAuftrag.getText(), txtNameVorname.getText(), FOOTER);

            // create QR code
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(qr.toString(), BarcodeFormat.QR_CODE, 300, 300);

            // create BufferedImage from BitMatrix
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            // get the path for the output file
            String outputFilePath = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", "Schalterassistent", "QR.png").toString();
            
            // write the image to the output file
            File outputFile = new File(outputFilePath);
            ImageIO.write(image, "png", outputFile);
        }
        catch(IOException | WriterException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There was an error while saving the QR code.");
            e.printStackTrace();
        }
        } else {
            try{
                for (String entity : selectedCheckboxes) {
                    sb.append("A;").append(entity);
                    sb.append("\r\n");
                }
                // create QR object with relevant data
                QRWithList qr = new QRWithList(TITLE + sb.toString(), txtAuftrag.getText(), txtNameVorname.getText(), FOOTER, selectedCheckboxes);

                // create QR code
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix matrix = writer.encode(qr.toString(), BarcodeFormat.QR_CODE, 300, 300);

                // create BufferedImage from BitMatrix
                int width = matrix.getWidth();
                int height = matrix.getHeight();
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                    }
                }
                // get the path for the output file
                String outputFilePath = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", "Schalterassistent", "QR.png").toString();
                
                // write the image to the output file
                File outputFile = new File(outputFilePath);
                ImageIO.write(image, "png", outputFile);
                }
                catch(IOException | WriterException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("There was an error while saving the QR code.");
                    e.printStackTrace();
                }
                String outputFilePath = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", "Schalterassistent", "QR.png").toString();
                Printer.printImage(outputFilePath);
        }
        
    }

    @FXML
    void copyCsvFileToClient(ActionEvent event) {
        try {
            // Define the server path
            String serverPath = "\\\\abev-apps.infra.be.ch\\abev-apps$\\SchalterassistentNG\\Artikel.csv";
            
            // Define the client temp path
            Path clientTempPath = Paths.get(System.getenv("APPDATA"), "Schalterassistent", "Artikel.csv");
            
            // Copy the file from the server to the client
            Files.copy(Paths.get(serverPath), clientTempPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while copying the file: " + e.getMessage());
            alert.showAndWait();
        }
    }

/* 
    private void printDocument() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(txtNameVorname.getScene().getWindow())) {
            printerJob.getJobSettings().setJobName("Schalterassistent Druckauftrag");
            printerJob.getJobSettings().setCopies(1);

            PageFormat pageFormat = printerJob.getJobSettings().getPageLayout().getPageFormat(0);
            pageFormat = printerJob.getJobSettings().getPageLayout().getPageFormat(0);

            printerJob.printPage(pageFormat, new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    if (pageIndex != 0) {
                        return NO_SUCH_PAGE;
                    }

                    int startX = (int) pageFormat.getImageableX();
                    int startY = (int) pageFormat.getImageableY();
                    int offset = 20;

                    Font font = new Font("Arial", Font.PLAIN, 10);
                    float fontHeight = (float) font.getLineMetrics("A", graphics.getFontRenderContext()).getBounds().getHeight();

                    graphics.setFont(font);

                    graphics.drawString(TITLE, startX, startY + offset);
                    offset += 20;

                    Image qrImage = null;
                    try {
                        qrImage = ImageIO.read(new File(Paths.get(System.getenv("APPDATA"), "Schalterassistent", "QR.png").toString()));
                        graphics.drawImage(qrImage, startX + 80, startY + offset, null);
                        offset += 150;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    graphics.drawString(FOOTER, startX, startY + offset);
                    offset += 20;

                    return PAGE_EXISTS;
                }
            });
            printerJob.endJob();
        }
    }*/
}





    
    