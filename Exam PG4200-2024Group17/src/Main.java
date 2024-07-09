import java.util.ArrayList;

// Using Maven:com.opencsv:opencsv:5.9
public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader();       //Reader
        MergeSortArray msa = new MergeSortArray();
        QuickSortArray qsa = new QuickSortArray();
        LinkedList ll = new LinkedList().read("worldcities.csv");
        ll.head = ll.mergeSortLat(ll.head);

        ArrayList<CityRecord> cities = reader.citiesReaderArray("worldcities.csv"); // Make array and Array reader
        msa.mergeSortArrayLat(cities);

        /*System.out.println("1A. First 20 elements after merge sorting linked lists based on latitude: ");
        ll.temp = ll.head;
        for ( int i = 0; i < 20; i++) {
            System.out.println(ll.temp.city.lat());
            ll.temp = ll.temp.next;
        }*/
        System.out.println("1A. First 20 elements after merge sorting array based on latitude: ");
        for (int i = 0; i < 20; i++) {
            System.out.println(cities.get(i).lat());
        }/*

        System.out.println("1B. Amount of merges in merge sort: " + msa.countMerges(cities));
        System.out.println("1B. Amount of merges in linked list: " + ll.mergeCount(ll.head));

        msa.mergeSortArrayHav(cities);
        System.out.println("1C. First 20 elements after merge sorting array based on haversine: ");
        for (int i = 0; i < 20; i++) {
            System.out.println(cities.get(i).lat() + " " + cities.get(i).lng());
        }

        ll.head = ll.mergeSort(ll.head);

        System.out.println("1C. First 20 elements after merge sorting linked lists based on haversine: ");
        ll.temp = ll.head;
        for ( int i = 0; i < 20; i++) {
            System.out.println(ll.temp.city.lat() + " " + ll.temp.city.lng());
            ll.temp = ll.temp.next;
        }

        ArrayList<CityRecord> cities1 = reader.citiesReaderArray("worldcities.csv");
        ArrayList<CityRecord> cities2 = reader.citiesReaderArray("worldcities.csv");
        qsa.quickSortHaversine(cities1, 0, (cities1.size() - 1));
        qsa.quickSort(cities2, 0, (cities2.size() - 1));

        System.out.println("2A. First 20 elements after quick sorting based on latitude: ");
        for (int i = 0; i < 20; i++) {
            System.out.println(cities2.get(i).lat());
        }

        System.out.println("2B. Comparison count of quick sort: " + qsa.comparisonCount());

        System.out.println("2C. First 20 elements after quick sorting based on haversine: ");
        for (int i = 0; i < 20; i++) {
            System.out.println(cities1.get(i).lat() + " " + cities1.get(i).lng());
        }*/
    }
}
