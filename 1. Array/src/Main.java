import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Array a = new Array();
        a.userInteraction();

    }
}

class Array{
    int[] arr = {10, 7, 11, 5, 13, 8, 38, 37, 14, 92, 84, 74, 77, 20, 14, 40, 47, 33, 65, 62, 69, 73, 62};
    int n = arr.length;
    int[] newArr;

    void userInteraction(){
        int choice = 1;
        System.out.println("Hi, bellow are your options:");
        System.out.println("1. See original array:");
        System.out.println("2. See most frequent element in the array.");
        System.out.println("3. Get maximum element.");
        System.out.println("4. Get minimum element.");
        System.out.println("5. Add an element to the array:");
        System.out.println("6. Remove an element from the array:");
        System.out.println("7. Search en element.");
        System.out.println("8. Quit.");
        Scanner scanner = new Scanner(System.in);
        while (choice != 8){
            choice = scanner.nextInt();
            switch (choice){
                    case 1 -> display();
                    case 2 -> mostFrequent();
                    case 3 -> getMax();
                    case 4 -> getMin();
                    case 5 -> add();
                    case 6 -> remove();
                    case 7 -> search();
                    case 8 -> quit();
            }
        }
    }

    private void quit() {
        System.out.println("Bye.");
    }


    void display(){
        System.out.println("Original array:");
        System.out.println(Arrays.toString(arr));
        for (int i = 0; i < n; i++) {
            System.out.print("arr[" + i + "] = " + arr[i] + ", ");
        }
    }

    void add(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter index you'd like to add to:");
        int index = scanner.nextInt();
        System.out.println("Enter element:");
        int element = scanner.nextInt();
        newArr = new int[n + 1];
        for (int i = 0; i < index; i ++){
            newArr[i] = arr[i];
        }
        newArr[index] = element;

        for (int i = index + 1; i < n + 1; i++){
            newArr[i] = arr[i - 1];
        }
        System.out.println("Original array after addition:");
        System.out.println(Arrays.toString(newArr));
    }

    void remove(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter index you'd like to remove from array:");
        int index = scanner.nextInt();
        newArr = new int[n - 1];
        for (int i = 0; i < index; i++){
            newArr[i] = arr[i];
        }
        for (int i = index + 1; i < n; i++){
            newArr[i - 1] = arr[i];
        }
        System.out.println("Original array after deletion:");
        System.out.println(Arrays.toString(newArr));

        /*void remove(int index){
        for (int i = index; i < n-1; i++){
            arr[i] = arr[i + 1];
        }
        n = n - 1;*/
    }

    void search(){
        System.out.println("Enter element you'd like to search:");
        Scanner scanner = new Scanner(System.in);
        int userInput = scanner.nextInt();
        int index = searchE(userInput);

        if (index == - 1){
            System.out.println("Element not found.");
        } else {
            System.out.println("Element found at index " + index);
        }
    }

    private int searchE(int userInput) {
        for (int i = 0; i < n; i++){
            if (userInput == arr[i]){
                return i;
            }
        }
        return -1;
    }

    void getMin() {
        int res = arr[0];
        for (int i = 1; i < n; i++) {
            res = Math.min(res, arr[i]);
        }
        System.out.println(res);
    }

    void getMax() {
        int res = arr[0];
        for (int i = 1; i < n; i++) {
            res = Math.max(res, arr[i]);
        }
        System.out.println(res);
    }
    void mostFrequent() {
            //the outer loop picks all elements one by one
            //the inner loop finds the frequency of the picked element
            //and compares it with the maximum so far
        int maxcount = 0;
        int elementWithMaxFreq = 0;
        for (int i = 0; i < n; i++) {
        int count = 0;
            for (int j = 0; j < n; j++) {
                if (arr[i] == arr[j]) {
                    count++;
                }
            }
            if (count > maxcount) {
                maxcount = count;
                elementWithMaxFreq = arr[i];
            }
        }
        System.out.println(elementWithMaxFreq);
    }
}



