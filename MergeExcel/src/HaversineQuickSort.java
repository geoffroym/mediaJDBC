import java.util.ArrayList;

public class HaversineQuickSort {

    public void quickSort(ArrayList<Cities> cities, int start, int end) {
        //if there is only one element, stop
        if (end <= start) return;

        //method to choose the pivot, in this case, the last one
        int pivot = partition(cities, start, end);

        //recursively sort the arrays on the left to the pivot and right to pivot
        quickSort(cities, start, pivot - 1);
        quickSort(cities, pivot + 1, end);
    }

    private int partition(ArrayList<Cities> cities, int start, int end) {
        //declare pivot as last element
        double pivot = cities.get(end).haversineDistance();
        //create
        int i = start - 1;

        //if element we're comparing to pivot is bigger, do nothing with i
        //but if element is smaller or equal to pivot, then increase i
        //put 'i' element where temp is, put element that was on j on i, and the put the element that was on temp back to 'j'
        for (int j = start; j <= end; j++) {
            if (cities.get(j).haversineDistance() <= pivot) {
                i++;
                Cities temp = cities.get(j);
                cities.set(j, cities.get(i));
                cities.set(i, temp);
            }
        }
        return i;
    }
}
