package punkte_eleminieren;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;

public class PunktEleminieren {
    public static void main(String[] args) {
    	//Abfrage des Users nach dem Pfad und dem anfänglichen Zeichen welches gelöscht werden soll
    	String DateiPfad = JOptionPane.showInputDialog("Wie lautet der Dateipfad(Ohne Anführungszeichen)");
    	String Eliminieren = JOptionPane.showInputDialog("Welches Zeichen soll gelöscht werden");
    	String directoryPath = DateiPfad;
    	

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        
        //Prüfen ob der gegebene Pfad Datein enhält
        if (files != null) {
        	//Für alle Datein werden die folgenden Schritte durchlaufen
            for (File file : files) {
                if (file.isFile() && file.getName().startsWith(Eliminieren)) {
                	//Der Wert des ersten Zeichens wird abgespeichert
                    String newName = file.getName().substring(1);
                    File renamedFile = new File(directoryPath + newName);
                    //Prüfen ob das erste Zeichen mit dem, vom User angegebenen Zeichen übereinstimmt
                    if (file.renameTo(renamedFile)) {
                    	//Stimmt es überein wird die Datei in den Namen ohne das Zeichen abgespeichert
                        System.out.println("Datei " + file.getName() + " umbenannt zu " + renamedFile.getName());
                    } else {
                    	//Ist das Zeichen nicht das selbe wie das vom User, wird der Dateiname nicht verändert
                        System.out.println("Fehler beim Umbenennen der Datei " + file.getName());
                    }
                }
            }
        }
    }
}
