import db.FoundService;
import dto.AllArtifacts;
import dto.Coins;
import dto.Jewellery;
import dto.Weapon;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    private final FoundService foundService;

    public Program() throws SQLException {
        foundService = new FoundService();
        menu();
    }

    private void menu() throws SQLException {
        String choice = "0";
        while (!(choice.equals("4"))) {
            displayMainMenu();
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showAllArtifacts();
                case "2" -> showAllArtifactsOlderThan();
                case "3" -> showAmountOfArtifacts();
                case "4" -> quit();
                default -> System.out.println("Vennligst velg et tall fra 1 - 4");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("1. Se informasjon om alle funngjenstander");
        System.out.println("2. Se informasjon om alle funngjenstander eldre enn <årstall>");
        System.out.println("3. Få informasjon om antall funngjenstander registrert");
        System.out.println("4. Avslutt programmet");
    }

    private void showAllArtifacts() throws SQLException {
        System.out.println("Nedenfor finner du listen av alle fun:");
        List<AllArtifacts> allArtifacts = foundService.getArtifacts();
        allArtifacts.forEach(System.out::println);
    }

    private void showAllArtifactsOlderThan() throws SQLException {
        System.out.println("Vennligst skriv et årstall:");
        Scanner scanner = new Scanner(System.in);
        int year = Integer.parseInt(scanner.nextLine());

        Optional<List<AllArtifacts>> optionalArtifacts = foundService.getArtifactsOlderThan(year);

        if (optionalArtifacts.isPresent()) {
            List<AllArtifacts> allArtifacts = optionalArtifacts.get();
            for (AllArtifacts artifact : allArtifacts) {
                System.out.println(artifact);
            }
        } else {
            System.out.println("Vi kan ikke se å ha funnet noe eldre enn " + year);
        }
    }

    private void showAmountOfArtifacts() throws SQLException {
        List<AllArtifacts> allArtifacts = foundService.getArtifacts();
        List<Jewellery> jewelleries = foundService.getJewellery();
        List<Coins> coins = foundService.getCoins();
        List<Weapon> weapons = foundService.getWeapon();
        System.out.println("Det finnes totalt " + allArtifacts.size() + " registrert i vårt system.");
        System.out.println("Antall smykke: " + jewelleries.size());
        System.out.println("Antall mynt: " + coins.size());
        System.out.println("Antall våpen: " + weapons.size());
    }

    private void quit() {
        System.out.println("Ha en fin dag videre.");
    }
}
