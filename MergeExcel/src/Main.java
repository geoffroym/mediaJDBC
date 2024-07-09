import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CsvReader reader = new CsvReader();
        ArrayList<Cities> cities = reader.citiesReader("worldcities.csv");
        MergeSort ms = new MergeSort();
        HaversineMergeSort hms = new HaversineMergeSort();
        //HaversineQuickSort hqs = new HaversineQuickSort();
        ms.mergeSort(cities);
        for (Cities city : cities) {
            System.out.println(city);
        }
    }
}