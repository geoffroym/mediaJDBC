import java.util.ArrayList;

public class QuickSortArray {

    //Quicksort 2C

    ///XXXXXXXXXXXXXXXXXx
    //XXXXXXXXXXXXXXX

    private int counter= 0;
    // The function for swapping elements
    private void swap(ArrayList<CityRecord> arr, int i, int j) {
        CityRecord temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }
    /*
      Function for placing the pivot element at its correct position in the sorted
      array, the smaller elements to the left and the greater elements to the right
    */
    private int partition(ArrayList<CityRecord> arr, int low, int high) {
        // Using the last element as the pivot
        double pivot = arr.get(high).lat();
        // Index of smaller element and indicates the right position of pivot found so far
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {
            // Increments the index if the current element is smaller than the pivot
            if (arr.get(j).lat() < pivot) {
                i++;
                swap(arr, i, j);
            }
            counter++;
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    //Quicksort 2A
    public void quickSort(ArrayList<CityRecord> arr, int low, int high) {

        if (low < high) {
            // The partitioning index is now at its right place
            int partition = partition(arr, low, high);
            // Sorting elements before and after partition
            quickSort(arr, low, partition - 1);
            quickSort(arr, partition + 1, high);
        }
    }

    //Quicksort 2C
    private int partitionHaversine(ArrayList<CityRecord> arr, int low, int high) {
        double pivot = Haversine.calculateDistance(arr.get(high).lat(), arr.get(high).lng());
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {
            if (Haversine.calculateDistance(arr.get(j).lat(), arr.get(j).lng()) < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    public void quickSortHaversine(ArrayList<CityRecord> arr, int low, int high) {

        if (low < high) {
            int partition = partitionHaversine(arr, low, high);
            quickSortHaversine(arr, low, partition - 1);
            quickSortHaversine(arr, partition + 1, high);
        }
    }

    public int comparisonCount() {
        return counter;
    }
}
