import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Reader reader = new Reader();       //Reader
        MergeSortArray msa = new MergeSortArray();
        QuickSortArray qsa = new QuickSortArray();

        //LL
        //LinkedList ll = new LinkedList();       //LinkedList
        //reader.citiesReaderLinkedList("worldcities.csv", ll);   //LinkedList Reader
        //ll.mergeSort(ll.head);


        LinkedList ll = new LinkedList().read("worldcities.csv");
        ll.head = LinkedList.mergeSort(ll.head);
        //Array
        ArrayList<CityRecord> cities = reader.citiesReaderArray("worldcities.csv"); // Make array and Array reader
        msa.mergeSortArray(cities);

        ArrayList<CityRecord> cities1 = reader.citiesReaderArray("worldcities.csv");
        ArrayList<CityRecord> cities2 = reader.citiesReaderArray("worldcities.csv");
        qsa.quickSortHaversine(cities1, 0, (cities1.size() - 1));
        qsa.quickSort(cities2, 0, (cities2.size() - 1));
        System.out.println(qsa.comparisonCount());
        System.out.println(msa.countMerges(cities));

        ArrayList<CityRecord> cities3 = reader.citiesReaderArray("worldcities.csv");

    }
}
