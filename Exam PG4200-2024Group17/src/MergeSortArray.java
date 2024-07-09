import java.util.ArrayList;

public class MergeSortArray {

        //MERGESORT
    public void mergeSortArrayHav(ArrayList<CityRecord> cities2){
        int size = cities2.size();
        int middle = size / 2;
        if (size <= 1) return;

        ArrayList<CityRecord> left = new ArrayList<>();
        ArrayList<CityRecord> right = new ArrayList<>();

        // splits the input ArrayList into two sub-arrays
        for (int i = 0; i < size; i++){
            if (i < middle){
                left.add(cities2.get(i));
            } else {
                right.add(cities2.get(i));
            }
        }
        mergeSortArrayHav(left);
        mergeSortArrayHav(right);
        mergeArrayHav(left, right, cities2);
    }


    private void mergeArrayHav(ArrayList<CityRecord> left, ArrayList<CityRecord> right, ArrayList<CityRecord> cities) {
        int leftSize = cities.size() / 2;
        int rightSize = cities.size() - leftSize;

        // indices for main array, left sub-array and right sub-array respectively
        int i = 0, l = 0, r = 0;

        // while left and right sides aren't sorted
        while (l < leftSize && r < rightSize){
            // if latitude of element in left array is smaller than latitude of element in right array,
            // add element from left array to sorted array
            // else, do the opposite
            if (left.get(l).haversineDistance() < right.get(r).haversineDistance()){
                cities.set(i, left.get(l));
                i++;
                l++;
            } else {
                cities.set(i, right.get(r));
                i++;
                r++;
            }
        }


        // add remaining elements from the left sub-array if any
        while (l < leftSize){
            cities.set(i, left.get(l));
            i++;
            l++;
        }
        // add remaining elements from the right sub-array if any
        while ((r < rightSize)){
            cities.set(i, right.get(r));
            i++;
            r++;
        }
    }
    public void mergeSortArrayLat(ArrayList<CityRecord> cities2){
        int size = cities2.size();
        int middle = size / 2;
        if (size <= 1) return;

        ArrayList<CityRecord> left = new ArrayList<>();
        ArrayList<CityRecord> right = new ArrayList<>();

        // splits the input ArrayList into two sub-arrays
        for (int i = 0; i < size; i++){
            if (i < middle){
                left.add(cities2.get(i));
            } else {
                right.add(cities2.get(i));
            }
        }
        mergeSortArrayLat(left);
        mergeSortArrayLat(right);
        mergeArrayLat(left, right, cities2);
    }
    private void mergeArrayLat(ArrayList<CityRecord> left, ArrayList<CityRecord> right, ArrayList<CityRecord> cities) {

        int leftSize = cities.size() / 2;
        int rightSize = cities.size() - leftSize;

        // indices for main array, left sub-array and right sub-array respectively
        int i = 0, l = 0, r = 0;

        // while left and right sides aren't sorted
        while (l < leftSize && r < rightSize) {
            // if latitude of element in left array is smaller than latitude of element in right array,
            // add element from left array to sorted array
            // else, do the opposite
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

        // add remaining elements from the left sub-array if any
        while (l < leftSize){
            cities.set(i, left.get(l));
            i++;
            l++;
        }
        // add remaining elements from the right sub-array if any
        while (r < rightSize){
            cities.set(i, right.get(r));
            i++;
            r++;
        }
    }
    public double countMerges(ArrayList<CityRecord> arr) {
        return Math.log(arr.size())/Math.log(2);
    }

}
