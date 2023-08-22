import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JOptionPane;

public class Ordner_Auflösen {
    public static void main(String[] args) {
        String sourceDirectory = JOptionPane.showInputDialog("Eingabe Pfad?"); // Hier den Pfad zum Quellverzeichnis angeben
        String outputDirectory = JOptionPane.showInputDialog("Ausgabe Pfad?"); // Hier den Pfad zum Ausgabeverzeichnis angeben

        File sourceDir = new File(sourceDirectory);
        File outputDir = new File(outputDirectory);

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            System.out.println("Quellverzeichnis existiert nicht oder ist kein Verzeichnis.");
            return;
        }

        if (!outputDir.exists() || !outputDir.isDirectory()) {
            System.out.println("Ausgabeverzeichnis existiert nicht oder ist kein Verzeichnis.");
            return;
        }

        copyFilteredFolderContents(sourceDir, outputDir);
    }

    private static void copyFilteredFolderContents(File sourceDir, File outputDir) {
        File[] files = sourceDir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    copyFilteredFolderContents(file, outputDir);
                } else {
                    if (file.getName().matches("\\d.*") && !file.getName().contains("STAMM")) {
                        File outputFile = new File(outputDir, file.getName());
                        try {
                            Files.copy(file.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Datei kopiert: " + outputFile.getAbsolutePath());
                        } catch (IOException e) {
                            System.out.println("Fehler beim Kopieren der Datei: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }
}