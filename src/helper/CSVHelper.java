package helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Julius
 */
public class CSVHelper {

    private String pathString;
    private Path path;
    private String csvSplitBy;

    /**
     * Class constuctor.
     * @param pathString The path to the CSV file.
     */
    public CSVHelper(String pathString) {
        this.pathString = pathString;
        this.path = Paths.get(pathString);
        csvSplitBy = ";";
    }

    /**
     * Class constructor.
     * @param pathString The path to the CSV file.
     * @param csvSplitBy The character that splits the Entries.
     */
    public CSVHelper(String pathString, String csvSplitBy) {
        this.pathString = pathString;
        this.path = Paths.get(pathString);
        this.csvSplitBy = csvSplitBy;
    }
    
    /**
     * Add a line to a existing CSV File
     * @param value The entries in an array of Strings.
     */
    public void addLine(String[] value) {
        try (FileWriter writer = new FileWriter(pathString, true)) {
            CSVWriter.writeLine(writer, Arrays.asList(value));

            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(CSVHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Write a line to a file. If there are already lines in it, they will be overwritten.
     * @param value The entries in an array of Strings.
     */
    public void writeLine(String[] value) {
        try (FileWriter writer = new FileWriter(pathString)) {
            CSVWriter.writeLine(writer, Arrays.asList(value));

            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(CSVHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Deletes an complete line in a CSV file.
     * @param line The number of the line that should be deleted.
     * @throws IOException
     */
    public void deleteEntry(int line) throws IOException {

        File original = new File(path.toUri());
        File copy = new File("copy.csv");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(copy)));
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(original)));

        int counter = 0;

        String currLine;
        while ((currLine = reader.readLine()) != null) {
            if (counter != (line - 1)) {
                writer.write(currLine);
                writer.newLine();
            }
            counter++;
        }

        writer.close();
        reader.close();

        File pap = new File("pap.csv");
        File placeholder = original;
        original.renameTo(pap);
        original.delete();
        pap.delete();

        if (copy.renameTo(placeholder)) {
            System.out.println("File erfolgreich umbenannt");
        } else {
            System.err.println("Umbennnen fehlgeschlagen");
        }
    }

    /**
     * Returns the number of lines in a CSV file.
     * @return Number of lines
     */
    public int getLinesValue() {
        int i = 0;
        try {
            if (Files.notExists(path)) {
                return -1;
            }
            BufferedReader reader = Files.newBufferedReader(path);
            while ((reader.readLine() != null)) {
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(CSVHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    /**
     * Returns the Entries in a specific line.
     * @param line Number of line
     * @return Entries
     */
    public String[] getLine(int line) {
        String readLine = "";
        String[] values = null;
        try {
            if (getLinesValue() < line) {
                return values;
            }
            BufferedReader reader = Files.newBufferedReader(path);
            for (int i = 0; i < line; i++) {
                readLine = reader.readLine();
                values = readLine.split(csvSplitBy);
            }
        } catch (IOException ex) {
            Logger.getLogger(CSVHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values;
    }
}
