package fxml;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Printer {
    public static void printImage(String imagePath) {
        try {
            FileInputStream fis = new FileInputStream(new File(imagePath));
            Doc doc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.PNG, null);
            PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            attr.add(OrientationRequested.PORTRAIT);
            attr.add(MediaSizeName.ISO_A4);

            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            if (printServices.length > 0) {
                PrintService defaultPrinter = PrintServiceLookup.lookupDefaultPrintService();
                DocPrintJob job = defaultPrinter.createPrintJob();
                job.print(doc, attr);
            } else {
                System.out.println("No printer services found");
            }

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PrintException e) {
            e.printStackTrace();
        }
    }
}
