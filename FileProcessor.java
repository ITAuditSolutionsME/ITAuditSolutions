import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class FileProcessor {
	//Eingabe des Anfangwertes der Zeilen, welche behalten werden sollen
	static String wasBehalten = JOptionPane.showInputDialog("Zeilen mit welchem Anfang sollen behalten werden?");
	
    public static void main(String[] args) {
    	//Eingabe des Pfades des Ordners in welchem die zu bearbeitend Datein liegen
        String folderPath = JOptionPane.showInputDialog("Was ist der Pfad des Ordners?");
        
        //Prüfen ob das Verzeichnis existiert
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            System.out.println("Gegebener Pfad ist nicht existent.");
            return;
        }

        //Prüfen ob es Datein in dem gegebenen Ordner gibt
        File[] files = folder.listFiles();
        if (files == null) {
            System.out.println("No files found in the directory.");
            return;
        }

        //Wenn Datein in Ordner vorhanden dann verarbeite diese
        for (File file : files) {
            if (file.isFile()) {
                processFile(file);
            }
        }

        System.out.println("Processing of files in the folder completed.");
    }

    private static void processFile(File file) {
        try {
        	//Liste mit allen, der zu behaltenden Zeilen
            List<String> linesToKeep = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            //Alle Spalten einer Datei werden durchgegangen und die Zeilen welche den gegebenen Wert enhalten werden in
            //die vorher angelegte Liste gespeichert
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(wasBehalten)) {
                    linesToKeep.add(line);
                }
            }
            reader.close();

            //Die Liste mit den richtigen Zeilen werden wieder in die Datei geschrieben
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String lineToKeep : linesToKeep) {
                writer.write(lineToKeep);
                writer.newLine();
            }
            writer.close();

            System.out.println("Processed: " + file.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}