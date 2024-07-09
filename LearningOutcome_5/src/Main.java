public class Main {
    public static void main(String[] args) {
        //best case: the key might be present at first index, so O(1).
        //worst case: key present at last index, or not found, so O(N).
        //average case: O(N)
        //not suitable for large arrays
        LinearSearch ls = new LinearSearch();
        System.out.println("Linear search:");
        ls.findElement();

        //best case: O(1)
        //average case: O(log N)
        //worst case: O(log N)
        //faster than linar search, specially for large arrays
        //most be a sorted array
        System.out.println("Binary search:");
        BinarySearch bs = new BinarySearch();
        bs.findElement();
    }
}

class LinearSearch {
    int[] arr = {2, 3, 4, 5, 6};
    int x = 10;
    public void findElement() {
        int result = search(arr, arr.length, x);
        if (result == -1) {
            System.out.println("Element is no present in the array.");
        } else {
            System.out.println("Element is present at index " + result);
        }
    }
    public static int search(int arr[], int N, int x){
        for (int i = 0; i < N; i++){
            if (arr[i] == x){
                return i;
            }
        }
        return -1;
    }
}

class BinarySearch{
    void findElement(){
        int[] arr = {2, 3, 4, 10, 40};
        int n = arr.length;
        int x = 10;
        int result = binarySearch(arr, x);
        if (result == -1){
            System.out.println("Element is not present in array.");
        } else {
            System.out.println("Element is present at index " + result);
        }
    }
    int binarySearch(int arr[], int x){
        int l = 0, r = arr.length - 1;
        while (l <= r){
            int m = l + (r - 1) / 2;
            if (arr[m] == x){
                return  m;
            } if (arr[m] < x){
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }
}