package db;

import com.mysql.cj.jdbc.MysqlDataSource;
import dto.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FoundService {
    private static final String INSERT_PERSON_SQL = "INSERT INTO person (Id, Navn, Tlf, E_post) VALUES (?, ?, ?, ?)";
    private static final String INSERT_MUSEUM_SQL = "INSERT INTO museum (Id, Navn, Sted) VALUES (?, ?, ?)";
    private static final String INSERT_MYNT_SQL = "INSERT INTO mynt (Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Museum_id, Type, Diameter, Metall)" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_VAAPEN_SQL = "INSERT INTO vaapen (Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Museum_id, Type, VaapenType, Materialle, Vekt)" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_SMYKKE_SQL = "INSERT INTO smykke (Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Museum_id, Type, Vardiestimat, filnavn)" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_COIN_SQL = "select Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Museum_id, Type, Diameter, Metall from mynt";
    private static final String GET_WEAPON_SQL = "select Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Museum_id, Type, VaapenType, Materiale, Vekt FROM vaapen";
    private static final String GET_JEWELLERY_SQL = "select Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Museum_id, Type, Verdiestimat, filNavn FROM smykke";
    private static final String GET_ALL_ARTIFACTS = """
            SELECT m.Funnsted, m.Finner_id, p.Navn AS FinnerNavn, m.Funntidspunkt, m.Antatt_årstall, m.Museum_id, m.Type AS FunnType, mu.Navn AS MuseumNavn
            FROM mynt m\s
            JOIN person p ON m.Finner_id = p.Id
            JOIN museum mu ON mu.Id = m.Museum_id
            UNION
            SELECT v.Funnsted, v.Finner_id, p.Navn AS FinnerNavn, v.Funntidspunkt, v.Antatt_årstall, v.Museum_id, v.Type AS FunnType, mu.Navn AS MuseumNavn
            FROM vaapen v\s
            JOIN person p ON v.Finner_id = p.Id
            JOIN museum mu ON mu.Id = v.Museum_id
            UNION
            SELECT s.Funnsted, s.Finner_id, p.Navn AS FinnerNavn, s.Funntidspunkt, s.Antatt_årstall, s.Museum_id, s.Type AS FunnType, mu.Navn AS MuseumNavn
            FROM smykke s\s
            JOIN person p ON s.Finner_id = p.Id
            JOIN museum mu ON mu.Id = s.Museum_id""";
    private static final String GET_ALL_ARTIFACTS_OLDER_THAN = """
            SELECT m.Funnsted, m.Finner_id, p.Navn AS FinnerNavn, m.Funntidspunkt, m.Antatt_årstall, m.Museum_id, m.Type AS FunnType, mu.Navn AS MuseumNavn
            FROM mynt m
            JOIN person p ON m.Finner_id = p.Id
            JOIN museum mu ON mu.Id = m.Museum_id
            WHERE m.Antatt_årstall < ?
            UNION
            SELECT v.Funnsted, v.Finner_id, p.Navn AS FinnerNavn, v.Funntidspunkt, v.Antatt_årstall, v.Museum_id, v.Type AS FunnType, mu.Navn AS MuseumNavn
            FROM vaapen v
            JOIN person p ON v.Finner_id = p.Id
            JOIN museum mu ON mu.Id = v.Museum_id
            WHERE v.Antatt_årstall < ?
            UNION
            SELECT s.Funnsted, s.Finner_id, p.Navn AS FinnerNavn, s.Funntidspunkt, s.Antatt_årstall, s.Museum_id, s.Type AS FunnType, mu.Navn AS MuseumNavn
            FROM smykke s
            JOIN person p ON s.Finner_id = p.Id
            JOIN museum mu ON mu.Id = s.Museum_id
            WHERE s.Antatt_årstall < ?""";

    private MysqlDataSource funnDS;

    public FoundService() {
        funnDS = new MysqlDataSource();
        funnDS.setPassword(FunnProperties.PROPS.getProperty("pwd"));
        funnDS.setUser(FunnProperties.PROPS.getProperty("uname"));
        funnDS.setDatabaseName(FunnProperties.PROPS.getProperty("db_name"));
        funnDS.setPortNumber(Integer.parseInt(FunnProperties.PROPS.getProperty("port")));
        funnDS.setServerName(FunnProperties.PROPS.getProperty("host"));
    }

    public void addPersonFromFile(String fileName) throws FileNotFoundException, SQLException {
        List<Person> persons = parsePersonsFromFile();
        try (Connection conn = funnDS.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_PERSON_SQL)) {
            for (Person person : persons) {
                ps.setInt(1, person.getId());
                ps.setString(2, person.getNavn());
                ps.setString(3, person.getTlf());
                ps.setString(4, person.getE_post());
                ps.executeUpdate();
            }
            System.out.println("Persons added successfully");
        } catch (SQLException e) {
            System.out.println("Feil:" + e.getMessage());
        }
    }

    public List<Person> parsePersonsFromFile() throws FileNotFoundException {
        List<Person> people = new ArrayList<>();
        File file = new File("funn.txt");
        Scanner scanner = new Scanner(file);

        scanner.nextLine(); //skip "Personner:"
        int numberOfPersons = Integer.parseInt(scanner.nextLine().trim());

        for (int i = 0; i < numberOfPersons; i++) {
            int id = Integer.parseInt(scanner.nextLine());
            String navn = scanner.nextLine();
            String tlf = scanner.nextLine();
            String ePost = scanner.nextLine();
            Person p = new Person(id, navn, tlf, ePost);
            people.add(p);
        }
        return people;
    }

    public void addMuseumFromFile(String fileName) throws FileNotFoundException, SQLException {
        List<Museum> museums = parseMuseumFromFile();
        try (Connection conn = funnDS.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_MUSEUM_SQL)) {
            for (Museum museum : museums) {
                ps.setInt(1, museum.getId());
                ps.setString(2, museum.getNavn());
                ps.setString(3, museum.getSted());
                ps.executeUpdate();
            }
            System.out.println("Museum added successfully");
        } catch (SQLException e) {
            System.out.println("Feil:" + e.getMessage());
        }
    }

    private List<Museum> parseMuseumFromFile() throws FileNotFoundException {
        List<Museum> museums = new ArrayList<>();
        File file = new File("funn.txt");
        Scanner scanner = new Scanner(file);

        skipToSection(scanner, "Museer:");

        int numberOfMuseums = Integer.parseInt(scanner.nextLine().trim());
        for (int i = 0; i < numberOfMuseums; i++) {
            int id = Integer.parseInt(scanner.nextLine());
            String navn = scanner.nextLine();
            String sted = scanner.nextLine();
            Museum m = new Museum(id, navn, sted);
            museums.add(m);
        }
        return museums;
    }

    //jeg klarte ikke å håndtere alle unntak fra funn fra fil, jeg måtte legge til fra SQL for å kunne gjære resten av oppgaven.
    //men nedenfor er det jeg gjorde:
    public void addCoinFromFile(String fileName) throws FileNotFoundException, SQLException {
        List<Coins> coinsList = parseCoinFromFile();
        try (Connection conn = funnDS.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_MYNT_SQL)) {
            for (Coins coins : coinsList) {
                ps.setInt(1, coins.getId());
                ps.setString(2, coins.getFunnSted());
                ps.setInt(3, coins.getFinnerId());
                ps.setDate(4, (java.sql.Date) new Date(String.valueOf(coins.getFunntidspunkt())));
                ps.setInt(5, coins.getAntattAarstall());
                ps.setInt(6, coins.getMuseumId());
                ps.setString(7, coins.getType());
                ps.setInt(8, coins.getDiameter());
                ps.setString(9, coins.getMetall());
                ps.executeUpdate();
            }
            System.out.println("Coins added successfully");
        } catch (SQLException e) {
            System.out.println("Feil:" + e.getMessage());
        }
    }

    public void addWeaponFromFile(String fileName) throws FileNotFoundException, SQLException {
        List<Weapon> weaponList = parseWeaponFromFile();
        try (Connection conn = funnDS.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_VAAPEN_SQL)) {
            for (Weapon weapon : weaponList) {
                ps.setInt(1, weapon.getId());
                ps.setString(2, weapon.getFunnSted());
                ps.setInt(3, weapon.getFinnerId());
                ps.setDate(4, (java.sql.Date) new Date(String.valueOf(weapon.getFunntidspunkt())));
                ps.setInt(5, weapon.getAntattAarstall());
                ps.setInt(6, weapon.getMuseumId());
                ps.setString(7, weapon.getType());
                ps.setString(8, weapon.getMateriale());
                ps.setInt(9, weapon.getVekt());
                ps.executeUpdate();
            }
            System.out.println("Weapon added successfully");
        } catch (SQLException e) {
            System.out.println("Feil:" + e.getMessage());
        }
    }

    public void addJewelleryFromFile(String fileName) throws FileNotFoundException, SQLException {
        List<Jewellery> jewelleryList = parseJewelleryFromFile();
        try (Connection conn = funnDS.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SMYKKE_SQL)) {
            for (Jewellery jewellery : jewelleryList) {
                ps.setInt(1, jewellery.getId());
                ps.setString(2, jewellery.getFunnSted());
                ps.setInt(3, jewellery.getFinnerId());
                ps.setString(4, jewellery.getFunntidspunkt());
                ps.setInt(5, jewellery.getAntattAarstall());
                ps.setInt(6, jewellery.getMuseumId());
                ps.setString(7, jewellery.getType());
                ps.setInt(8, jewellery.getVerdiEstimat());
                ps.setString(9, jewellery.getFilNavn());
                ps.executeUpdate();
            }
            System.out.println("Jewellery added successfully");
        } catch (SQLException e) {
            System.out.println("Feil:" + e.getMessage());
        }
    }

    private List<Coins> parseCoinFromFile() throws FileNotFoundException {
        List<Coins> coinsList = new ArrayList<>();
        File file = new File("funn.txt");
        Scanner scanner = new Scanner(file);

        skipToSection(scanner, "Funn:");
        while (scanner.hasNextLine()) {
            int id = Integer.parseInt(scanner.nextLine());
            String funnSted = scanner.nextLine();
            int finnerId = Integer.parseInt(scanner.nextLine());
            String funnTidsPunkt = scanner.nextLine();
            int antattAarstall = Integer.parseInt(scanner.nextLine());
            String museumIdLine = scanner.nextLine();
            int museumId;
            if (museumIdLine.isEmpty()) {
                museumId = 0;
            } else {
                museumId = Integer.parseInt(museumIdLine);
            }
            String type = scanner.nextLine();
            if (Objects.equals(type, "Mynt")) {
                int diameter = Integer.parseInt(scanner.nextLine());
                String metall = scanner.nextLine();
                scanner.nextLine();
                Coins coins = new Coins(id, finnerId, funnSted, funnTidsPunkt, antattAarstall, museumId, type, diameter, metall);
                coinsList.add(coins);
            }
        }
        return coinsList;
    }

    private List<Coins> parseArtifactsFromFile() throws FileNotFoundException {
        List<Coins> coinsList = new ArrayList<>();
        File file = new File("funn.txt");
        Scanner scanner = new Scanner(file);

        skipToSection(scanner, "Funn:");
        while (scanner.hasNextLine()) {
            int id = Integer.parseInt(scanner.nextLine());
            String funnSted = scanner.nextLine();
            int finnerId = Integer.parseInt(scanner.nextLine());
            String funnTidsPunkt = scanner.nextLine();
            int antattAarstall = Integer.parseInt(scanner.nextLine());
            String museumIdLine = scanner.nextLine();
            int museumId;
            if (museumIdLine.isEmpty()) {
                museumId = 0;
            } else {
                museumId = Integer.parseInt(museumIdLine);
            }
            String type = scanner.nextLine();
            if (Objects.equals(type, "Mynt")) {
                int diameter = Integer.parseInt(scanner.nextLine());
                String metall = scanner.nextLine();
                scanner.nextLine();
                Coins coins = new Coins(id, finnerId, funnSted, funnTidsPunkt, antattAarstall, museumId, type, diameter, metall);
                coinsList.add(coins);
            }
        }
        return coinsList;
    }

    private List<Weapon> parseWeaponFromFile() throws FileNotFoundException {
        List<Weapon> weaponList = new ArrayList<>();
        File file = new File("funn.txt");
        Scanner scanner = new Scanner(file);

        skipToSection(scanner, "Funn:");

        while (scanner.hasNextLine()) {
            int id = Integer.parseInt(scanner.nextLine());
            String funnSted = scanner.nextLine();
            int finnerId = Integer.parseInt(scanner.nextLine());
            String funnTidsPunkt = scanner.nextLine();
            int antattAarstall = Integer.parseInt(scanner.nextLine());
            String museumIdLine = scanner.nextLine();
            int museumId;
            if (museumIdLine.isEmpty()) {
                museumId = 0;
            } else {
                museumId = Integer.parseInt(museumIdLine);
            }
            String type = scanner.nextLine();
            if (type == "Vaapen") {
                String vaapenType = scanner.nextLine();
                String materialle = scanner.nextLine();
                int vekt = Integer.parseInt(scanner.nextLine());
                scanner.nextLine();
                Weapon weapon = new Weapon(id, finnerId, funnSted, funnTidsPunkt, antattAarstall, museumId, type, vaapenType, materialle, vekt);
                weaponList.add(weapon);
            }
        }
        return weaponList;
    }

    private List<Jewellery> parseJewelleryFromFile() throws FileNotFoundException {
        List<Jewellery> jewelleryList = new ArrayList<>();
        File file = new File("funn.txt");
        Scanner scanner = new Scanner(file);

        skipToSection(scanner, "Funn:");

        while (scanner.hasNextLine()) {
            int id = Integer.parseInt(scanner.nextLine());
            String funnSted = scanner.nextLine();
            int finnerId = Integer.parseInt(scanner.nextLine());
            String funnTidsPunkt = scanner.nextLine();
            int antattAarstall = Integer.parseInt(scanner.nextLine());
            String museumIdLine = scanner.nextLine();
            int museumId;
            if (museumIdLine.isEmpty()) {
                museumId = 0;
            } else {
                museumId = Integer.parseInt(museumIdLine);
            }
            String type = scanner.nextLine();

            if (type == "Smykke") {
                int verdiEstimat = Integer.parseInt(scanner.nextLine());
                String filNavn = scanner.nextLine();
                scanner.nextLine();
                Jewellery jewellery = new Jewellery(id, finnerId, funnSted, funnTidsPunkt, antattAarstall, museumId, type, filNavn, verdiEstimat);
                jewelleryList.add(jewellery);
            }
        }
        return jewelleryList;
    }


    private void skipToSection(Scanner scanner, String sectionName) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals(sectionName)) {
                break;
            }
        }
    }

    public List<Coins> getCoins() throws SQLException {
        List<Coins> coinsList = new ArrayList<>();
        try (Connection conn = funnDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_COIN_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("Id");
                String funnSted = rs.getString("Funnsted");
                int finnerId = rs.getInt("Finner_id");
                String funntidspunkt = rs.getString("Funntidspunkt");
                int antattÅrstall = rs.getInt("Antatt_årstall");
                int museumId = rs.getInt("Museum_id");
                String type = rs.getString("Type");
                int diameter = rs.getInt("Diameter");
                String metall = rs.getString("Metall");
                coinsList.add(new Coins(id, finnerId, funnSted, funntidspunkt, antattÅrstall, museumId, type, diameter, metall));
            }
        }
        return coinsList;
    }

    public List<Weapon> getWeapon() throws SQLException {
        List<Weapon> weaponList = new ArrayList<>();
        try (Connection conn = funnDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_WEAPON_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("Id");
                int finnerId = rs.getInt("Finner_id");
                String funnSted = rs.getString("Funnsted");
                String funntidspunkt = rs.getString("Funntidspunkt");
                int antattÅrstall = rs.getInt("Antatt_årstall");
                int museumId = rs.getInt("Museum_id");
                String type = rs.getString("Type");
                String vaapenType = rs.getString("VaapenType");
                String materiale = rs.getString("Materiale");
                int vekt = rs.getInt("Vekt");
                weaponList.add(new Weapon(id, finnerId, funnSted, funntidspunkt, antattÅrstall, museumId, type, vaapenType, materiale, vekt));
            }
        }
        return weaponList;
    }

    public List<Jewellery> getJewellery() throws SQLException {
        List<Jewellery> jewelleryList = new ArrayList<>();
        try (Connection conn = funnDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_JEWELLERY_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("Id");
                String funnSted = rs.getString("Funnsted");
                int finnerId = rs.getInt("Finner_id");
                String funntidspunkt = rs.getString("Funntidspunkt");
                int antattÅrstall = rs.getInt("Antatt_årstall");
                int museumId = rs.getInt("Museum_id");
                String type = rs.getString("Type");
                int verdiEstimat = rs.getInt("Verdiestimat");
                String filNavn = rs.getString("filNavn");
                jewelleryList.add(new Jewellery(id, finnerId, funnSted, funntidspunkt, antattÅrstall, museumId, type, filNavn, verdiEstimat));
            }
        }
        return jewelleryList;
    }

    public List<AllArtifacts> getArtifacts() throws SQLException {
        List<AllArtifacts> allArtifacts = new ArrayList<>();

        try (Connection conn = funnDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_ARTIFACTS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String funnsted = rs.getString("Funnsted");
                int finnerId = rs.getInt("Finner_id");
                String navn = rs.getString("FinnerNavn");
                String funntidspunkt = rs.getString("Funntidspunkt");
                int antattÅrstall = rs.getInt("Antatt_årstall");
                int museumId = rs.getInt("Museum_id");
                String type = rs.getString("FunnType");
                String museumNavn = rs.getString("MuseumNavn");

                allArtifacts.add(new AllArtifacts(finnerId, new Person(finnerId, navn), funnsted, funntidspunkt, antattÅrstall, new Museum(museumId, museumNavn), type));
            }
        }
        return allArtifacts;
    }


    public Optional<List<AllArtifacts>> getArtifactsOlderThan(int year) throws SQLException {
        List<AllArtifacts> allArtifacts = new ArrayList<>();

        try (Connection conn = funnDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_ARTIFACTS_OLDER_THAN)) {

            stmt.setInt(1, year);
            stmt.setInt(2, year);
            stmt.setInt(3, year);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String funnsted = rs.getString("Funnsted");
                    int finnerId = rs.getInt("Finner_id");
                    String navn = rs.getString("FinnerNavn");
                    String funntidspunkt = rs.getString("Funntidspunkt");
                    int antattÅrstall = rs.getInt("Antatt_årstall");
                    int museumId = rs.getInt("Museum_id");
                    String type = rs.getString("FunnType");
                    String museumNavn = rs.getString("MuseumNavn");

                    allArtifacts.add(new AllArtifacts(finnerId, new Person(finnerId, navn), funnsted, funntidspunkt, antattÅrstall, new Museum(museumId, museumNavn), type));
                }
            }
        }
        if (allArtifacts.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(allArtifacts);
        }
    }
}
