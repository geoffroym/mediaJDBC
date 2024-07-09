import com.sun.source.tree.ArrayAccessTree;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.util.Set;

public class Program {
    public void readFromFile() throws FileNotFoundException {
        //next sentence is general, but it throws the exception over
        File file = new File("lesFraFil.txt");
        try (Scanner scanner = new Scanner(file)) {
            System.out.println("Reading from file...");
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        }
        System.out.println("Reading complete.");
    }

    public Set<Artist> readArtistToSet() throws FileNotFoundException {
        File file = new File("artisterMedIdentifikasjon.txt");
        Set<Artist> artists = new HashSet<>();
        try (Scanner scanner = new Scanner(file);) {
            System.out.println("Reading from file...");
            while (scanner.hasNextLine()) {
                Artist artist = new Artist();
                String artistId = scanner.nextLine();
                artist.setId(Integer.parseInt(artistId));
                String artistName = scanner.nextLine();
                artist.setArtistName(artistName);
                String stringDateOfBirth = scanner.nextLine();
                artist.setDateOfBirth(LocalDate.parse(stringDateOfBirth));
                String city = scanner.nextLine();
                artist.setCity(city);
                String country = scanner.nextLine();
                artist.setCountry(country);
                scanner.nextLine();
                artists.add(artist);
            }
        }
        System.out.println("Reading complete.");
        return artists;
    }

    public ArrayList<Artist> readArtist() throws FileNotFoundException {
        File file = new File("Id_artister.txt");
        ArrayList<Artist> artists = new ArrayList<>();
        try (Scanner scanner = new Scanner(file);) {
            System.out.println("Reading from file...");
            while (scanner.hasNextLine()) {
                Artist artist = new Artist();
                String artistId = scanner.nextLine();
                artist.setId(Integer.parseInt(artistId));
                String artistName = scanner.nextLine();
                artist.setArtistName(artistName);
                String stringDateOfBirth = scanner.nextLine();
                artist.setDateOfBirth(LocalDate.parse(stringDateOfBirth));
                String city = scanner.nextLine();
                artist.setCity(city);
                String country = scanner.nextLine();
                artist.setCountry(country);
                scanner.nextLine();
                artists.add(artist);
            }
        }
        System.out.println("Reading complete.");
        return artists;
    }

    public void writeToFile() throws IOException {
        File file = new File("skrivTilFil.txt");
        file.createNewFile();
        Scanner scanner = new Scanner(System.in);
        FileWriter writer = new FileWriter(file);
        System.out.println("Writing content file...");
        for (int i = 0; i < 5; i++) {
            System.out.println("Enter sentence: ");
            String s = scanner.nextLine();
            writer.write(s);
            writer.write("\n");
        }
        writer.close();
        scanner.close();
    }

    public void writeArtistToFile(ArrayList<Artist> artists) throws IOException {
        File file = new File("Id_artister.txt");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        for (Artist a : artists){
            fileWriter.write(a.getId() + "\n");
            fileWriter.write(a.getArtistName() + "\n");
            fileWriter.write(a.getDateOfBirth().toString() + "\n");
            fileWriter.write(a.getCity() + "\n");
            fileWriter.write(a.getCountry() + "\n");
            fileWriter.write("---");
            fileWriter.write("\n");
        }
        fileWriter.close();
    }
}
