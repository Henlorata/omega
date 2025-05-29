import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    new Main();
  }

  Main() {
    // 1. Feladat
    ArrayList<Utazok> data = Utazok.readFileLines("utazok.csv");
    System.out.println("0) " + data.size() + " utazó adata beolvasva.");

    // 2. Feladat
    System.out.println("1) Összesen " + data.stream().map(utas -> utas.place).distinct().count() + " városba utaztak,");
    String randomPlace = data.stream()
        .map(utas -> utas.place)
        .distinct()
        .skip((int) (Math.random() * data.stream().map(utas -> utas.place).distinct().count()))
        .findFirst()
        .orElse("Nincs város");
    System.out.println("\tKöztük egy véletlenszerűen kiválasztott: " + randomPlace);
    System.out.println("\tEbbe a városba " + data.stream().filter(utas -> utas.place.equals(randomPlace)).count() + " utazó érkezett.");

    // 3. Feladat
    String earliestTime = data.stream()
        .map(utas -> utas.time)
        .min(String::compareTo)
        .orElse("Nincs idő");
    System.out.println("2) A legkorábbi indulás: " + earliestTime);
    List<Utazok> beforeNoon = data.stream()
        .filter(utas -> utas.time.compareTo("12:00") < 0)
        .toList();
    System.out.println("\tÖsszesen " + beforeNoon.size() + " utas érkezett délelőtt.");

    // 4. Feladat;
    // Count the number of travelers in each month when there was at least one traveler. If the month is 1 digits, then add a leading zero. Do this with streams.

    System.out.println("3) Hónapok száma, amikor volt utas:");
    data.stream()
        .map(utas -> utas.date.split("\\.")[0]) // Extract month from date
        .distinct() // Get unique months
        .sorted() // Sort months
        .forEach(month -> System.out.println("\t" + month + ".hó : " +
            data.stream().filter(utas -> utas.date.split("\\.")[0].equals(month)).count() + " utazás"));

    // 5. Feladat
    List<String> firstNames = data.stream()
        .map(utas -> utas.name.split(" ")[0]) // Extract first name
        .filter(firstName -> data.stream().filter(utas -> utas.name.split(" ")[0].equals(firstName)).count() > 1) // Count occurrences
        .distinct() // Get unique first names
        .sorted() // Sort first names
        .toList();

    System.out.println("4) Többször szereplő vezetéknevek:");
    System.out.println("\t" + String.join(", ", firstNames));

    // 6. Feladat
    System.out.println("5) Ugyanazon a napon kettőnél több utazás: " +
        String.join(", ",
            data.stream()
                .map(utas -> utas.date) // Extract date
                .distinct() // Get unique dates
                .filter(date -> data.stream().filter(utas -> utas.date.equals(date)).count() > 2) // Count occurrences
                .sorted() // Sort dates
                .map(date -> date + " (" + data.stream().filter(utas -> utas.date.equals(date)).count() + ")")
                .toList()));

    // 7. Feladat

    ArrayList<String> toWrite = new ArrayList<>();
    data.stream().filter(utas -> utas.place.equals("Szeged")).forEach(utas -> toWrite.add(utas.name + "(" + utas.date + " " + utas.time + ")"));
    Utazok.writeFile("szeged.txt", toWrite, false);
    System.out.println("6) Szegedre utazók kiírva a szeged.txt fájlba.");

  }


  public static class Utazok {

    String name;
    String place;
    String date;
    String time;

    Utazok(String line) {
      String[] lineData = line.split(";");
      this.name = lineData[0];
      this.place = lineData[1];
      this.date = lineData[2];
      this.time = lineData[3];
    }

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

    public static ArrayList<Utazok> readFileLines(String fileName) {
      File file = new File(fileName);
      ArrayList<Utazok> lines = new ArrayList<>();

      if (!file.exists() || !file.isFile()) {
        System.err.println("Error: File '" + fileName + "' not found or is not a regular file.");
        return lines;
      }

      try (FileInputStream fis = new FileInputStream(file);
           InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
           BufferedReader reader = new BufferedReader(isr)) {

        String line;
        while ((line = reader.readLine()) != null) {
          lines.add(new Utazok(line));
        }
      } catch (IOException e) {
        System.err.println("Error reading lines from file '" + fileName + "': " + e.getMessage());
        // lines.clear();
      }
      return lines;
    }
  }
}

/*

 Az utazok.csv fájl különböző utazások adatait (név, város, dátum, indulás)
 tartalmazza, pontosvesszővel elválasztva, utf-8 kódolással. NINCS fejléc!
 Hozzunk létre egy Utazok nevű projektet és oldjuk meg a következő feladatokat!

 0) Olvassuk be a fájl adatait egy megfelelő adatszerkezetbe,
    és jelenítsük meg a beolvasott adatok számát!.....................(2p)
 1) Határozzuk meg hány féle városba utaztak!.........................(1p)
    Ezek közül válasszunk ki egyet véletlenszerűen,...................(1p)
    és írjuk ki hányan utaztak abba a városba összesen!...............(1p)
 2) Írjuk ki a legkorábbi indulás időpontját!.........................(1p)
    Írjuk ki azt is, összesen hány utazás kezdődött délelőtt!.........(1p)
 3) Készísünk statisztikát a havonkénti utazások számából!............(2p)
 4) Írjuk ki azokat a vezetékneveket (első név) ABC sorrendben,
    amelyek egynél többször fordulnak elő!............................(2p)
 5) Írjuk ki azokat a napokat, ahol kettőnél többen utaztak!..........(2p)
 6) Írjuk ki a szeged.txt fájlba a mintának megfelelően
    a Szegedre utazók adatait!........................................(2p)

 Minta:
 0) 50 utazó adata beolvasva
 1) Összesen 9 városba utaztak
    Közülük egy véletlenszerűen kiválasztott: Pécs
    Ebbe a városba 7 utazó érkezett
 2) Legkorábbi indulás: 10:17
    Összesen 5 utazás kezdődött délelőtt
 3) Utazások száma hónaponként:
    03.hó : 13 utazás
    04.hó : 17 utazás
    05.hó : 9 utazás
    06.hó : 11 utazás
 4) Többször szereplő vezetéknevek:
    Kocsis Kovács Molnár Papp Szabó Tóth Varga
 5) Ugyanazon a napon kettőnél több utazás: 04.10(3) 05.10(4) 06.11(3)
 6) Szegedre utazók kiírva a szeged.txt fájlba

 szeged.txt:
 Molnár Éva (05.25 18:43)
 Barna Ádám (04.16 11:45)
 Kuncz László (06.27 23:37)
 Varga András (04.23 15:56)

*/