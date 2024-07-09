import java.util.ArrayList;

public class MergeSort {
    void timeComplexity(ArrayList<Cities> cities){
        /* The number of merges required to merge sort an array remains the same regardless
        of whether the data is randomly sorted beforehand, because merge sort's time complexity is always (n log(n)),
        where 'n' is the number of elements in the array.*/
        double result = Math.log(cities.size()) / Math.log(2);
        System.out.println("Amount of merges: " + result);
    }
    void mergeSort(ArrayList<Cities> cities) {
        int size = cities.size();
        int middle = size / 2;

        if (size <= 1) return;

        ArrayList<Cities> left = new ArrayList<>();
        ArrayList<Cities> right = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (i < middle) {
                left.add(cities.get(i));
            } else {
                right.add(cities.get(i));
            }
        }

        mergeSort(left);
        mergeSort(right);
        merge(left, right, cities);
    }

    private void merge(ArrayList<Cities> left, ArrayList<Cities> right, ArrayList<Cities> cities) {
        int leftSize = cities.size() / 2;
        int rightSize = cities.size() - leftSize;

        int i = 0, l = 0, r = 0;

        while (l < leftSize && r < rightSize) {
            if (left.get(l).lat() < right.get(r).lat()) {
                cities.set(i, left.get(l));
                i++;
                l++;
            } else {
                cities.set(i, right.get(r));
                i++;
                r++;
            }
        }

        while (l < leftSize) {
            cities.set(i, left.get(l));
            i++;
            l++;
        }
        while (r < rightSize) {
            cities.set(i, right.get(r));
            i++;
            r++;
        }
    }
}
