package fxml;


import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.WriterException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRSchweiz {

    private static String PortalID;
    private static String GID;
    private static String Artikel;
    private static String Nachname;
    private static String Vornamen;
    private static String Geburtsdatum;
    private static String Sprache;

    private static final String TITLE = "Pass- und Identitätskartendienst des Kantons Bern";
    private static final String FOOTER = "Die Produktionsfreigabe erfolgt erst nach Bezahlung der Gebühren und Erhalt allfälliger fehlender Dokumente";


    public static void getParameterFromWebsocketList(List<String> antwortliste) {
        int zaehler = 0;
        while (zaehler < 6) {
            for (String entity : antwortliste) {
                if (zaehler == 0) {
                    if (entity.contains("operatorPortalId")) {
                        String[] temp = entity.split("'");
                        PortalID = temp[1];
                        zaehler++;
                        continue;
                    }
                }
                if (zaehler == 1) {
                    if (entity.contains("businessId")) {
                        String[] temp = entity.split("'");
                        GID = temp[1];
                        zaehler++;
                        continue;
                    }
                }
                if (zaehler == 2) {
                    if (entity.contains("codeID")) {
                        String[] temp = entity.split("=");
                        Artikel = temp[1].replace("'", "").replace("]", "").replace(",", "").trim();
                        zaehler++;
                        continue;
                    }
                }
                if (zaehler == 3) {
                    if (entity.contains("name")) {
                        String[] temp = entity.split("'");
                        Nachname = temp[1];
                        zaehler++;
                        continue;
                    }
                }
                if (zaehler == 4) {
                    if (entity.contains("firstNames")) {
                        String[] temp = entity.split("'");
                        Vornamen = temp[1];
                        zaehler++;
                        continue;
                    }
                }
                if (zaehler == 5) {
                    if (entity.contains("dateOfBirth")) {
                        String[] temp = entity.split("'");
                        Geburtsdatum = temp[1];
                        zaehler++;
                        continue;
                    }
                }
                if (zaehler == 6) {
                    if (entity.contains("language")) {
                        String[] temp = entity.split("'");
                        Sprache = temp[1];
                        zaehler++;
                        //return ?;
                    }
                }
            }
        }
        CreateSwissQR(PortalID, GID, Artikel,Nachname, Vornamen, Geburtsdatum, Sprache);
    }

    private static void CreateSwissQR(String PortalID, String GID, String Artikel, String Nachname, String Vornamen,
    String Geburtsdatum, String Sprache) {
        String Geburt = Geburtsdatum;
        StringBuilder sb = new StringBuilder();
        Geburtsdatum = Global.calculateAgeIfKid(Geburt);
        int Kind = 0;
        try {
            Kind = NumberFormat.getInstance().parse(Geburtsdatum).intValue();
        } catch (ParseException e) {
            //
        }

        String[] subs = Artikel.split(" ");
        double KindArtikel = 0;

        if (Kind < 18) {
            NumberFormat provider = NumberFormat.getInstance();
            provider.setMaximumFractionDigits(2);
            provider.setMinimumFractionDigits(2);
            try {
                KindArtikel = provider.parse(subs[0]).doubleValue();
            } catch (ParseException e) {
                //
            }
            KindArtikel += 0.010;
            subs[0] = String.format("%.2f", KindArtikel).replace(",", ".");
        }

        try{
            // create QR object with relevant data
            QR qr = new QR(TITLE + sb.toString(), Artikel, PortalID + GID, FOOTER);

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
            //
        }

    }
}

    
    


