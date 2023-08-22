import java.io.*;
import javax.swing.JOptionPane;

public class Zeilen_Trennen {
    public static void main(String[] args) {
        String sourceFolderPath = JOptionPane.showInputDialog("Eingabe Pfad?"); // Pfad zum Quellordner anpassen
        String destinationFolderPath = JOptionPane.showInputDialog("Ausgabe Pfad?");  // Pfad zum Zielordner anpassen

        File sourceFolder = new File(sourceFolderPath);
        File[] sourceFiles = sourceFolder.listFiles();

        for (File sourceFile : sourceFiles) {
            if (sourceFile.isFile()) {
                int id = 1; // Zurücksetzen der ID für jede neue Quelldatei

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        char firstChar = line.charAt(0);
                        if (firstChar == 'F') {
                            writeToFile(destinationFolderPath, sourceFile.getName(), "F", "ID:" + id + ";" + line);
                            ++id;
                        } else if (firstChar == 'K') {
                            writeToFile(destinationFolderPath, sourceFile.getName(), "K", "ID:" + (id - 1) + ";" + line);
                        } else if (firstChar == 'G' && (line.contains("Ust.") || line.contains("Vst."))) {
                            writeToFile(destinationFolderPath, sourceFile.getName(), "Steuerart", "ID:" + (id - 1) + ";" + line);
                        } else {
                            writeToFile(destinationFolderPath, sourceFile.getName(), "G", "ID:" + (id - 1) + ";" + "G2;" + line);
                        }
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void writeToFile(String destinationFolderPath, String sourceFileName, String marker, String content) {
        String newFileName = sourceFileName + "_" + marker + ".txt";
        File newFile = new File(destinationFolderPath, newFileName);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile, true));
            writer.write(content);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}