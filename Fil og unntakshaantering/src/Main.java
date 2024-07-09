import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Program program = new Program();
        ArrayList<Artist> artists = null;
        Set<Artist> artistSet = null;

        try {
            artists = program.readArtist();
            artistSet = program.readArtistToSet();
            System.out.println("Artists: ");
            for (Artist a : artists){
                a.printString();
            }
            System.out.println("---");
            for (Artist a : artistSet){
                a.printString();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Exception when calling readArtist: " + e.getMessage());
            e.printStackTrace();
        }

        int choice = 1;
        Scanner scanner = new Scanner(System.in);
        while (choice != 3) {
            System.out.println("Welcome! See your option bellow: ");
            System.out.println("1. Show all artists ordered by name.");
            System.out.println("2. Show all artists ordered by age.");
            System.out.println("3. Quit.");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> showArtistsOrderedByName(artists);
                case 2 -> showArtistsOrderedByAge(artists);
                case 3 -> quit(artists);
                default -> {
                    System.out.println("Please, pick a number from 1 - 3.");
                }
            }
        }
        /*Artist artist1 = new Artist(8, "Camila", LocalDate.now(), "Brasilia", "Brazil" );
        artists.add(artist1);

        try {
            program.writeArtistToFile(artists);
        } catch (FileNotFoundException e) {
            System.out.println("Exception when calling readArtist: " + e.getMessage());
            e.printStackTrace();
        }*/

    }

    public static void quit(ArrayList<Artist> artists){
        System.out.println("Bye");
    }

    public static void showArtistsOrderedByName(ArrayList<Artist> artists){
        Collections.sort(artists, new ArtistNameComparator());
        for (Artist a: artists){
            a.printString();
        }
    }

    public static void showArtistsOrderedByAge(ArrayList<Artist> artists){
        Collections.sort(artists, (a1, a2) -> {
            return a1.getDateOfBirth().compareTo(a2.getDateOfBirth());
        });
        for (Artist a: artists){
            a.printString();
        }
    }
}
