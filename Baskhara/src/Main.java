import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Bhaskara bhaskara = new Bhaskara();

        Scanner scanner = new Scanner((System.in));
        System.out.println("What's the value of a?");
        int a = scanner.nextInt();

        System.out.println("What's the value of b?");
        int b = scanner.nextInt();

        System.out.println("What's the value of c?");
        int c = scanner.nextInt();
        bhaskara.findRoots(a, b, c);
        }
}