import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    private final WorldProvider worldProvider;

    public Program() {
        worldProvider = new WorldProvider();
    }

    public void splitAustralia() throws SQLException, FileNotFoundException {
        System.out.println("Updating world database with Australia split.");
        File file = new File("australia.txt");
        try (Scanner reader = new Scanner(file);
             Connection con = worldProvider.getWorldDS().getConnection()){
            boolean autoCommit = con.getAutoCommit();
            con.setAutoCommit(false);
            try {
                while (reader.hasNext()) {
                    Country country = readCountry(reader);
                    worldProvider.addCountry(country, con);
                    int nrOfCities = Integer.parseInt(reader.nextLine());
                    for (int i = 0; i < nrOfCities; i++) {
                        worldProvider.moveCity(Integer.parseInt(reader.nextLine()), country.code(), con);
                    }
                    int nrOfCountryLanguages = Integer.parseInt(reader.nextLine());
                    for (int i = 0; i < nrOfCountryLanguages; i++) {
                        CountryLanguage newCountryLanguage = readCountryLanguage(reader, country.code());
                        worldProvider.addCountryLanguage(newCountryLanguage, con);
                    }
                    reader.nextLine(); //Reading --- separator
                }
                worldProvider.deleteAllCountryLanguagesInCountry("AUS", con);
                worldProvider.deleteCountry("AUS", con);
            } catch (SQLException sqle){
                con.rollback();
            } finally {
                con.setAutoCommit(autoCommit);
            }
        }
        System.out.println("Australia split done.");
    }

    private CountryLanguage readCountryLanguage(Scanner reader, String countryCode) {
        String language = reader.nextLine();
        String isOfficialTxt = reader.nextLine();
        boolean isOfficial = isOfficialTxt.equalsIgnoreCase("T");
        float percentage = Float.parseFloat(reader.nextLine());
        return new CountryLanguage(countryCode, language, isOfficial, percentage);
    }

    private Country readCountry(Scanner reader){
        String code = reader.nextLine();
        String name = reader.nextLine();
        String continent = reader.nextLine();
        String region = reader.nextLine();
        double surfaceArea = Double.parseDouble(reader.nextLine());
        int indepYear = Integer.parseInt(reader.nextLine());
        int population = Integer.parseInt(reader.nextLine());
        double lifeExpectancy = Double.parseDouble(reader.nextLine());
        double gnp = Double.parseDouble(reader.nextLine());
        double gnpOld = Double.parseDouble(reader.nextLine());
        String localName = reader.nextLine();
        String governmentForm = reader.nextLine();
        String headOfState = reader.nextLine();
        int capital = Integer.parseInt(reader.nextLine());
        String code2 = reader.nextLine();
        return new Country(code, name, continent, region, surfaceArea, indepYear,
                population, lifeExpectancy, gnp, gnpOld, localName, governmentForm,
                headOfState, capital, code2);
    }

    public void run() throws SQLException {
        int choice = 1;
        System.out.println("Welcome to WorldInfo!");
        System.out.println("1. Show information about all countries.");
        System.out.println("2. Show information about all cities.");
        System.out.println("3. Show all cities based on country code.");
        System.out.println("4. Add city.");
        System.out.println("5. Update city.");
        System.out.println("6. Delete city.");
        System.out.println("7. Get city.");
        System.out.println("8. Quit.");
        Scanner userInput = new Scanner(System.in);
        while (choice != 8) {
            choice = userInput.nextInt();
            switch (choice) {
                case 1 -> showAllCountries();
                case 2 -> showAllCities();
                case 3 -> showCityByCountryCode();
                case 4 -> addCity();
                case 5 -> updateCity();
                case 6 -> deleteCity();
                case 7 -> getCity();
                case 8 -> quit();
                default -> System.out.println("Please, pick a number from 1 - 8.");
            }
        }
    }

    private void getCity() throws SQLException {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter city id:");
        int id = Integer.parseInt(userInput.nextLine());
        Optional<City> city = worldProvider.getCity(id);
        if(city.isPresent()){
            System.out.println(city.get().name());
        }
    }

    private void showAllCountries() {
        List<Country> countries = worldProvider.getAllCountries();
        System.out.println("Here are all the countries:");
        for (Country country : countries) {
            System.out.println(country);
        }
    }

    private void showAllCities() {
        List<City> cities = worldProvider.getAllCities();
        System.out.println("Here are all cities:");
        for (City city : cities) {
            System.out.println(city);
        }
    }

    private void showCityByCountryCode() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter country code:");
        String countryCode = userInput.nextLine();
        List<City> cities = worldProvider.getAllCitiesByCountryCode(countryCode);
        for (City city : cities) {
            System.out.println(city);
        }
    }

    private void addCity() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter city name:");
        String cityName = scanner.nextLine();
        System.out.println("Enter country code:");
        String countryCode = scanner.nextLine();
        System.out.println("Enter district:");
        String district = scanner.nextLine();
        System.out.println("Enter population:");
        String stringPop = scanner.nextLine();
        int population = Integer.parseInt(stringPop);
        int generatedId = worldProvider.addNewCity(cityName, countryCode, district, population);
        if (generatedId > 1) {
            System.out.println("New city created successfully. Id=" + generatedId);
        } else {
            System.out.println("Unable to create city.");
        }
    }

    private void updateCity() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id:");
        String stringId = scanner.nextLine();
        int id = Integer.parseInt(stringId);
        System.out.println("Enter new city name:");
        String cityName = scanner.nextLine();
        System.out.println("Enter new district:");
        String district = scanner.nextLine();
        System.out.println("Enter new country code:");
        String countryCode = scanner.nextLine();
        System.out.println("Enter new population:");
        String stringPop = scanner.nextLine();
        int population = Integer.parseInt(stringPop);
        City newCity = new City(id, cityName, countryCode, district, population);
        int rowsAffected = worldProvider.updateNewCity(newCity);
        if (rowsAffected == 1) {
            System.out.println("New city updated successfully.");
        } else {
            System.out.println("Unable to update city.");
        }
    }

    private void deleteCity() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id of city you want to delete:");
        int id = Integer.parseInt(scanner.nextLine());
        int rowsAffected = worldProvider.deleteCity(id);
        if (rowsAffected == 1) {
            System.out.println("City deleted successfully.");
        } else {
            System.out.println("Unable to delete city.");
        }
    }

    private void quit() {
        System.out.println("Bye");
    }
}
