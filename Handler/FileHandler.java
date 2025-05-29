import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

  public static boolean writeFile(String fileName, ArrayList<String> content, boolean append) {
    File file = new File(fileName);
    File parentDir = file.getParentFile();
    if (parentDir != null && !parentDir.exists()) {
      if (!parentDir.mkdirs()) {
        System.err.println("Error: Could not create parent directories for " + fileName);
        return false;
      }
    }

    try (FileOutputStream fos = new FileOutputStream(file, append);
         OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
         BufferedWriter writer = new BufferedWriter(osw)) {

      for (String line : content) {
        writer.write(line);
        writer.newLine();
      };
      // writer.newLine();
      return true;
    } catch (IOException e) {
      System.err.println("Error writing to file '" + fileName + "': " + e.getMessage());
      return false;
    }
  }

  public static ArrayList<Main.Utazok> readFileLines(String fileName) {
    File file = new File(fileName);
    ArrayList<Main.Utazok> lines = new ArrayList<>();

    if (!file.exists() || !file.isFile()) {
      System.err.println("Error: File '" + fileName + "' not found or is not a regular file.");
      return lines;
    }

    try (FileInputStream fis = new FileInputStream(file);
         InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(isr)) {

      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(new Main.Utazok(line));
      }
    } catch (IOException e) {
      System.err.println("Error reading lines from file '" + fileName + "': " + e.getMessage());
      // lines.clear();
    }
    return lines;
  }

}