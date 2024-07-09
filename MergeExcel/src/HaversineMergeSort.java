import java.util.ArrayList;

public class HaversineMergeSort {
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
            if (left.get(l).haversineDistance() < right.get(r).haversineDistance()) {
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
        while ((r < rightSize)) {
            cities.set(i, right.get(r));
            i++;
            r++;
        }
    }
}
