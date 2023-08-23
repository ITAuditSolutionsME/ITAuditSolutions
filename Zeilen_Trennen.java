import java.io.*;
import javax.swing.JOptionPane;

public class Zeilen_Trennen {
    public static void main(String[] args) {
        String sourceFolderPath = JOptionPane.showInputDialog("Eingabe Pfad?"); // Pfad zum Quellordner anpassen
        String destinationFolderPath = JOptionPane.showInputDialog("Ausgabe Pfad?");  // Pfad zum Zielordner anpassen
        int id = 1;
        
        File sourceFolder = new File(sourceFolderPath);
        File[] sourceFiles = sourceFolder.listFiles();

        for (File sourceFile : sourceFiles) {
            if (sourceFile.isFile()) {

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        char firstChar = line.charAt(0);
                        //Hier wird geschaut ob die Zeile mit einem F beginnt
                        if (firstChar == 'F') {
                            writeToFile(destinationFolderPath, sourceFile.getName(), "F", "ID:" + id + ";" + line);
                            ++id;
                            //Hier wird geschaut ob die Zeile mit K beginnt
                        } else if (firstChar == 'K') {
                            writeToFile(destinationFolderPath, sourceFile.getName(), "K", "ID:" + (id - 1) + ";" + line);
                            //Hier wird geschaut ob die Zeile mit G beginnt und Ust. oder Vst. in der Zeile vorkommt
                        } else if (firstChar == 'G' && (line.contains("Ust.") || line.contains("Vst."))) {
                            writeToFile(destinationFolderPath, sourceFile.getName(), "Steuerart", "ID:" + (id - 1) + ";" + "G2;" + line);
                            //All anderen Zeilen, also in diesem Fall alle welche mit G beginnen aber keine Ust. oder Vst. haben
                        } else {
                            writeToFile(destinationFolderPath, sourceFile.getName(), "G", "ID:" + (id - 1) + ";" + line);
                        }
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//Hier werden die Zeilen in die jeweiligen neu erzeugten Datein geschrieben und richtig benannt.
//Benannt werden die Datein wie folgt: Name der Quelldatei + ein Merkmal der Zeilen welche sie abbilden
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