import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<Integer> STACK = new Stack<Integer>();
        //last in first out

        STACK.push(10);
        STACK.push(20);
        STACK.push(30);
        STACK.push(5);

        System.out.println("Initial Stack: " + STACK);

        STACK.push(269);
        STACK.push(666);

        System.out.println("Final Stack: " + STACK);

        System.out.println("Popped element: " + STACK.pop());
        System.out.println("Another popped element: " + STACK.pop());

        System.out.println("Stack after pop operation: " + STACK);
        System.out.println("Is the stack empty? " + STACK.isEmpty());
        System.out.println(STACK.peek());
        System.out.println(STACK.stream().count());

    }
}