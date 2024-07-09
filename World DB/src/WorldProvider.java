import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorldProvider {
    private static final String INSERT_CITY_NO_ID_SQL = "INSERT INTO City (Name, CountryCode, District, Population) VALUES (?, ?, ?, ?)";
    private static final String GET_CITY = "SELECT id, name, countryCode, district, population FROM City WHERE id=?";
    private static final String UPDATE_CITY_SQL = "UPDATE City SET Name=?, District=?, CountryCode=?, Population=?";
    private static final String DELETE_CITY_SQL = "DELETE FROM City WHERE id=?";

    private static final String ADD_COUNTRY_LANGUAGE_SQL = "INSERT INTO CountryLanguage VALUES (?, ?, ?, ?)";
    private static final String ADD_COUNTRY_SQL = "INSERT INTO Country VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_COUNTRY_SQL = "DELETE FROM Country WHERE code=?";
    private static final String DELETE_ALL_LANGUAGES_IN_COUNTRY_SQL = "DELETE FROM CountryLanguage WHERE CountryCode=?";
    private static final String UPDATE_CITY_PLACEMENT_SQL = "UPDATE City SET CountryCode=? WHERE id=?";
    private final MysqlDataSource worldDS;

    public WorldProvider() {
        worldDS = new MysqlDataSource();
        worldDS.setServerName(PropertiesProvider.PROPS.getProperty("host"));
        worldDS.setPortNumber(Integer.parseInt(PropertiesProvider.PROPS.getProperty("port")));
        worldDS.setDatabaseName(PropertiesProvider.PROPS.getProperty("db_name"));
        worldDS.setUser(PropertiesProvider.PROPS.getProperty("uname"));
        worldDS.setPassword(PropertiesProvider.PROPS.getProperty("pwd"));
    }

    public MysqlDataSource getWorldDS() {
        return worldDS;
    }

    public List<Country> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        try (Connection con = worldDS.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Country");
            while (resultSet.next()) {
                String code = resultSet.getString("Code");
                String name = resultSet.getString("Name");
                String continent = resultSet.getString("Continent");
                String region = resultSet.getString("Region");
                double surfaceArea = resultSet.getDouble("SurfaceArea");
                int indepYear = resultSet.getInt("indepYear");
                int population = resultSet.getInt("population");
                double lifeExpectancy = resultSet.getDouble("lifeExpectancy");
                double gnp = resultSet.getDouble("gnp");
                double gnpOld = resultSet.getDouble("gnpOld");
                String localName = resultSet.getString("localName");
                String governmentForm = resultSet.getString("governmentForm");
                String headOfState = resultSet.getString("headOfState");
                int capital = resultSet.getInt("capital");
                String code2 = resultSet.getString("code2");
                countries.add(new Country(code, name, continent, region, surfaceArea, indepYear,
                        population, lifeExpectancy, gnp, gnpOld, localName, governmentForm,
                        headOfState, capital, code2));
            }
        } catch (
                SQLException e) {
            System.out.println("Unable to connect to database:" + e.getMessage());
            e.printStackTrace();
        }
        return countries;
    }

    public List<City> getAllCities() {
        List<City> cities = new ArrayList<>();

        try (Connection con = worldDS
                .getConnection()) {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM City");
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                String countryCode = resultSet.getString("countryCode");
                String district = resultSet.getString("district");
                int population = resultSet.getInt("population");
                cities.add(new City(ID, name, countryCode, district, population));
            }
        } catch (
                SQLException e) {
            System.out.println("Unable to connect to database:" + e.getMessage());
            e.printStackTrace();
        }

        return cities;
    }

    public List<City> getAllCitiesByCountryCode(String countryCode) {
        List<City> cities = new ArrayList<>();
        try (Connection conn = worldDS
                .getConnection()) {
            String sql = "SELECT * FROM City WHERE countryCode =?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, countryCode);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                String district = resultSet.getString("district");
                int population = resultSet.getInt("population");
                cities.add(new City(ID, name, countryCode, district, population));
            }
        } catch (
                SQLException e) {
            System.out.println("Unable to connect to database:" + e.getMessage());
            e.printStackTrace();
        }
        return cities;
    }

    public int addNewCity(String name, String countryCode, String district, int population) throws SQLException {
        try (Connection conn = worldDS
                .getConnection()) {
            PreparedStatement ps = conn.prepareStatement(INSERT_CITY_NO_ID_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, countryCode);
            ps.setString(3, district);
            ps.setInt(4, population);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
            return 0;
        }
    }

    public int updateNewCity(City newCity) throws SQLException {
        try (Connection conn = worldDS
                .getConnection()) {
            PreparedStatement ps = conn.prepareStatement(UPDATE_CITY_SQL);
            ps.setInt(5, newCity.ID());
            ps.setString(1, newCity.name());
            ps.setString(2, newCity.district());
            ps.setString(3, newCity.countryCode());
            ps.setInt(4, newCity.population());
            return ps.executeUpdate();
        }
    }

    public int deleteCity(int id) throws SQLException {
        try (Connection conn = worldDS
                .getConnection()) {
            PreparedStatement ps = conn.prepareStatement(DELETE_CITY_SQL);
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    public Optional<City> getCity(int id) throws SQLException {
        try (Connection con = worldDS.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_CITY);
        ) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String countryCode = resultSet.getString("countryCode");
                    String district = resultSet.getString("district");
                    int population = resultSet.getInt("population");
                    return Optional.of(new City(id, name, countryCode, district, population));
                }
            }
            return Optional.empty();
        }
    }

    public int addCountry(Country country, Connection con) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(ADD_COUNTRY_SQL)) {
            ps.setString(1, country.code());
            ps.setString(2, country.name());
            ps.setString(3, country.continent());
            ps.setString(4, country.region());
            ps.setDouble(5, country.surfaceArea());
            ps.setInt(6, country.indepYear());
            ps.setInt(7, country.population());
            ps.setDouble(8, country.lifeExpectancy());
            ps.setDouble(9, country.gnp());
            ps.setDouble(10, country.gnpOld());
            ps.setString(11, country.localName());
            ps.setString(12, country.governmentForm());
            ps.setString(13, country.headOfState());
            ps.setInt(14, country.capital());
            ps.setString(15, country.code2());
            return ps.executeUpdate();
        }
    }

    public int addCountryLanguage(CountryLanguage newCountryLanguage, Connection con) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(ADD_COUNTRY_LANGUAGE_SQL)) {
            ps.setString(1, newCountryLanguage.countryCode());
            ps.setString(2, newCountryLanguage.language());
            String booleanValue = (newCountryLanguage.isOfficial()) ? "T" : "F";
            ps.setString(3, booleanValue);
            ps.setFloat(4, newCountryLanguage.percentage());
            return ps.executeUpdate();
        }
    }

    public int moveCity(int cityId, String code, Connection con) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_CITY_PLACEMENT_SQL)) {
            ps.setString(1, code);
            ps.setInt(2, cityId);
            return ps.executeUpdate();
        }
    }

    public int deleteAllCountryLanguagesInCountry(String code, Connection con) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(DELETE_ALL_LANGUAGES_IN_COUNTRY_SQL)){
            ps.setString(1, code);
            return ps.executeUpdate();
        }
    }

    public int deleteCountry(String code, Connection con) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(DELETE_COUNTRY_SQL)){
            ps.setString(1, code);
            return ps.executeUpdate();
        }
    }
}


