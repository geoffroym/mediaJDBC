import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReader {
    public ArrayList<Cities> citiesReader(String file) {
        ArrayList<Cities> cities = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                String city = nextLine[0];
                String city_ascii = nextLine[1];
                double lat = Double.parseDouble(nextLine[2]);
                double lng = Double.parseDouble(nextLine[3]);
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
                double haversineDistance = calculateDistance(lat, lng);
                cities.add(new Cities(city, city_ascii, lat, lng, country, iso2, iso3, adm_name, capital, population, Integer.parseInt(id), haversineDistance));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }

    double haversine (double val){
        return Math.pow(Math.sin(val / 2), 2);
    }

    double calculateDistance (double endLat, double endLng){
        int earth_radius = 6371;
        double startLat = 0;
        double startLng = 0;
        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLng - startLng));

        startLat = Math.toRadians(0);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earth_radius * c;
    }
}
