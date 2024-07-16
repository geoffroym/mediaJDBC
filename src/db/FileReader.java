package db;

import dto.Books;
import dto.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    public List<Books> parseBooksFromFile() throws FileNotFoundException {
        List<Books> books = new ArrayList<>();
        File file = new File("media.txt");
        Scanner scanner = new Scanner(file);

        scanner.nextLine(); //skip "Books:"
        int numberOfBooks = Integer.parseInt(scanner.nextLine().trim());
        for(int i = 0; i < numberOfBooks; i++){
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println("reading id" + id);
            String title = scanner.nextLine();
            System.out.println("reading title" + title);
            String author = scanner.nextLine();
            System.out.println("reading author" + author);
            String genre = scanner.nextLine();
            System.out.println("reading genre" + genre);
            int pages = Integer.parseInt(scanner.nextLine());
            System.out.println("reading pages" + pages);
            int mediaId = Integer.parseInt(scanner.nextLine());
            System.out.println("reading media" + mediaId);
            books.add(new Books(id, title, author, genre, pages, new Media(mediaId, "Book")));
        }
        return books;
    }

    public List<Movies> parseMoviesFromFile() throws FileNotFoundException {
        List<Movies> movies = new ArrayList<>();
        File file = new File("media.txt");
        Scanner scanner = new Scanner(file);

        skipToSection(scanner, "Movies:");
        //scanner.nextLine();

        int numberOfMovies = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numberOfMovies; i++){
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println("reading id" + id);
            String title = scanner.nextLine();
            System.out.println("reading title" + title);
            String director = scanner.nextLine();
            System.out.println("reading director" + director);
            String genre = scanner.nextLine();
            System.out.println("reading genre" + genre);
            int ageRating = Integer.parseInt(scanner.nextLine());
            System.out.println("reading agerating" + ageRating);
            int mediaId = Integer.parseInt(scanner.nextLine());
            System.out.println("reading media" + mediaId);
            movies.add(new Movies(id, title, director, genre, ageRating, new Media(mediaId, "Movies")));
        }
        scanner.close();
        return movies;
    }

    public List<Games> parseGamesFromFile() throws FileNotFoundException {
        List<Games> games = new ArrayList<>();
        File file = new File("media.txt");
        Scanner scanner = new Scanner(file);

        skipToSection(scanner, "Games:");
        //scanner.nextLine();
        int numberOfGames = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numberOfGames; i++){
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println("reading id" + id);
            String title = scanner.nextLine();
            System.out.println("reading title" + title);
            String developer = scanner.nextLine();
            System.out.println("reading developer" + developer);
            String publisher = scanner.nextLine();
            System.out.println("reading publisher" + publisher);
            int ageRating = Integer.parseInt(scanner.nextLine());
            System.out.println("reading agerating" + ageRating);
            String mode = scanner.nextLine();
            System.out.println("reading mode " + mode);
            int mediaId = Integer.parseInt(scanner.nextLine());
            System.out.println("reading media " + mediaId);

            List<Platform> platforms = new ArrayList<>();
            int numberOfPlatforms = Integer.parseInt(scanner.nextLine().trim());
            for (int j = 0; j < numberOfPlatforms; j++){
                int platformId = Integer.parseInt(scanner.nextLine());
                System.out.println("reading platID " + platformId);
                String platformName = scanner.nextLine();
                System.out.println("reading platName " + platformName);
                platforms.add(new Platform(platformId, platformName));
            }
            games.add(new Games(id, title, developer, publisher, ageRating, mode, new Media(mediaId, "Games"), platforms));
        }
        scanner.close();
        return games;
    }


    private void skipToSection(Scanner scanner, String sectionName) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals(sectionName)) {
                break;
            }
        }
    }


}
