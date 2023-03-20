package fxml;
//Help Class

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;

public class Global {

    public static PrintService getPrintService() {
        // Get all installed printers
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        
        // Loop through all printers and look for the EPSON TM-T20II Receipt
        for (PrintService printService : printServices) {
            if (printService.getName().equals("EPSON TM-T20II Receipt")) {
                return printService;
            }
        }
        
        // If the printer is not found, return null
        return null;
    }

    public static String getCsvFile() {
        String path = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", "Schalterassistent", "Artikel.csv").toString();
        return path;
    }
    

    public static String getItemInCsvFile(String artikel) {
        try {
            String path = getCsvFile();
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts[0].equals(artikel)) {
                    return parts[1];
                }
            }
            return "Konfig File has an Error";
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR - the file is missing! " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return "Konfig File has an Error";
    }

    static String calculateAgeIfKid(String birthday) {
        int old = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date geburt = dateFormat.parse(birthday);
            old = (int) ((new Date().getTime() - geburt.getTime()) / (1000 * 60 * 60 * 24 * 365.242199));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR - Calculate Kid Function" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return String.valueOf(old);
    }

}
