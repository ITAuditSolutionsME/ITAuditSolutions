import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class Datein_Zusammenfassen {
    public static void main(String[] args) {
        String inputFolderPath = JOptionPane.showInputDialog("Eingabe Pfad?");
        String outputFolderPath = JOptionPane.showInputDialog("Ausgabe Pfad?");
        String zI = JOptionPane.showInputDialog("Sollen Zeichen am Anfang ignoriert werden, wenn ja wie viele? Sonst 0. Eingabe muss eine Zahl sein");
        int zeichenIgnorieren = Integer.parseInt(zI);

        File inputFolder = new File(inputFolderPath);
        File[] files = inputFolder.listFiles();

        // Map zur Speicherung von Dateinamen und ihren Zeilen
        Map<String, List<String>> fileLinesMap = new HashMap<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName().substring(zeichenIgnorieren); // Erste 8 Zeichen ignorieren
                    List<String> lines = fileLinesMap.getOrDefault(fileName, new ArrayList<>());

                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            lines.add(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    fileLinesMap.put(fileName, lines);
                }
            }

            // Ausgabe der zusammengefügten Dateien
            for (Map.Entry<String, List<String>> entry : fileLinesMap.entrySet()) {
                String outputFileName = outputFolderPath + File.separator + entry.getKey();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
                    for (String line : entry.getValue()) {
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Dateien wurden zusammengefasst.");
        } else {
            System.out.println("Keine Dateien im Eingabeordner gefunden.");
        }
    }
}