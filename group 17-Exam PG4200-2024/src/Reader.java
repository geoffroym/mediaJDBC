import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
public class Reader {


    public ArrayList<CityRecord> citiesReaderArray(String file) {
        ArrayList<CityRecord> cities = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                String city = nextLine[0];
                String city_ascii = nextLine[1];
                double lat = Double.parseDouble(nextLine[2]);
                double lng = Double.parseDouble(nextLine[3]);
                double haversineDistance = Haversine.calculateDistance(lat,lng);

                String country = nextLine[4];
                String iso2 = nextLine[5];
                String iso3 = nextLine[6];
                String adm_name = nextLine[7];
                String capital = nextLine[8];
                String pop = nextLine[9];
                double population;
                if (pop == "") {
                    population = 0;
                } else {
                    population = Double.parseDouble(pop);
                }
                String id = nextLine[10];
                int idint = Integer.parseInt(id);


                cities.add(new CityRecord(city, city_ascii, lat, lng, country,iso2, iso3, adm_name, capital, population, idint, haversineDistance));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }

}
