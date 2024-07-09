import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class LinkedList {


    cityNode head = null;

    cityNode temp = null;
    static class cityNode { // private X static
        CityRecord city;
        cityNode next;
        private cityNode(CityRecord city) {
            this.city = city;
        }
    }

    public static cityNode mergeSort(cityNode start) { //static
        if (start == null || start.next == null) {
            return start;
        }
        cityNode middle = getMiddle(start);
        cityNode nextOfMiddle = middle.next;
        middle.next = null;

        cityNode left = mergeSort(start); // Sort the left half
        cityNode right = mergeSort(nextOfMiddle); // Sort the right half
        cityNode sortedList = merge(left, right);
        return sortedList;
    }
    public static cityNode merge(cityNode left, cityNode right) { // static
        cityNode pointer = new cityNode(null);
        cityNode tail = pointer;
        while (left != null && right != null) {

            if (left.city.haversineDistance() < right.city.haversineDistance()) {
                tail.next = left;
                left = left.next;
            } else {
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;
        }
        tail.next = (left != null) ? left : right;
        return pointer.next;
    }

    public static cityNode mergeSortLat(cityNode start) { //static
        if (start == null || start.next == null) {
            return start;
        }
        cityNode middle = getMiddle(start);
        cityNode nextOfMiddle = middle.next;
        middle.next = null;

        cityNode left = mergeSortLat(start); // Sort the left half
        cityNode right = mergeSortLat(nextOfMiddle); // Sort the right half
        cityNode sortedList = mergeLat(left, right);
        return sortedList;
    }

    public static cityNode mergeLat(cityNode left, cityNode right) { // static
        cityNode pointer = new cityNode(null);
        cityNode tail = pointer;
        while (left != null && right != null) {

            if (left.city.lat() < right.city.lat()) {
                tail.next = left;
                left = left.next;
            } else {
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;
        }
        tail.next = (left != null) ? left : right;
        return pointer.next;
    }
    public static cityNode getMiddle(cityNode start) { //static
        if (start == null) {
            return start;
        }
        cityNode slow = start;
        cityNode fast = start;
        while ((fast.next != null) && (fast.next.next != null)) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


    void push(CityRecord city) {
        cityNode newNode = new cityNode(city);
        newNode.next = head;
        head = newNode;
    }

    public LinkedList read(String file) {
        LinkedList liste = new LinkedList();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] nextLine;
            reader.readNext(); // Skipping header
            while ((nextLine = reader.readNext()) != null ){
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
                double population = 0.0; // Default value for population
                if (!nextLine[9].isEmpty()) {
                    population = Double.parseDouble(nextLine[9]);
                }
                int id = Integer.parseInt(nextLine[10]); // Assuming ID is always an integer
                liste.push(new CityRecord(city, city_ascii, lat, lng, country, iso2, iso3, adm_name, capital, population, id, haversineDistance));
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        } return liste;
    }


    // metode for å kunne bruke listen i en for-loop
    cityNode getNode(int x) {
        cityNode b = head;
        int count = 0;
        while (b != null && count < x) {
            b = b.next;
            count++;
        }
        return b;
    }

}
