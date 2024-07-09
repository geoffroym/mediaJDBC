public class Main {
    public static void main(String[] args) {
        int[] array = {9, 1, 3, 6, 7, 2, 4, 5, 8, 22, 10, 12, 11};
        quickSort(array, 0, array.length - 1);
        int numberOfComparisons = 0;

        for (int i : array){
            System.out.print(i + " ");
        }
    }

    public static void quickSort(int[] array, int start, int end) {
        if (end <= start){
            return;
        } else {
            int pivot = partition(array, start, end);
            numberOfComparisons += end - start;
            quickSort(array, start, pivot -1);
            quickSort(array, pivot +1, end);
        }

    }

    private static int partition(int[] array, int start, int end) {
        int pivot = array[end];
        int i = start - 1;
        for (int j = start; j <= end; j++){
            if (pivot >= array[j]){
                i++;
                int temp = array[j]; //i
                array[j] = array[i]; // i  j
                array[i] = temp;     // j
            }
        }
        /*i++;
        int temp = array[i];
        array[i] = array[end];
        array[end] = temp;*/
        return i;
    }
}