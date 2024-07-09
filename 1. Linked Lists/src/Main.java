public class Main {
    public static void main(String[] args) {
        LinkedList ll = new LinkedList();

        ll.add(10);
        ll.add(20);
        ll.add(30);
        ll.add(40);

        ll.add(4, 25);
        ll.add(5, 35);

        System.out.println("Bellow follows the linked list:");
        ll.printLinkedList();
    }
}